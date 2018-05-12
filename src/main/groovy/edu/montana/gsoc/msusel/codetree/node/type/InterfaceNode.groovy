/**
 * The MIT License (MIT)
 *
 * MSUSEL CodeTree
 * Copyright (c) 2015-2018 Montana State University, Gianforte School of Computing,
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
package edu.montana.gsoc.msusel.codetree.node.type

import edu.montana.gsoc.msusel.codetree.INode
import edu.montana.gsoc.msusel.codetree.node.Accessibility
import edu.montana.gsoc.msusel.codetree.node.member.FieldNode
import edu.montana.gsoc.msusel.codetree.node.member.MethodNode
import edu.montana.gsoc.msusel.codetree.node.structural.NamespaceNode
import edu.montana.gsoc.msusel.codetree.typeref.TypeVarTypeRef
import groovy.transform.builder.Builder

import javax.persistence.Entity

/**
 * @author Isaac Griffith
 * @version 1.2.0
 */
@Entity
class InterfaceNode extends ClassifierNode {

    /**
     *
     */
    @Builder(buildMethodName='create')
    InterfaceNode(String key, String parentKey, Accessibility accessibility = Accessibility.DEFAULT, specifiers = [],
                  int start, int end, List<TypeVarTypeRef> templateParams, NamespaceNode namespace) {
        super(key, parentKey, accessibility, specifiers, start, end, templateParams, namespace)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    boolean isInterface()
    {
        true
    }

    /**
     * {@inheritDoc}
     */
    @Override
    INode cloneNoChildren() {
        null
    }

    /**
     * {@inheritDoc}
     */
    @Override
    def extractTree(tree) {
        null
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void update(INode other) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    def type() {
        null
    }

    /**
     * {@inheritDoc}
     */
    @Override
    def generatePlantUML() {
        StringBuilder builder = new StringBuilder()
        builder.append("interface ${this.name()} {\n")
        fields().each { FieldNode f -> builder.append("${f.accessibility.plantUML}${f.name()} ${f.getType()}\n")}
        methods().each { MethodNode m -> builder.append("${m.accessibility.plantUML}${m.name()}() : ${m.getType()}\n") }
        builder.append("}\n")
        builder.append("\n")

        builder.toString()
    }
}