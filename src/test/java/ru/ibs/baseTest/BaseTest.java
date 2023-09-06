package ru.ibs.baseTest;

import ru.ibs.data.DataBaseControl;
import ru.ibs.data.SpecificationControl;

import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static DataBaseControl dataBaseControl;
    @BeforeAll
    static void beforeAll() {
        SpecificationControl.installSpecification(SpecificationControl.requestSpecification());
        dataBaseControl = DataBaseControl.getInstance();
    }
}
