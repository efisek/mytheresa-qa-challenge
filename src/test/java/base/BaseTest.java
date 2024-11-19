package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import config.Config;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pages.AboutPage;
import pages.AccountPage;
import pages.AppwritePage;
import utils.browser.BrowserFactory;
import utils.report.ReportManager;

import java.nio.file.Paths;

public class BaseTest {
    protected Page page;
    protected Browser browser;
    protected Playwright playwright;
    protected ExtentTest test;
    protected ExtentReports extentReports;
    protected BrowserFactory browserFactory;
    protected AccountPage accountPage;
    protected AboutPage aboutPage;
    protected AppwritePage appwritePage;

    @BeforeSuite
    public void beforeSuite() {
        extentReports=ReportManager.createInstance("ExtentReport.html");
    }
    @BeforeMethod
    public void setup(ITestResult result) {
        browserFactory = new BrowserFactory();
        page = browserFactory.initBrowser("chromium"); //firefox and webkit browsers can be passed
        navigateToPage("");
        test = extentReports.createTest(result.getMethod().getMethodName());

        aboutPage = new AboutPage(page);
        accountPage = new AccountPage(page);
        appwritePage = new AppwritePage(page);

    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel("Test Failed: " + result.getName(), ExtentColor.RED));
            captureScreenshotOnFailure(result.getMethod().getMethodName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel("Test Passed " + result.getName(),ExtentColor.GREEN));
        } else {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
        }
        if (page != null) page.context().close();
        if (playwright != null) playwright.close();
        tearDown();
    }

    @AfterSuite
    public void afterSuite(){
        ReportManager.flush();
    }

    public void captureScreenshotOnFailure(String testName){
        try {
            String screenshotPath = "test-output/screenshots/" + testName + ".png";
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath))
                    .setFullPage(true));
            System.out.println("Screenshot taken: " + screenshotPath);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    public void tearDown() {
        // Close the browser and Playwright
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    public void navigateToPage(String path){
        String url=Config.getBaseUrl();
        page.navigate(url+path);
    }

}