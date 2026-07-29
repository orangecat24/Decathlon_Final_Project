package stepDefinitions;

import Tests.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    static BaseTest baseTest = new BaseTest();

    @Before
    public void setUp()
    {
        baseTest.setUp();
    }

    @After
    public void tearDown() throws InterruptedException
    {
        baseTest.tearDown();
    }
}
