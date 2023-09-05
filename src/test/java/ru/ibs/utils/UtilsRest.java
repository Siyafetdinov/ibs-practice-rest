package ru.ibs.utils;
import ru.ibs.product.Product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

import java.util.List;

public class UtilsRest {
    public static List<Product> getProductAPI(String url, String urn ) {

        // Отправляем GET Запрос
        String response = given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .when()
                .get(urn)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().asString();

        // Десериализация JSON в объект Product и сохраняем состояние в List<Product>
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products;

        try {
            return objectMapper.readValue(response, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не получилось получить List<Product> из Get запроса" + e);
        }
    }
}
