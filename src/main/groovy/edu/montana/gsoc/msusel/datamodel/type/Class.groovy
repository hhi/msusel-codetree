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
package edu.montana.gsoc.msusel.datamodel.type

import edu.montana.gsoc.msusel.datamodel.Accessibility
import edu.montana.gsoc.msusel.datamodel.Component
import edu.montana.gsoc.msusel.datamodel.Modifier
import edu.montana.gsoc.msusel.datamodel.member.Field
import edu.montana.gsoc.msusel.datamodel.member.Method
import edu.montana.gsoc.msusel.datamodel.structural.File
import groovy.transform.builder.Builder

import javax.persistence.Entity

/**
 * @author Isaac Griffith
 * @version 1.2.0
 */
@Entity
class Class extends Classifier {

    @Builder(buildMethodName = "create")
    private Class(String key, File file, Type parent, String name, Accessibility access, List<Modifier> modifiers, List<Type> children, List<Component> members, int start, int end) {
        super(key, file, parent, name, access, modifiers, children, members, start, end)
    }

    Class() {
        super()
    }

    /**
     * {@inheritDoc}
     */
    @Override
    boolean isInterface()
    {
        false
    }

    /**
     * {@inheritDoc}
     */
    @Override
    def generatePlantUML() {
        StringBuilder builder = new StringBuilder()
        builder.append("class ${this.name()} {\n")
        fields().each { Field f -> builder.append("${f.plantUML()}\n")}
        methods().each { Method m -> builder.append("${m.accessibility.plantUML}${m.name()}() : ${m.getType()}\n") }
        builder.append("}\n")
        builder.append("\n")

        builder.toString()
    }
}