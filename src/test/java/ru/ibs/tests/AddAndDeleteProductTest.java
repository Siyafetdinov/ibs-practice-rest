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

public class AddAndDeleteProductTest extends BaseTest {
    private final String TABLE_NAME = "Food";

    @Tag("IBS_1")
    @DisplayName("Добавление товаров")
    @ParameterizedTest
    @ArgumentsSource(AddProductDataProvider.class)
    void addAndDeleteProductTest(Product testProduct) {

        // Добавляем тестируемый товар через API
        RestUtils.postProduct(testProduct);

        // Сраниваем тестируемый продукт и продукт получаемый через API и БД
        Assertions.assertAll(
                // Сравниваем тестируемый товар, с полученным товаром из списка API
                () -> Assertions.assertEquals(
                        testProduct,
                        RestUtils.getAllProducts().stream()
                                .filter(product -> testProduct.getName().equals(product.getName()))
                                .findFirst()
                                .orElse(null),
                        "Полученный товар из списка API не совпадает с тестриуемым товаром, либо не был найден"),

                // Проверяем что тестируемый товар, был добавлен в БД
                () -> Assertions.assertNotNull(
                        dataBaseControl.getProductFromTable(testProduct, TABLE_NAME),
                        "Тестируемый товар не был найден в БД")
        );

        // Возвращаем к первоначальному состоянию. Через API
        RestUtils.postReset();

        // Проверяем, что тестируемый продукт был удален
        Assertions.assertAll(
                // Провеярем есть ли тестируемый продукт внутри списка полученного из API
                () -> Assertions.assertNull(
                        RestUtils.getAllProducts().stream()
                                .filter(product -> testProduct.getName().equals(product.getName()))
                                .findFirst()
                                .orElse(null),
                        "Товар не был удален, через API его можно получить"),

                // Провеярем есть ли тестируемый продукт внутри БД
                () -> Assertions.assertNull(dataBaseControl.getProductFromTable(testProduct, TABLE_NAME),
                        "Товар не был удален, через БД его можно получить")
        );
    }
}