package com.trimble;

import com.trimble.utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrimbleTest {


    @Test
    public void creatingResource() {
        //This test is written only to show sample POST response
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body("{\"title\": \"trimble\",\"body\": \"sdetInterview\",\"userId\": 1}")
                .when().post(ConfigurationReader.getProperty("base_uri_api") + "/posts");

        assertEquals(201, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());

        JsonPath jsonPath = response.jsonPath();
        assertEquals(jsonPath.getString("title"), "trimble");
        assertEquals(jsonPath.getString("body"), "sdetInterview");
        assertEquals(jsonPath.getString("id"), "101");
        assertEquals(jsonPath.getString("userId"), "1");

        response.prettyPrint();

    }

    @Test
    public void getPosts() {
        Response response = given().accept(ContentType.JSON)
                .when()
                .get(ConfigurationReader.getProperty("base_uri_api") + "/posts");

        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());
        assertNotNull(response.jsonPath());
        response.prettyPrint();


    }

    @Test
    public void put() {
        ////This test is written only to show sample PUT response
        Map<String, Object> putRequestMap = new LinkedHashMap<>();
        putRequestMap.put("id", "1");
        putRequestMap.put("userId", "1");
        putRequestMap.put("title", "trimble2");
        putRequestMap.put("body", "sdetInterview2");
        Response response = given().accept(ContentType.JSON)
                .body(putRequestMap).log().body()
                .when()
                .put(ConfigurationReader.getProperty("base_uri_api") + "/posts/1");

        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());

        response.prettyPrint();


    }

    @Test
    public void getPosts1() {
        Response response = given().accept(ContentType.JSON)
                .when()
                .get(ConfigurationReader.getProperty("base_uri_api") + "/posts/2");

        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());

        //body validation
        JsonPath jsonPath = response.jsonPath();
        String expectedTitle = "qui est esse";
        String expectedBody = "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla";
        assertEquals(jsonPath.getString("userId"), "1");
        assertEquals(jsonPath.getString("id"), "2");
        assertEquals(jsonPath.getString("title"), expectedTitle);
        assertEquals(jsonPath.getString("body"), expectedBody);
        response.prettyPrint();
    }


    @Test
    public void comments() {
        //This test is written only to show sample POST response
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"postId\": 1,\n" +
                        "    \"id\": 55,\n" +
                        "    \"name\": \"sumeyra\",\n" +
                        "    \"email\": \"sumeyra@gmail.com\",\n" +
                        "    \"body\": \"Good serve\"\n" +
                        "  }")
                .when().post(ConfigurationReader.getProperty("base_uri_api") + "/posts/1/comments");

        assertEquals(201, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());

        JsonPath jsonPath = response.jsonPath();
        assertEquals(jsonPath.getString("postId"), "1");
        assertEquals(jsonPath.getString("id"), "501");
        assertEquals(jsonPath.getString("email"), "sumeyra@gmail.com");
        assertEquals(jsonPath.getString("body"), "Good serve");

        response.prettyPrint();

    }

    @Test
    public void getComments() {
        Response response = given().accept(ContentType.JSON)
                .when()
                .get(ConfigurationReader.getProperty("base_uri_api") + "/posts/1/comments");

        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());
        assertNotNull(response.jsonPath());
        response.prettyPrint();


    }


}
