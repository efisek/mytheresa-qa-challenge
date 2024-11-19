package utils.browser;

import com.microsoft.playwright.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BrowserFactory {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private static final Map<String, Supplier<BrowserSetup>> browsers = new HashMap<>();

    static {
        browsers.put("chrome", ChromiumBrowserSetup::new);
        browsers.put("firefox", FirefoxBrowserSetup::new);
        browsers.put("webkit", WebkitBrowserSetup::new);
    }

    public Page initBrowser(String browserType) {
        playwright = Playwright.create();

        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
        }

        context = browser.newContext();
        return context.newPage();
    }
}
