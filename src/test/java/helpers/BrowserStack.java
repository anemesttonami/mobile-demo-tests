package helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import drivers.Config;

import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.id;
import static io.restassured.RestAssured.given;

public class BrowserStack {

    public static String videoUrl(String sessionId, Config config) {
        String url = String.format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);

        return given()
                .auth()
                .basic(config.browserstackLogin(), config.browserstackPass())
                .get(url)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }

    public static void skipOnboardingScreen() {
        try {
            SelenideElement skipButton = $(id("org.wikipedia.alpha:id/fragment_onboarding_skip_button"));
            skipButton.shouldBe(Condition.visible).click();
        } catch (ElementNotFound ignored) {
        }
    }
}
