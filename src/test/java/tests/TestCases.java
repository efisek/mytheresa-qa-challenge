package tests;

import base.BaseTest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.Config;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestCases extends BaseTest {
    private final List<String> consoleErrors = new ArrayList<>();

    @Test
    public void consoleErrorTest(){
        captureConsoleError();
        navigateToPage("");
     // "about.html" can be passed to navigateToPage() method to test the about page
        assert consoleErrors.isEmpty() : "Console errors found: " + consoleErrors;
    }

    @Test
    public void checkLinksStatusCodes() {
        // to find all the <a> elements and related href attributes
        List<String> links = (List<String>) page.locator("a[href]").evaluateAll(
                "elements => elements.map(element => element.href)"
        );

        System.out.println("Total links in the page: " + links.size());

        // to store the valid and invalid links
        List<String> validLinks = new ArrayList<>();
        List<String> invalidLinks = new ArrayList<>();

        // for loop to check status codes for each link obtained
        for (String link : links) {
            String url = link.startsWith("http") ? link : page.url() + link;

            APIResponse apiResponse = page.request().get(url);

            int statusCode = apiResponse.status();

            if (statusCode >= 200 && statusCode < 300) {
                validLinks.add(url + " -> " + statusCode);
            } else if (statusCode >= 300 && statusCode < 400) {
                validLinks.add(url + " -> " + statusCode);
            } else if (statusCode >= 400) {
                invalidLinks.add(url + " -> " + statusCode);
            }
        }

        // optional, to check the links on console
        validLinks.forEach(System.out::println);
        invalidLinks.forEach(System.out::println);

        assert invalidLinks.isEmpty() : "Invalid links are: " + invalidLinks;
    }

    @Test
    public void loginTest() {
        navigateToPage("login.html");
        accountPage.sendInput(accountPage.getUsernameField(), Config.getProperty("userName"));
        accountPage.sendInput(accountPage.getPasswordField(), Config.getProperty("password"));
        accountPage.click(accountPage.getLoginButton());
        assert (page.getByText(accountPage.getWelcomeMessage(), new Page.GetByTextOptions().setExact(true))).isVisible();
    }


    @Test
    public void checkPullRequestInfo(){
        page.navigate(Config.getProperty("repoUrl"));
        List<String[]> prDetails=getPullRequests(page);
        writeToCSV(Config.getProperty("csvFilePath"), prDetails);
    }


    // helper methods
    private void captureConsoleError() {
        page.onConsoleMessage(message -> {
            if ("error".equals(message.type())) {
                consoleErrors.add("Console error: " + message.text());
                System.err.println("Console error captured: " + message.text());
            }
        });
    }

    private void writeToCSV(String filePath, List<String[]> data) {
        try (FileWriter csvWriter = new FileWriter(filePath)) {

            csvWriter.append("PR Title, Created Date, Author\n");

            for (String[] row : data) {
                csvWriter.append(String.join(",", row)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String[]> getPullRequests(Page page){
        List<String[]> prDetails = new ArrayList<>();
        List<Locator> pullRequests = page.locator(".js-issue-row").all();

        while (true){
            for (Locator pr : pullRequests) {
                Locator prTitle = pr.locator(appwritePage.getTitle());

                String title;
                if (prTitle.count() > 0) {
                    title = prTitle.textContent().trim();
                } else {
                    title = "No title found";
                }

                Locator locatorDate = pr.locator(appwritePage.getCreatedDate());
                String createdDate;
                if (locatorDate.count() > 0) {
                    createdDate = locatorDate.getAttribute("datetime").trim();
                } else {
                    createdDate = "No date found";
                }

                String author;
                Locator locatorAuthor = pr.locator(appwritePage.getAuthor());
                if (locatorAuthor.isVisible()) {
                    author = locatorAuthor.textContent().trim();
                } else {
                    author = appwritePage.getBotName();
                }

                prDetails.add(new String[]{title, createdDate, author});
            }

            if(!(appwritePage.getNextButton().isVisible()) || "true".equals(appwritePage.getNextButton().getAttribute("next_page disabled"))) {
                break;
            }
            appwritePage.click(appwritePage.getNextButton());
            page.waitForLoadState();
        }
        return prDetails;
    }

}
