package ru.ibs.dataProvider;
import ru.ibs.product.Product;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AddProductDataProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(Product.builder().name("Манго").type("FRUIT").exotic(true).build()),
                Arguments.of(Product.builder().name("Апельсин").type("FRUIT").exotic(false).build()),
                Arguments.of(Product.builder().name("Арбуз").type("FRUIT").exotic(false).build())
        );
    }
}
