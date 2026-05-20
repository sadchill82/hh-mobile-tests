package guru.qa.hhmob.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:config/${env}.properties"
})
public interface MobileConfig extends Config {

    @Key("deviceName")
    @DefaultValue("Pixel_10_Pro_Fold")
    String deviceName();

    @Key("platformName")
    @DefaultValue("Android")
    String platformName();

    @Key("platformVersion")
    @DefaultValue("16")
    String platformVersion();

    @Key("automationName")
    @DefaultValue("UiAutomator2")
    String automationName();

    @Key("appiumUrl")
    @DefaultValue("http://127.0.0.1:4723")
    String appiumUrl();

    @Key("appPath")
    @DefaultValue("src/test/resources/apps/hh.apk")
    String appPath();

    @Key("appPackage")
    @DefaultValue("ru.hh.android")
    String appPackage();

    @Key("appActivity")
    @DefaultValue("")
    String appActivity();

    // BrowserStack-only
    @Key("BROWSERSTACK_USERNAME")
    @DefaultValue("")
    String browserstackUsername();

    @Key("BROWSERSTACK_ACCESS_KEY")
    @DefaultValue("")
    String browserstackAccessKey();

    @Key("BROWSERSTACK_APP_URL")
    @DefaultValue("")
    String browserstackAppUrl();

    @Key("projectName")
    @DefaultValue("hh-mobile-tests")
    String projectName();

    @Key("buildName")
    @DefaultValue("local")
    String buildName();

    @Key("sessionName")
    @DefaultValue("session")
    String sessionName();
}
