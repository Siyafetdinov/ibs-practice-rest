package ru.ibs.utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import ru.ibs.constans.EndPoints;
import ru.ibs.product.Product;

import io.restassured.http.ContentType;

import java.util.Arrays;
import java.util.List;

public class RestUtils {
    private static final RequestSpecification baseSpecification = RestAssured.given().log().all();

    public static List<Product> getAllProducts() {
        return Arrays.asList(baseSpecification.given()
                .baseUri(EndPoints.URL)
                .contentType(ContentType.JSON)
                .when()
                .get(EndPoints.API_FOOD)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .getBody()
                .as(Product[].class));
    }

    public static void postProduct(Product product) {
        baseSpecification.given()
                .baseUri(EndPoints.URL)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(EndPoints.API_FOOD)
                .then()
                .statusCode(200);
    }

    public static void postReset() {
        postReset(EndPoints.API_DATA_RESET);
    }

    public static void postReset(String endPoint) {
        baseSpecification.given()
                .baseUri(EndPoints.URL)
                .when()
                .post(endPoint)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
