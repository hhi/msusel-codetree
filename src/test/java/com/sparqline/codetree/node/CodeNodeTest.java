package com.sparqline.codetree.node;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sparqline.codetree.node.CodeNode;
import com.sparqline.codetree.node.FieldNode;
import com.sparqline.codetree.node.FileNode;
import com.sparqline.codetree.node.TypeNode;

/**
 * The class <code>CodeNodeTest</code> contains tests for the class
 * <code>{@link CodeNode}</code>.
 *
 * @generatedBy CodePro at 1/26/16 6:38 PM
 * @author fate
 * @version $Revision: 1.0 $
 */
public class CodeNodeTest {

    private CodeNode fixture;

    /**
     * Run the int compareTo(CodeNode) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testCompareTo_1() throws Exception {
        final CodeNode other = new TypeNode("namespace.Type", "Type", 1, 100);

        final int result = fixture.compareTo(other);

        // add additional test code here
        Assert.assertEquals(1, result);
    }

    /**
     * Run the int compareTo(CodeNode) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testCompareTo_2() throws Exception {
        final CodeNode other = new TypeNode("namespace.Type", "Type", 100, 1000);

        final int result = fixture.compareTo(other);

        // add additional test code here
        Assert.assertEquals(0, result);
    }

    /**
     * Run the int compareTo(CodeNode) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testCompareTo_3() throws Exception {
        final CodeNode other = new TypeNode("namespace.Type", "Type", 200, 1000);

        final int result = fixture.compareTo(other);

        // add additional test code here
        Assert.assertEquals(-1, result);
    }

    /**
     * Run the boolean containsLine(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testContainsLine_1() throws Exception {
        final int line = 100;

        final boolean result = fixture.containsLine(line);

        // add additional test code here
        Assert.assertEquals(true, result);
    }

    /**
     * Run the boolean containsLine(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testContainsLine_2() throws Exception {
        final int line = 99;

        final boolean result = fixture.containsLine(line);

        // add additional test code here
        Assert.assertEquals(false, result);
    }

    /**
     * Run the boolean containsLine(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testContainsLine_3() throws Exception {
        final int line = 1000;

        final boolean result = fixture.containsLine(line);

        // add additional test code here
        Assert.assertEquals(true, result);
    }

    /**
     * Run the boolean containsLine(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testContainsLine_4() throws Exception {
        final int line = 1001;

        final boolean result = fixture.containsLine(line);

        // add additional test code here
        Assert.assertEquals(false, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testEquals_1() throws Exception {
        final CodeNode obj = new TypeNode("namespace.Type", "Type", 100, 1000);

        final boolean result = fixture.equals(obj);

        // add additional test code here
        Assert.assertEquals(true, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testEquals_2() throws Exception {
        final Object obj = null;

        final boolean result = fixture.equals(obj);

        // add additional test code here
        Assert.assertEquals(false, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testEquals_3() throws Exception {
        final Object obj = new Object();

        final boolean result = fixture.equals(obj);

        // add additional test code here
        Assert.assertEquals(false, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testEquals_4() throws Exception {
        final CodeNode obj = new TypeNode("namespace.Type", "Type", 100, 1000);

        final boolean result = fixture.equals(obj);

        // add additional test code here
        Assert.assertTrue(result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testEquals_5() throws Exception {
        final CodeNode obj = new TypeNode("namespace.NewType", "NewType", 100, 1000);

        final boolean result = fixture.equals(obj);

        // add additional test code here
        Assert.assertFalse(result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testEquals_6() throws Exception {
        final FileNode obj = new FileNode("path");

        final boolean result = fixture.equals(obj);

        // add additional test code here
        Assert.assertFalse(result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testEquals_7() throws Exception {
        final CodeNode obj = new TypeNode("namespace.Type", "Type", 125, 1000);

        final boolean result = fixture.equals(obj);

        // add additional test code here
        Assert.assertFalse(result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testEquals_8() throws Exception {
        final CodeNode obj = new TypeNode("namespace.Type", "Type", 100, 900);

        final boolean result = fixture.equals(obj);

        // add additional test code here
        Assert.assertFalse(result);
    }

    /**
     * Run the int getEnd() method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testGetEnd_1() throws Exception {
        final int result = fixture.getEnd();

        // add additional test code here
        Assert.assertEquals(1000, result);
    }

    /**
     * Run the String getQIdentifier() method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testGetQIdentifier_1() throws Exception {
        final String result = fixture.getQIdentifier();

        // add additional test code here
        Assert.assertEquals("namespace.Type", result);
    }

    /**
     * Run the int getStart() method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testGetStart_1() throws Exception {
        final int result = fixture.getStart();

        // add additional test code here
        Assert.assertEquals(100, result);
    }

    /**
     * Run the void setEnd(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testSetEnd_1() throws Exception {
        final int end = 150;

        try {
            fixture.setEnd(end);
            Assert.assertEquals(end, fixture.getEnd());
        }
        catch (final IllegalArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * Run the void setEnd(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testSetEnd_2() throws Exception {
        final int end = 1;

        try {
            fixture.setEnd(end);
            Assert.fail();
        }
        catch (final IllegalArgumentException e) {

        }
    }

    /**
     * Run the void setEnd(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testSetEnd_3() throws Exception {
        fixture = new FieldNode("field", "field", 150);
        final int end = 1001;

        try {
            fixture.setEnd(end);

        }
        catch (final IllegalArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * Run the void setStart(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testSetStart_1() throws Exception {
        final int start = 1001;

        try {
            fixture.setStart(start);
            Assert.fail();
        }
        catch (final IllegalArgumentException e) {
        }
    }

    /**
     * Run the void setStart(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testSetStart_2() throws Exception {
        final int start = -1;

        try {
            fixture.setStart(start);
            Assert.fail();
        }
        catch (final IllegalArgumentException e) {
        }
    }

    /**
     * Run the void setStart(int) method test.
     *
     * @throws Exception
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Test
    public void testSetStart_3() throws Exception {
        final int start = 250;

        try {
            fixture.setStart(start);
            Assert.assertEquals(start, fixture.getStart());
        }
        catch (final IllegalArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *             if the initialization fails for some reason
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @Before
    public void setUp() throws Exception {
        fixture = new TypeNode("namespace.Type", "Type", 100, 1000);
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *             if the clean-up fails for some reason
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    @After
    public void tearDown() throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args
     *            the command line arguments
     * @generatedBy CodePro at 1/26/16 6:38 PM
     */
    public static void main(final String[] args) {
        new org.junit.runner.JUnitCore().run(CodeNodeTest.class);
    }
}