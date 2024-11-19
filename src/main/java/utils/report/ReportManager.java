package utils.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
    private static ExtentReports extentReports;
    public static ExtentReports createInstance(String fileName) {

        // to create the report directory if it doesn't exist
        File reportDir = new File("./test-output");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String reportFilePath = "test-output/reports/" + fileName + dateName;

        // to create and attach the HTML reporter
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportFilePath);
        htmlReporter.config().setDocumentTitle("Mytheresa Automation Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Automation Test Results");

        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentReports.setSystemInfo("Host Name", "Mytheresa");
        extentReports.setSystemInfo("QA Name", "Emre");
        extentReports.setSystemInfo("Environment", "Prod");

        return extentReports;
    }

    public static void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

}
