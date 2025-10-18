package enums;

public enum Env {
    LOCAL_ANDROID_EMULATOR("androidEmulator"),
    BROWSER_STACK("browserStack");

    private final String description;

    Env(String description) {
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
