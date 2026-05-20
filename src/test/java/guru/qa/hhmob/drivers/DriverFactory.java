package guru.qa.hhmob.drivers;

import guru.qa.hhmob.config.Project;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// Собирает AndroidDriver под локальный Appium или BrowserStack
// по флагу -Denv=local|browserstack
public final class DriverFactory {

    private DriverFactory() {
    }

    public static AndroidDriver newDriver() {
        if (Project.isBrowserstack()) {
            return browserstackDriver();
        }
        return localDriver();
    }

    private static AndroidDriver localDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(Project.CONFIG.platformName())
                .setPlatformVersion(Project.CONFIG.platformVersion())
                .setDeviceName(Project.CONFIG.deviceName())
                .setAutomationName(Project.CONFIG.automationName())
                .setAppPackage(Project.CONFIG.appPackage())
                .setNewCommandTimeout(java.time.Duration.ofSeconds(120))
                // first-run флоу - переустанавливаем APK каждую сессию,
                // чтобы language-диалог показывался заново
                .setFullReset(true);

        String activity = Project.CONFIG.appActivity();
        if (activity != null && !activity.isBlank()) {
            options.setAppActivity(activity);
        }

        String appPath = Project.CONFIG.appPath();
        if (appPath != null && !appPath.isBlank()) {
            File apk = new File(appPath);
            if (!apk.exists()) {
                throw new IllegalStateException(
                        "APK not found at '" + apk.getAbsolutePath() + "'. " +
                        "Download hh.apk and place it there (see README).");
            }
            options.setApp(apk.getAbsolutePath());
        }

        return new AndroidDriver(url(Project.CONFIG.appiumUrl()), options);
    }

    private static AndroidDriver browserstackDriver() {
        String user = Project.CONFIG.browserstackUsername();
        String key = Project.CONFIG.browserstackAccessKey();
        String app = Project.CONFIG.browserstackAppUrl();
        if (user.isBlank() || key.isBlank() || app.isBlank()) {
            throw new IllegalStateException(
                    "BrowserStack credentials missing. Set environment variables:\n" +
                    "  BROWSERSTACK_USERNAME, BROWSERSTACK_ACCESS_KEY, BROWSERSTACK_APP_URL");
        }

        Map<String, Object> bsOptions = new HashMap<>();
        bsOptions.put("userName", user);
        bsOptions.put("accessKey", key);
        bsOptions.put("projectName", Project.CONFIG.projectName());
        bsOptions.put("buildName", Project.CONFIG.buildName());
        bsOptions.put("sessionName", Project.CONFIG.sessionName());
        bsOptions.put("appiumVersion", "2.6.0");
        bsOptions.put("debug", true);
        bsOptions.put("video", true);

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(Project.CONFIG.platformName())
                .setPlatformVersion(Project.CONFIG.platformVersion())
                .setDeviceName(Project.CONFIG.deviceName())
                .setAutomationName(Project.CONFIG.automationName())
                .setApp(app);
        options.setCapability("bstack:options", bsOptions);

        return new AndroidDriver(url(Project.CONFIG.appiumUrl()), options);
    }

    private static URL url(String s) {
        try {
            return URI.create(s).toURL();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid Appium URL: " + s, e);
        }
    }
}
