package ru.ibs.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String name;
    private String type;
    private boolean exotic;

    public Product() {
    }

    @JsonCreator
    public Product(@JsonProperty("name") String name, @JsonProperty("type") String type, @JsonProperty("exotic") boolean exotic) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }
}
