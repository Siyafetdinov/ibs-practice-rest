package ru.ibs.baseTest;

import ru.ibs.control.DataBaseControl;
import ru.ibs.control.SpecificationControl;

import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static DataBaseControl dataBaseControl;
    @BeforeAll
    static void beforeAll() {
        SpecificationControl.installSpecification(SpecificationControl.requestSpecification());
        dataBaseControl = DataBaseControl.getInstance();
    }
}
