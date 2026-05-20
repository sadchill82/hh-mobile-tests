package guru.qa.hhmob.tests;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// Дамперы page source - включать когда обновляется UI и плывут селекторы
@Disabled
public class ExplorationTest extends BaseTest {

    @Test
    void dumpAppPageSource() throws Exception {
        sleep(7000);
        dump("01-first-screen");

        // Step 1: language dialog — keep Russian.
        if (tapByTextContains("Оставить русский")) {
            sleep(3000);
            dump("02-after-language");
        }

        // Step 2: try the "I'm a new user" path to see if it leads to anonymous browsing or just signup.
        if (tapByTextContains("Я новый пользователь")) {
            sleep(3500);
            dump("02b-after-new-user");
        }

        // Step 3-N: blindly tap typical onboarding buttons if present.
        String[] continueTexts = {"Продолжить", "Принять", "Разрешить", "Пропустить", "Далее", "Понятно", "Хорошо", "Найти работу", "Поиск", "Без регистрации", "Гость"};
        for (int i = 0; i < 8; i++) {
            boolean tapped = false;
            for (String t : continueTexts) {
                if (tapByTextContains(t)) {
                    tapped = true;
                    sleep(2500);
                    break;
                }
            }
            if (!tapped) break;
            dump("0" + (3 + i) + "-after-tap");
        }

        sleep(2000);
        dump("99-final-screen");

        System.out.println("CURRENT ACTIVITY: " + driver.currentActivity());
        System.out.println("CURRENT PACKAGE: " + driver.getCurrentPackage());
    }

    private void dump(String label) throws Exception {
        String src = driver.getPageSource();
        Path out = Path.of("build", "exploration-" + label + ".xml");
        Files.createDirectories(out.getParent());
        Files.writeString(out, src);
        System.out.println("DUMPED " + label + ": " + src.length() + " chars");
    }

    private boolean tapByTextContains(String text) {
        try {
            List<WebElement> els = driver.findElements(
                    AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")"));
            if (els.isEmpty()) return false;
            els.get(0).click();
            System.out.println("TAPPED: " + text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
