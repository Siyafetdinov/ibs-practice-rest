package ru.ibs.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import ru.ibs.baseTest.BaseTest;
import ru.ibs.dataProvider.AddProductDataProvider;
import ru.ibs.product.Product;
import ru.ibs.utils.RestUtils;
import java.util.List;

public class AddAndDeleteProductTest extends BaseTest {
    private final String TABLE_NAME = "Food";

    @Tag("IBS_1")
    @DisplayName("Добавление товаров")
    @ParameterizedTest
    @ArgumentsSource(AddProductDataProvider.class)
    void addAndDeleteProductTest(Product testProduct) {

        // Сохранение первоначальный список товаров, через API")
        List<Product> products = RestUtils.getAllProducts();

        // Проверяем что список полученный через API совпадает со списком полученным через БД
        Assertions.assertEquals(products, dataBaseControl.selectAllProductFrom(TABLE_NAME));

        // Добавляем тестируемый товар через API")
        RestUtils.postProduct(testProduct);

        // Проверяем, что тестируемый товар добавился.
        // Методом сравнения первоначального списка товаров, полученного через API в начале теста
        // и текущего списка товаров, полученного из БД, после добавления тестируемого товара.
        // Они должны быть не равными
        Assertions.assertNotEquals(products, dataBaseControl.selectAllProductFrom(TABLE_NAME),
                "Первоначальный список товаров из API, совпадает со списком из БД, после добавления товара");

        // Проверяем добавленный товар
        Assertions.assertEquals(testProduct, dataBaseControl.getProductFromTable(testProduct, TABLE_NAME),
                "Товар не совпадает с тем, что был отправлен в POST запросе");

        // Возвращаем к первоначальному состоянию. Через API
        RestUtils.postReset();

        // Проверяем что тестируемый товар удалился из БД
        Assertions.assertNull(dataBaseControl.getProductFromTable(testProduct, TABLE_NAME),
                "Товар не был удален");

        // Проверяем что список полученный через API в начале теста совпадает
        // со списком полученным через через API в конце теста")
        Assertions.assertEquals(products, RestUtils.getAllProducts(),
                "Первоначальный список и список после всех манипуляйций не совпадает");
    }
}