package enums;

import drivers.Config;

public enum Env {

    LOCAL_ANDROID_EMULATOR("emulator"),
    BROWSER_STACK("browserstack");

    private final String description;

    Env(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    private static Env from(String envValue) {
        for (Env env : values()) {
            if (env.getDescription().equalsIgnoreCase(envValue)) {
                return env;
            }
        }
        throw new IllegalArgumentException("Unknown environment: " + envValue);
    }

    public static Env getCurrentEnv(Config config) {
        String envValue = config.env();
        return from(envValue);
    }
}
