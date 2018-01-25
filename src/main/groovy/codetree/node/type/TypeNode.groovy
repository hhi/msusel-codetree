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
package codetree.node.type

import codetree.*
import codetree.node.Accessibility
import codetree.node.CodeNode
import codetree.node.Modifiers
import codetree.node.member.FieldNode
import codetree.node.structural.FileNode
import codetree.node.member.MethodNode
import codetree.node.structural.ModuleNode
import codetree.node.structural.NamespaceNode
import codetree.node.member.ParameterNode
import codetree.node.member.PropertyNode
import codetree.node.structural.ProjectNode

/**
 * @author Isaac Griffith
 * @version 1.2.0
 */
abstract class TypeNode extends CodeNode {

    List<ParameterNode> templateParams = []
    NamespaceNode namespace

    TypeNode(String key, String parentKey, Map<String, Double> metrics = [:],
             Accessibility accessibility = Accessibility.PUBLIC, specifiers = [],
             int start, int end, List<ParameterNode> templateParams, NamespaceNode namespace) {
        super(key, parentKey, metrics, accessibility, specifiers, start, end)
        this.templateParams = templateParams
        this.namespace = namespace
    }

    def methods() {
        children.findAll {
            it instanceof MethodNode
        }
    }

    def fields() {
        children.findAll {
            it instanceof FieldNode
        }
    }

    def properties() {
        children.findAll {
            it instanceof PropertyNode
        }
    }

    boolean isStatic() {
        modifiers.contains(Modifiers.STATIC)
    }

    boolean isAbstract() {
        modifiers.contains(Modifiers.ABSTRACT)
    }

    abstract boolean isInterface()

    def name() {
        key.split("\\.").last()
    }

    MethodNode findMethod(final int line) {
        if (line < getStart() || line > getEnd())
            return null

        methods().find { MethodNode m -> m.containsLine(line) }
    }

    def extractTree(tree) {
        TypeNode type = (TypeNode) node

        FileNode file = findFile(type.getParentKey())

        retVal = new CodeTree()
        Stack<ProjectNode> stack = new Stack<>()
        ProjectNode project
        ModuleNode module

        if (findProject(file.getParentKey()) != null) {
            project = findProject(file.getParentKey())
        } else {
            module = findModule(file.getParentKey())
            project = findProject(module.getParentKey())
        }

        stack.push(project)
        while (project.hasParent()) {
            project = findProject(project.getParentKey())
            stack.push(project)
        }

        ProjectNode current = stack.pop().cloneNoChildren()
        ProjectNode root = current
        ProjectNode next
        while (!stack.isEmpty()) {
            next = stack.pop().cloneNoChildren()

            current.addSubProject(next)
            current = next
        }

        ProjectNode currentProject = findProject(current.getQIdentifier())

        file = currentProject.getFile(type.getParentKey()).cloneNoChildren()

        currentProject.addFile(file)

        file.addType(type.cloneNoChildren())

        retVal.setProject(root)
    }

    abstract def generatePlantUML()
}