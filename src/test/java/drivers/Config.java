package drivers;

@org.aeonbits.owner.Config.Sources({"classpath:${os}/${env}.properties"})
public interface Config extends org.aeonbits.owner.Config {

    @DefaultValue("android")
    String os();

    @DefaultValue("11.0")
    @Key("osVersion")
    String osVersion();

    @DefaultValue("emulator")
    String env();

    @DefaultValue("Pixel_4")
    String device();

    @Key("browserstackApp")
    String browserstackApp();

    @DefaultValue("Mobile Autotests")
    String browserstackProjectName();

    @Key("browserstack.user")
    String browserstackLogin();

    @Key("browserstack.key")
    String browserstackPass();

    @DefaultValue("https://hub.browserstack.com/wd/hub")
    String browserstackUrl();

    @DefaultValue("http://localhost:4723/wd/hub")
    String appiumServerUrl();
}
