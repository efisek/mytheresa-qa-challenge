package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AppwritePage extends BasePage {

    public AppwritePage(Page page){
        super(page);
        this.title = page.locator("//a[@class='Link--primary v-align-middle no-underline h4 js-navigation-open markdown-title']");
        this.createdDate = page.locator("relative-time");
        this.author = page.locator("//a[@data-octo-click='hovercard-link-click']");
        this.nextButton = page.locator("//a[@class='next_page']").first();
        this.botName=getBotName();
    }

    private final Locator title;
    private final Locator createdDate;
    private final Locator author;
    private final Locator nextButton;
    private final String botName;

    public Locator getNextButton() {
        return nextButton;
    }

    public Locator getTitle() {
        return title;
    }

    public Locator getCreatedDate() {
        return createdDate;
    }

    public Locator getAuthor() {
        return author;
    }

    public String getBotName() {
        return botName;
    }

}
