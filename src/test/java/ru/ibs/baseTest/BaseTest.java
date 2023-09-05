package ru.ibs.baseTest;
import ru.ibs.dataBaseControl.DataBaseControl;

import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static DataBaseControl dataBaseControl;
    @BeforeAll
    static void beforeAll() {
        dataBaseControl = DataBaseControl.getInstance();
    }
}
