package tests.android;

import helpers.WikiAppHelper;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.qameta.allure.Allure.step;


@Tag("android")
@Tag("wiki")
public class WikiAndroidTests extends BaseTest {

    private final WikiAppHelper helper = new WikiAppHelper();

    @Test
    @Tag("regress")
    @DisplayName("Все элементы поиска на странице \"Explore\" присутствуют.")
    void isSearchClickableAndHisElementsVisible() {
        step("Поиск видно.", () -> helper.getSearch().shouldBe(visible));
        step("Поиск кликабелен.", () -> helper.getSearch().shouldHave(attribute("clickable", "true")));
        step("Кнопка поиска видна.", () -> $(accessibilityId("Search Wikipedia")).should(visible));
        step("Плейсхолдер \"Search Wikipedia\" видно.", () ->
                $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Search Wikipedia\")")).shouldBe(visible));
        step("Кнопка \"Voice input\" видна и кликабельна.", () -> $(accessibilityId("Voice input search")).should(visible)
                .shouldHave(attribute("clickable", "true")));
    }

    @Test
    @Tag("smoke")
    @DisplayName("Выполнение поиска со страницы \"Explore\" и переход на страницу результата поиска.")
    void canSearchSmth() {
        step("Вводим текст в поиск.", () -> {
            helper.getSearch().click();
            helper.getSearchInput().sendKeys(helper.getTextForSearch());
        });
        step("Введённый текст отображается в строке поиска.", () -> helper.getSearchInput().shouldBe(visible));
        step("Отобразились какие-то результаты поиска.", () -> {
            helper.getSearchResults().first().shouldBe(visible);
            Assertions.assertFalse(helper.getSearchResults().isEmpty());
        });
        step("Проверяем, что у первого найденного элемента содержится запрашиваемый текст.",
                () -> helper.getSearchResults().first().shouldHave(attribute("text", helper.getTextForSearch())));
        step("Переходим на страницу первого из результатов.", () -> helper.getSearchResults().first().click());

        helper.closeWikiGamesPopup();

        step("Проверяем, что переход успешен", () -> helper.getResultPageHeaderImg().shouldBe(visible));
    }

    @Test
    @Tag("smoke")
    @DisplayName("Отображение вкладок на tab bar.")
    void areTabBarElementsVisible() {
        step("Вкладки на tab bar видны и кликабельны, кроме \"Explore\", \"Explore\" не кликабелен, потому что выбран.", () -> {
            step("\"Explore\"", () -> helper.getExploreTab().shouldBe(visible).shouldHave(attribute("clickable", "false")));
            step("\"Saved\"", () -> helper.getSavedTab().shouldBe(visible).shouldHave(attribute("clickable", "true")));
            step("\"Search\"", () -> helper.getSearchTab().shouldBe(visible).shouldHave(attribute("clickable", "true")));
            step("\"Activity\" или \"Edits\". " +
                    "\"Activity\", когда поиска еще ни разу не было." +
                    "\"Edits\" появляется вместо \"Activity\", когда поиска уже совершался ранее в приложении.", () -> {
                boolean isActivityOrEditsVisibleAndClickable =
                        (helper.getActivityTab().is(visible) && helper.getActivityTab().is(have(attribute("clickable", "true"))))
                                || (helper.getEditsTab().is(visible) && helper.getEditsTab().is(have(attribute("clickable", "true"))));

                Assertions.assertTrue(isActivityOrEditsVisibleAndClickable, "Ни \"Activity\", ни \"Edits\" не видны и кликабельны одновременно.");
            });
            step("\"More\"", () -> helper.getMoreTab().shouldBe(visible).shouldHave(attribute("clickable", "true")));
        });
    }
}