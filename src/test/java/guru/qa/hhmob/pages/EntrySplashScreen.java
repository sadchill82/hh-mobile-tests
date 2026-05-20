package guru.qa.hhmob.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Splash после language-диалога: «Работа у вас в кармане»
public class EntrySplashScreen extends BaseScreen {

    public static final By HEADING = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Работа\").textContains(\"в кармане\")");
    public static final By NEW_USER_BUTTON = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Я новый пользователь\")");
    public static final By EXISTING_ACCOUNT_BUTTON = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"У меня есть аккаунт\")");
    public static final By POLICY_TEXT = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"конфиденциальности\")");

    public EntrySplashScreen(AndroidDriver driver) {
        super(driver);
    }

    @Step("Дождаться появления splash-экрана")
    public EntrySplashScreen waitVisible() {
        wait.until(ExpectedConditions.presenceOfElementLocated(NEW_USER_BUTTON));
        return this;
    }

    public boolean hasNewUserButton() {
        return isPresent(NEW_USER_BUTTON);
    }

    public boolean hasExistingAccountButton() {
        return isPresent(EXISTING_ACCOUNT_BUTTON);
    }

    public boolean hasPolicyDisclaimer() {
        return isPresent(POLICY_TEXT);
    }

    @Step("Тап «Я новый пользователь»")
    public LoginMethodsScreen tapNewUser() {
        waitClickable(NEW_USER_BUTTON).click();
        return new LoginMethodsScreen(driver);
    }

    @Step("Тап «У меня есть аккаунт. Войти»")
    public LoginMethodsScreen tapExistingAccount() {
        waitClickable(EXISTING_ACCOUNT_BUTTON).click();
        return new LoginMethodsScreen(driver);
    }
}
