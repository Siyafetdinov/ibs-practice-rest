package ru.ibs.baseTest;
import ru.ibs.dataBaseControl.DataBaseControl;

import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static DataBaseControl dataBaseControl;
    protected final String URL = "http://localhost:8080";

    @BeforeAll
    static void beforeAll() {
        dataBaseControl = DataBaseControl.getInstance();
    }
}
