package utils.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class WebkitBrowserSetup implements BrowserSetup{
    @Override
    public Page createBrowser(Playwright playwright) {
        Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        return browser.newContext().newPage();
    }
}
