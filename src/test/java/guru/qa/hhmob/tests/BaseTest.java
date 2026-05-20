package guru.qa.hhmob.tests;

import guru.qa.hhmob.drivers.DriverFactory;
import guru.qa.hhmob.helpers.Attach;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected AndroidDriver driver;

    @BeforeEach
    void startSession() {
        driver = DriverFactory.newDriver();
    }

    @AfterEach
    void endSession() {
        try {
            Attach.screenshot(driver);
            Attach.pageSource(driver);
            Attach.sessionVideo(driver);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
