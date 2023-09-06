package ru.ibs.utils;

import ru.ibs.constans.EndPoints;
import ru.ibs.product.Product;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

public class RestUtils {
    public static Cookie getCookie(String nameCookie) {
        return given()
                .when()
                .get(EndPoints.API_FOOD)
                .getDetailedCookie(nameCookie);
    }

    public static List<Product> getAllProducts() {
        return Arrays.asList(given()
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
        given()
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(EndPoints.API_FOOD)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public static void postReset() {
        postReset(EndPoints.API_DATA_RESET);
    }

    public static void postReset(String endPoint) {
        given()
                .when()
                .post(endPoint)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
