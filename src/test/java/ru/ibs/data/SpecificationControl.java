package ru.ibs.data;

import ru.ibs.constans.EndPoints;
import ru.ibs.utils.RestUtils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class SpecificationControl {
    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(EndPoints.URL)
                .build().cookie(RestUtils.getCookie("JSESSIONID"));
    }

    public static void installSpecification(RequestSpecification requestSpecification) {
        RestAssured.requestSpecification = requestSpecification.log().all();
    }
}
