package drivers;

import com.codeborne.selenide.WebDriverProvider;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BrowserStackDriverProvider implements WebDriverProvider {

    private final MutableCapabilities caps = new MutableCapabilities();
    private final Config config = ConfigFactory.create(Config.class, System.getProperties());

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        caps.merge(capabilities);
        caps.setCapability("browserstack.user", config.browserstackLogin());
        caps.setCapability("browserstack.key", config.browserstackPass());
        caps.setCapability("app", config.browserstackApp());
        caps.setCapability("device", config.device());
        caps.setCapability("os_version", config.osVersion());
        caps.setCapability("project", config.browserstackProjectName());
        caps.setCapability("build", "build from " + LocalDate.now().toString());
        caps.setCapability("name", "test from " + LocalDateTime.now().toString());

        try {
            return new RemoteWebDriver(new URL(config.browserstackUrl()), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
