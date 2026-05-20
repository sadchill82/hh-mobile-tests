package guru.qa.hhmob.config;

import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

public final class Project {

    public static final MobileConfig CONFIG = readConfig();

    private Project() {
    }

    private static MobileConfig readConfig() {
        Map<String, String> ctx = new HashMap<>();
        ctx.put("env", System.getProperty("env", "local"));
        MobileConfig cfg = ConfigFactory.create(MobileConfig.class, ctx);
        System.err.println("[hh-mobile-tests] Loaded config: env=" + ctx.get("env")
                + " appiumUrl=" + cfg.appiumUrl()
                + " deviceName=" + cfg.deviceName()
                + " appPath=" + cfg.appPath());
        return cfg;
    }

    public static boolean isBrowserstack() {
        return "browserstack".equalsIgnoreCase(System.getProperty("env", "local"));
    }
}
