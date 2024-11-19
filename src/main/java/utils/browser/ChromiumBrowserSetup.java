package utils.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class ChromiumBrowserSetup implements BrowserSetup {
    @Override
    public Page createBrowser(Playwright playwright) {
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        return browser.newContext().newPage();
    }
}
