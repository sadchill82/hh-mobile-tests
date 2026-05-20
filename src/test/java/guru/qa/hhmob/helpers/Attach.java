package guru.qa.hhmob.helpers;

import guru.qa.hhmob.config.Project;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;

public final class Attach {

    private Attach() {
    }

    public static void screenshot(AndroidDriver driver) {
        if (driver == null) return;
        try {
            byte[] bytes = driver.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(bytes), "png");
        } catch (Exception ignored) {
        }
    }

    public static void pageSource(AndroidDriver driver) {
        if (driver == null) return;
        try {
            String source = driver.getPageSource();
            Allure.addAttachment("Page source (XML)", "application/xml",
                    new ByteArrayInputStream(source.getBytes()), "xml");
        } catch (Exception ignored) {
        }
    }

    public static void sessionVideo(AndroidDriver driver) {
        if (!Project.isBrowserstack() || driver == null) return;
        // Полный URL видео отдаёт BS REST API (/app-automate/sessions/<id>.json),
        // здесь просто ссылка на страницу сессии
        String sessionId = driver.getSessionId().toString();
        String html = "<html><body><a href='https://app-automate.browserstack.com/builds'>" +
                "BrowserStack session " + sessionId + "</a></body></html>";
        Allure.addAttachment("BrowserStack session", "text/html", html, "html");
    }
}
