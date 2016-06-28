/**
 * The MIT License (MIT)
 *
 * Sonar Quamoco Plugin
 * Copyright (c) 2015 Isaac Griffith, SiliconCode, LLC
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
package com.sparqline.quamoco.codetree;

import com.google.gson.annotations.Expose;

/**
 * MethodNOde -
 *
 * @author Isaac Griffith
 */
public class MethodNode extends CodeNode {

    @Expose
    private boolean constructor;

    private MethodNode() {
        super();
        constructor = false;
    }

    /**
     * @param identifier
     * @param start
     * @param end
     */
    public MethodNode(final String qIdentifier, String name, final boolean constructor, final int start,
            final int end) {
        super(qIdentifier, name, start, end);
        this.constructor = constructor;
    }

    public boolean isConstructor() {
        return constructor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.siliconcode.quamoco.codetree.CodeNode#getType()
     */
    @Override
    public String getType() {
        return CodeNodeType.METHOD;
    }

    /**
     * @param constructor
     */
    public void setConstructor(final boolean constructor) {
        this.constructor = constructor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sparqline.quamoco.codetree.CodeNode#update(com.sparqline.quamoco.codetree.CodeNode)
     */
    @Override
    public void update(CodeNode m) {
        if (m == null)
            return;

        if (!(m instanceof MethodNode))
            return;

        MethodNode node = (MethodNode) m;
        setStart(m.getStart());
        setEnd(m.getEnd());

        setConstructor(node.isConstructor());
    }
}
