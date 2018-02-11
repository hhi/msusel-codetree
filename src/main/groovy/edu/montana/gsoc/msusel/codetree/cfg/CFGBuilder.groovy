/**
 * The MIT License (MIT)
 *
 * MSUSEL CodeTree
 * Copyright (c) 2015-2017 Montana State University, Gianforte School of Computing,
 * Software Engineering Laboratory
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package edu.montana.gsoc.msusel.codetree.cfg

import com.google.common.collect.Sets
import com.google.common.graph.GraphBuilder
import com.google.common.graph.MutableGraph
import org.apache.commons.lang3.tuple.Pair

/**
 * @author Isaac Griffith
 * @version 1.2.0
 */
class CFGBuilder {

    MutableGraph<ControlFlowNode> currentCFG
    ControlFlowNode prevNode
    Stack<Pair<BlockStart, BlockEnd>> blocks
    Stack<Pair<MethodStart, MethodEnd>> methodNodes
    int stmtCount = 1
    def labeledStatements = {}

    CFGBuilder() {
        currentCFG = GraphBuilder.directed().build()
        prevNode = null
        blocks = new Stack<>()

    }
    
    void inject(BlockStart start, BlockEnd end) {
        Set<ControlFlowNode> outs = Sets.newHashSet(currentCFG.successors(prevNode))
        println("Outs:")

        // unless special case of if
        //if (!blocks.empty() && blocks.peek().getLeft().getType() != StatementType.IF) {
        for (ControlFlowNode out : outs) {
            println("\t" + out)
            currentCFG.removeEdge(prevNode, out)
        }
        //}

        currentCFG.putEdge(start, end)
        currentCFG.putEdge(prevNode, start)

        // Unless special case of Break, Continue, or Return
        for (ControlFlowNode out : outs) {
            println("\t" + out)
            currentCFG.putEdge(end, out)
        }

        prevNode = start

        blocks.push(Pair.of(start, end))
    }

    void inject(ControlFlowNode node) {
        Set<ControlFlowNode> outs = Sets.newHashSet(currentCFG.successors(prevNode))

        if (!blocks.empty() && blocks.peek().getLeft().getType() == StatementType.IF)
            prevNode = blocks.peek().getLeft()

        ControlFlowNode endNode = !blocks.empty() ? blocks.peek().getRight() : null
        if (endNode != null) {
            currentCFG.removeEdge(prevNode, endNode)
            currentCFG.putEdge(prevNode, node)
            currentCFG.putEdge(node, endNode)
        } else {
            currentCFG.putEdge(prevNode, node)
            for (ControlFlowNode out : outs) {
                currentCFG.removeEdge(prevNode, out)
                currentCFG.putEdge(node, out)
            }
        }

        prevNode = node
    }

    void createStatement(StatementType type, String label = null, boolean jump = false, JumpTo jumpto = null) {
        ControlFlowNode s
        if (label != null) {
            s = LabeledStatement.builder().label(stmtCount++).codeLabel(label).create()
            labeledStatements.put(label, s)
            inject(s)
        } else {
            s = Statement.builder().type(type).label(stmtCount++).create()
        }

        if (jump) {
            ControlFlowNode oldPrev = prevNode
            inject(s)
            prevNode = oldPrev
            Pair<BlockStart, BlockEnd> loop = findLoop()
            switch (jumpto) {
                case JumpTo.LABEL:
                    currentCFG.putEdge(s, (ControlFlowNode) labeledStatements.get(label))
                    break
                case JumpTo.LOOP_END:
                    currentCFG.putEdge(s, loop.getRight())
                    break
                case JumpTo.LOOP_START:
                    currentCFG.putEdge(s, blocks.peek().getLeft())
                    break
                case JumpTo.METHOD_END:
                    currentCFG.putEdge(s, methodNodes.peek().getRight())
                    break
            }
        } else {
            inject(s)
        }
    }

    private Pair<BlockStart, BlockEnd> findLoop() {
        Stack<Pair<BlockStart, BlockEnd>> temp = new Stack<>()
        Pair<BlockStart, BlockEnd> loop = null

        while (!blocks.empty()) {
            if (blocks.peek().left.isLoop()) {
                loop = blocks.peek()
                break
            }

            temp.push(blocks.pop())
        }

        while (!temp.empty())
            blocks.push(temp.pop())

        loop
    }

    void startLoop(StatementType type, boolean atLeastOne = false) {
        BlockStart bs = LoopStart.builder().type(type).label(stmtCount++).create()
        BlockEnd be = BlockEnd.builder().type(StatementType.END).label(stmtCount++).create()

        injectBlock(bs, be)

        if (!atLeastOne) {
            Set<ControlFlowNode> set = Sets.newHashSet(currentCFG.successors(be))

            for (ControlFlowNode node : set) {
                currentCFG.putEdge(bs, node)
                currentCFG.removeEdge(be, node)
            }
        }
        currentCFG.putEdge(be, bs)
    }

    void endLoop(atLeastOne = false) {
        Pair<BlockStart, BlockEnd> pair = blocks.peek()

        endBlock()

        if (!atLeastOne)
            prevNode = pair.getLeft()
    }

    void startDecision(StatementType type) {
        BlockStart bs = BlockStart.builder().type(type).label(stmtCount++).create()
        BlockEnd be = BlockEnd.builder().type(StatementType.END).label(stmtCount++).create()

        inject(bs, be)
    }

    void endDecision(boolean noDefault = true) {
        if (!noDefault)
            currentCFG.putEdge(blocks.peek().getLeft(), blocks.peek().getRight())

        endBlock()
    }

    void startBlock(StatementType type, StatementType endType = StatementType.END) {
        BlockStart bs = BlockStart.builder().type(type).label(stmtCount++).create()
        BlockEnd be = BlockEnd.builder().type(endType).label(stmtCount++).create()

        inject(bs, be)
    }

    void endBlock() {
        Pair<BlockStart, BlockEnd> p = blocks.pop()

        if (!blocks.empty() && (blocks.peek().getLeft().getType() == StatementType.IF || blocks.peek().getLeft().getType() == StatementType.SWITCH))
            prevNode = blocks.peek().getLeft()
        else
            prevNode = p.getRight()
    }

    void startMethod() {
        MethodStart ms = new MethodStart()
        MethodEnd me = new MethodEnd()

        currentCFG.addNode(ms)
        currentCFG.addNode(me)
        currentCFG.putEdge(ms, me)

        prevNode = ms
    }

    void endMethod() {
        methods.peek().setCfg(currentCFG)

        println(CFG2DOT.generateDot(currentCFG, (String)methods.peek().name()))

        currentCFG = null
        prevNode = null
        stmtCount = 1
    }


}
