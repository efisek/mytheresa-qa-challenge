package base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class BasePage {
    final int DEFAULT_WAIT_TIMEOUT_SECONDS = 10;
    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }


    public void waitForAnElement(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setTimeout(DEFAULT_WAIT_TIMEOUT_SECONDS * 1000)
                .setState(WaitForSelectorState.valueOf("VISIBLE")));
    }

    public void click(Locator locator) {
        waitForAnElement(locator);
        locator.click();
    }

    public void sendInput(Locator locator, String input) {
        waitForAnElement(locator);
        locator.fill(input);
    }

}
