package ru.ibs.tests;
import ru.ibs.baseTest.BaseTest;
import ru.ibs.product.Product;
import ru.ibs.utils.UtilsRest;
import ru.ibs.dataProvider.AddProductDataProvider;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;

public class AddAndDeleteProductTest extends BaseTest {
    private final String NAME_TABLE = "Food";

    @Tag("IBS_1")
    @DisplayName("Добавление товаров")
    @ParameterizedTest
    @ArgumentsSource(AddProductDataProvider.class)
    void addAndDeleteProductTest(Product testProduct) {

        // Сохранение первоначальный список товаров, через API
        List<Product> products = UtilsRest.getProductAPI(URL, "/api/food");

        // Проверяем что список полученный через API совпадает со списком полученным через БД
        Assertions.assertEquals(products, dataBaseControl.selectAllProductFrom(NAME_TABLE));

        // Добавляем тестируемый товар через API
        given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .body(testProduct)
                .when()
                .post("/api/food")
                .then()
                .statusCode(200);

        // Проверяем, что тестируемый товар добавился.
        // Методом сравнения первоначального списка товаров, полученного через API в начале теста
        // и текущего списка товаров, полученного из БД, после добавления тестируемого товара.
        // Они должны быть не равными
        Assertions.assertNotEquals(products, dataBaseControl.selectAllProductFrom(NAME_TABLE));

        // Проверяем добавленный товар.
        Assertions.assertEquals(
                testProduct, dataBaseControl.getProductFromTable(testProduct, NAME_TABLE),
                "Товар не совпадает с тем, что был отправлен в POST запросе");

        // Возвращаем к первоначальному состоянию. Через API
        given()
                .baseUri(URL)
                .when()
                .post("api/data/reset")
                .then()
                .assertThat()
                .statusCode(200);

        // Проверяем что тестируемый товар удалился из БД
        Assertions.assertNull(dataBaseControl.getProductFromTable(testProduct, NAME_TABLE),
                "Товар не был удален");

        // Проверяем что список полученный через API в начале теста,
        // совпадает со списком полученным через через API в конце теста
        Assertions.assertEquals(products, UtilsRest.getProductAPI(URL, "/api/food"),
                "Первоначальный список и список после всех манипуляйций не совпадает");
    }
}