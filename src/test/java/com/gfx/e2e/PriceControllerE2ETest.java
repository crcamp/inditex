package com.gfx.e2e;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.is;

@SpringBootTest(classes = com.gfx.Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerE2ETest {
    
    @LocalServerPort 
    private int port;

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    @BeforeEach 
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void test1_day14At10h() {
        given().param("applicationDate", "2020-06-14T10:00:00").param("productId", PRODUCT_ID).param("brandId", BRAND_ID)
        .when().get("/api/prices")
        .then().statusCode(200).contentType(ContentType.JSON).body("priceList", is(1)).body("price", is(35.50f));
    }

    @Test
    void test2_day14At16h() {
        given().param("applicationDate", "2020-06-14T16:00:00").param("productId", PRODUCT_ID).param("brandId", BRAND_ID)
        .when().get("/api/prices")
        .then().statusCode(200).body("priceList", is(2)).body("price", is(25.45f));
    }

    @Test
    void test3_day14At21h() {
        given().param("applicationDate", "2020-06-14T21:00:00").param("productId", PRODUCT_ID).param("brandId", BRAND_ID)
        .when().get("/api/prices")
        .then().statusCode(200).body("priceList", is(1)).body("price", is(35.50f));
    }

    @Test
    void test4_day15At10h() {
        given().param("applicationDate", "2020-06-15T10:00:00").param("productId", PRODUCT_ID).param("brandId", BRAND_ID)
        .when().get("/api/prices")
        .then().statusCode(200).body("priceList", is(3)).body("price", is(30.50f));
    }

    @Test
    void test5_day16At21h() {
        given().param("applicationDate", "2020-06-16T21:00:00").param("productId", PRODUCT_ID).param("brandId", BRAND_ID)
        .when().get("/api/prices")
        .then().statusCode(200).body("priceList", is(4)).body("price", is(38.95f));
    }

    @Test
    void returnsNotFoundWhenNoPriceExists() {
        given().param("applicationDate", "2019-01-01T10:00:00").param("productId", PRODUCT_ID).param("brandId", BRAND_ID)
        .when().get("/api/prices")
        .then().statusCode(404);
    }

    @Test
    void returnsBadRequestWhenParamMissing() {
        given().param("productId", PRODUCT_ID)
        .when().get("/api/prices")
        .then().statusCode(400);
    }

}
