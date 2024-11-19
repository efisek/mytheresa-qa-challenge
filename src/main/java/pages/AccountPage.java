package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccountPage extends BasePage {
    public AccountPage(Page page) {
        super(page);
        this.usernameField = page.locator("#username"); // Locate username input
        this.passwordField = page.locator("#password"); // Locate password input
        this.loginButton = page.locator("//input[@type='submit']"); // Locate login button
    }
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;

    public Locator getUsernameField() {
        return usernameField;
    }

    public Locator getPasswordField() {
        return passwordField;
    }

    public Locator getLoginButton() {
        return loginButton;
    }

    public String getWelcomeMessage() {
        return "Welcome, testUser!";
    }
}
