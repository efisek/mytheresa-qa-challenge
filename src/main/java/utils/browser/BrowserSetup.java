package utils.browser;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public interface BrowserSetup {
    Page createBrowser(Playwright playwright);
}
