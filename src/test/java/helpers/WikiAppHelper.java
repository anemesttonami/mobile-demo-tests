package helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.id;

@Getter
public class WikiAppHelper {

    private final SelenideElement search = $(id("org.wikipedia.alpha:id/search_container")),
            searchInput = $(id("org.wikipedia.alpha:id/search_src_text")),
            resultPageHeaderImg = $(id("org.wikipedia.alpha:id/view_page_header_image")),
            exploreTab = $(accessibilityId("Explore")),
            savedTab = $(accessibilityId("Saved")),
            searchTab = $(accessibilityId("Search")),
            activityTab = $(accessibilityId("Activity")),
            editsTab = $(accessibilityId("Edits")),
            moreTab = $(accessibilityId("More")),
            closeWikiGamesPopup = $(accessibilityId("Close"));

    private final ElementsCollection searchResults = $$(id("org.wikipedia.alpha:id/page_list_item_title"));
    private final String textForSearch = "Sagrada Família";

    @Step("Закрываем иногда всплывающий popup wikipedia games.")
    public void closeWikiGamesPopup() {
        try {
            closeWikiGamesPopup.shouldBe(Condition.visible).click();
        } catch (ElementNotFound ignored) {
        }
    }
}
