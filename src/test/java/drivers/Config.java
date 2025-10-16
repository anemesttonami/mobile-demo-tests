package drivers;


@org.aeonbits.owner.Config.Sources({"classpath:${os}/${env}.properties"})
public interface Config extends org.aeonbits.owner.Config {

    @DefaultValue("android")
    String os();

    @DefaultValue("11.0")
    String osVersion();

    @DefaultValue("emulator")
    String env();

    @DefaultValue("Pixel_4")
    String device();

    String browserstackApp();

    @DefaultValue("Mobile Autotests")
    String browserstackProjectName();
    
    String browserstackLogin();
    
    String browserstackPass();

    @DefaultValue("https://hub.browserstack.com/wd/hub")
    String browserstackUrl();

    @DefaultValue("http://localhost:4723/wd/hub")
    String appiumServerUrl();
}
