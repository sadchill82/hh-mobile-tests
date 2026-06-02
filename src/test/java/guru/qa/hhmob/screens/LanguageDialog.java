package guru.qa.hhmob.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Compose UI - resource-id обфусцированы, ищем по тексту
public class LanguageDialog extends BaseScreen {

    public static final By HEADING = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Переключить язык приложения\")");
    public static final By KEEP_RUSSIAN_BUTTON = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Оставить русский язык\")");

    public LanguageDialog(AndroidDriver driver) {
        super(driver);
    }

    @Step("Дождаться появления диалога выбора языка")
    public LanguageDialog waitVisible() {
        wait.until(ExpectedConditions.presenceOfElementLocated(HEADING));
        return this;
    }

    public boolean isVisible() {
        return isPresent(HEADING);
    }

    @Step("Тап «Оставить русский язык»")
    public EntrySplashScreen keepRussian() {
        waitClickable(KEEP_RUSSIAN_BUTTON).click();
        return new EntrySplashScreen(driver);
    }
}
