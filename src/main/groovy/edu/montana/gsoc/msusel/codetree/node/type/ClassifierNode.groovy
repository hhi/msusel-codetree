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

import edu.montana.gsoc.msusel.codetree.node.Accessibility
import edu.montana.gsoc.msusel.codetree.node.CodeNode
import edu.montana.gsoc.msusel.codetree.node.member.FieldNode
import edu.montana.gsoc.msusel.codetree.node.member.MethodNode
import edu.montana.gsoc.msusel.codetree.node.structural.NamespaceNode
import edu.montana.gsoc.msusel.codetree.typeref.TypeVarTypeRef

import javax.persistence.Entity

@Entity
abstract class ClassifierNode extends TypeNode {

    ClassifierNode(String key, String parentKey, Accessibility accessibility, Object specifiers, int start, int end, NamespaceNode namespace) {
        super(key, parentKey, accessibility, specifiers, start, end, namespace)
    }

    ClassifierNode(String key, String parentKey, Accessibility accessibility, Object specifiers, int start, int end, List<TypeVarTypeRef> templateParams, NamespaceNode namespace) {
        super(key, parentKey, accessibility, specifiers, start, end, templateParams, namespace)
    }

    ClassifierNode(String key, String parentKey, Accessibility accessibility, int start, int end, NamespaceNode namespace) {
        super(key, parentKey, accessibility, start, end, namespace)
    }

    ClassifierNode(String key, String parentKey, int start, int end, NamespaceNode namespace) {
        super(key, parentKey, start, end, namespace)
    }

    MethodNode findMethod(String key) {
        methods().find { MethodNode m -> m.key == key}
    }

    boolean hasMethod(String key) {
        findMethod(key) != null
    }

    FieldNode findField(String key) {
        fields().find { FieldNode f -> f.key == key}
    }

    boolean hasField(String key) {
        findField(key) != null
    }

    abstract boolean isInterface()

    List<CodeNode> following(int line) {
        List<CodeNode> nodes = []
        nodes << fields().findAll { FieldNode fld ->
            fld.containsLine(line) || fld.start > line
        }

        nodes << methods().findAll { MethodNode meth ->
            meth.containsLine(line) || meth.start > line
        }

        nodes
    }
}