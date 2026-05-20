package guru.qa.hhmob.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginMethodsScreen extends BaseScreen {

    public static final By HEADING = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Вход и регистрация\")");
    public static final By PHONE_OPTION = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"По телефону\")");
    public static final By EMAIL_OPTION = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"По электронной почте\")");
    public static final By OTHER_METHODS_OPTION = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Другие способы\")");
    public static final By EMPLOYER_OPTION = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Найти сотрудников\")");
    public static final By BACK_BUTTON = AppiumBy.accessibilityId("Назад");

    public LoginMethodsScreen(AndroidDriver driver) {
        super(driver);
    }

    @Step("Дождаться появления экрана способов входа")
    public LoginMethodsScreen waitVisible() {
        wait.until(ExpectedConditions.presenceOfElementLocated(PHONE_OPTION));
        return this;
    }

    public boolean hasPhoneOption() {
        return isPresent(PHONE_OPTION);
    }

    public boolean hasEmailOption() {
        return isPresent(EMAIL_OPTION);
    }

    public boolean hasOtherMethodsOption() {
        return isPresent(OTHER_METHODS_OPTION);
    }

    public boolean hasEmployerOption() {
        return isPresent(EMPLOYER_OPTION);
    }

    public boolean hasBackButton() {
        return isPresent(BACK_BUTTON);
    }

    @Step("Тап «Назад»")
    public EntrySplashScreen tapBack() {
        waitClickable(BACK_BUTTON).click();
        return new EntrySplashScreen(driver);
    }
}
