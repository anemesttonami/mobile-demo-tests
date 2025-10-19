package tests.android;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.AndroidDriverProvider;
import drivers.BrowserStackDriverProvider;
import drivers.Config;
import enums.Env;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static enums.Env.getCurrentEnv;
import static helpers.BrowserStack.skipOnboardingScreen;

public class BaseTest {

    private static final Config CONFIG = ConfigFactory.create(Config.class, System.getProperties());
    private static final Env ENV = getCurrentEnv(CONFIG);

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.browserSize = null;
        Configuration.timeout = 15000;

        switch (ENV) {
            case LOCAL_ANDROID_EMULATOR -> Configuration.browser = AndroidDriverProvider.class.getName();
            case BROWSER_STACK -> {
                Configuration.screenshots = false;
                Configuration.browser = BrowserStackDriverProvider.class.getName();
            }
        }
    }

    @BeforeEach
    void beforeEach() {
        open();
        skipOnboardingScreen();
    }

    @AfterEach
    void afterEach() {
        switch (ENV) {
            case LOCAL_ANDROID_EMULATOR -> Attach.screenshotAs("Screenshot before closing driver.");
            case BROWSER_STACK -> {
                String sessionId = Selenide.sessionId().toString();
                Attach.addVideo(sessionId, CONFIG);
            }
        }
        Attach.pageSource();
        closeWebDriver();
    }
}
