/**
 * 
 */
package com.sparqline.codetree.json;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.sparqline.codetree.json.ProjectNodeDeserializer;
import com.sparqline.codetree.node.ProjectNode;

/**
 * @author fate
 *
 */
public class ProjectNodeDeserializerTest {

    private ProjectNodeDeserializer fixture;

    /**
     * @throws Exception
     */
    @Test
    public void testDeserialize_1() throws Exception {
        String json = "{\"subprojects\": {},\"modules\": {},\"files\": {},\"start\": 1,\"end\": 1,\"range\": {\"lowerBound\": {\"endpoint\": 1},\"upperBound\": {\"endpoint\": 1}},\"metrics\": {},\"qIdentifier\": \"project\",\"name\": \"project\"}";

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ProjectNode.class, fixture);
        Gson gson = builder.create();

        ProjectNode fn = gson.fromJson(json, ProjectNode.class);
        assertNotNull(fn);
    }

    /**
     * @throws Exception
     */
    @Test(expected = JsonParseException.class)
    public void testDeserialize_2() throws Exception {
        String json = "{\"subprojects\": {},\"modules\": {},\"files\": {},\"end\": 1,\"range\": {\"lowerBound\": {\"endpoint\": 1},\"upperBound\": {\"endpoint\": 1}},\"metrics\": {},\"qIdentifier\": \"project\",\"name\": \"project\"}";

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ProjectNode.class, fixture);
        Gson gson = builder.create();

        gson.fromJson(json, ProjectNode.class);
    }

    /**
     * @throws Exception
     */
    @Test(expected = JsonParseException.class)
    public void testDeserialize_3() throws Exception {
        String json = "{\"subprojects\": {},\"modules\": {},\"files\": {},\"start\": 1,\"end\": 1,\"range\": {\"lowerBound\": {\"endpoint\": 1},\"upperBound\": {\"endpoint\": 1}},\"metrics\": {},\"name\": \"project\"}";

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ProjectNode.class, fixture);
        Gson gson = builder.create();

        gson.fromJson(json, ProjectNode.class);
    }

    /**
     * @throws Exception
     */
    @Test(expected = JsonParseException.class)
    public void testDeserialize_4() throws Exception {
        String json = "{\"subprojects\": {},\"modules\": {},\"files\": {},\"start\": 1,\"end\": 1,\"range\": {\"lowerBound\": {\"endpoint\": 1},\"upperBound\": {\"endpoint\": 1}},\"metrics\": {},\"qIdentifier\": \"project\",}";

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ProjectNode.class, fixture);
        Gson gson = builder.create();

        gson.fromJson(json, ProjectNode.class);
    }

    /**
     * @throws Exception
     */
    @Test(expected = JsonParseException.class)
    public void testDeserialize_5() throws Exception {
        String json = "Test";

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ProjectNode.class, fixture);
        Gson gson = builder.create();

        gson.fromJson(json, ProjectNode.class);
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
        fixture = new ProjectNodeDeserializer();
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
        new org.junit.runner.JUnitCore().run(ProjectNodeDeserializerTest.class);
    }
}