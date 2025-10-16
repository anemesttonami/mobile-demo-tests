package tests.android;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.AndroidDriverProvider;
import drivers.BrowserStackDriverProvider;
import drivers.Config;
import helpers.AppiumServerManager;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class BaseTest {
    private static final Config CONFIG = ConfigFactory.create(Config.class, System.getProperties());
    private static final boolean ENV = "emulator".equals(CONFIG.env());

    @BeforeAll
    static void beforeAll() throws IOException, InterruptedException {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.browserSize = null;
        Configuration.timeout = 30000;
        if (ENV) {
            AppiumServerManager.ensureUiautomator2Installed();
            AppiumServerManager.startEmulator(CONFIG.device());
            AppiumServerManager.startServer();
            Configuration.browser = AndroidDriverProvider.class.getName();
        } else {
            Configuration.browser = BrowserStackDriverProvider.class.getName();
        }
    }

    @BeforeEach
    void beforeEach() {
        open();
    }

    @AfterEach
    void afterEach() {
        if (!"emulator".equals(CONFIG.os())) {
            String sessionId = Selenide.sessionId().toString();
            Attach.addVideo(sessionId);
        }
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        closeWebDriver();
    }

    @AfterAll
    static void afterAll() {
        if ("emulator".equals(CONFIG.os())) {
            AppiumServerManager.stopServer();
        }
    }
}
