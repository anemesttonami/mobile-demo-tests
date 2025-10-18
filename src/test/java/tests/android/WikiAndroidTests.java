package tests.android;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.id;
import static io.qameta.allure.Allure.step;


@Tag("android")
@Tag("wiki")
public class WikiAndroidTests extends BaseTest {

    private final SelenideElement search = $(id("org.wikipedia.alpha:id/search_container")),
            searchInput = $(id("org.wikipedia.alpha:id/search_src_text")),
            resultPageHeaderImg = $(id("org.wikipedia.alpha:id/view_page_header_image"));

    private final ElementsCollection searchResults = $$(id("org.wikipedia.alpha:id/page_list_item_title"));
    private final String textForSearch = "Sagrada Família";

    @Test
    @Tag("regress")
    void isSearchClickableAndHisElementsVisible() {
        step("Поиск видно и он кликабелен.", () -> search.shouldBe(visible).shouldHave(attribute("clickable", "true")));
        step("Кнопка поиска видна.", () -> $(accessibilityId("Search Wikipedia")).should(visible));
        step("Плейсхолдер \"Search Wikipedia\" видно.", () ->
                $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Search Wikipedia\")")).shouldBe(visible));
        step("Кнопка \"Voice input\" видна и кликабельна.", () -> $(accessibilityId("Voice input search")).should(visible)
                .shouldHave(attribute("clickable", "true")));
    }

    @Test
    @Tag("smoke")
    void canSearchSmth() {
        step("Вводим текст в поиск.", () -> {
            search.click();
            searchInput.sendKeys(textForSearch);
        });
        step("Введённый текст отображается в строке поиска.", () -> searchInput.shouldBe(visible));
        step("Отобразились какие-то результаты поиска.", () -> {
            searchResults.first().shouldBe(visible);
            Assertions.assertFalse(searchResults.isEmpty());
        });
        step("Проверяем, что у первого найденного элемента содержится запрашиваемый текст.",
                () -> searchResults.first().shouldHave(attribute("text", textForSearch)));
        step("Переходим на страницу первого из результатов.", () -> searchResults.first().click());
        step("Проверяем, что переход успешен", () -> resultPageHeaderImg.shouldBe(visible));
    }
}