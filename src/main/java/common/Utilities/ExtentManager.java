package common.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentManager {

    private static final String REPORT_DIR = System.getProperty("user.dir") + File.separator
            + "test-output" + File.separator + "ExtentReport";

    // Thư mục chứa ảnh chụp khi test fail: test-output/ExtentReport/Screenshots
    public static final String SCREENSHOT_DIR = REPORT_DIR + File.separator + "Screenshots";

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String reportPath = REPORT_DIR + File.separator + "ExtentReport_" + time + ".html";

        new File(REPORT_DIR).mkdirs();
        new File(SCREENSHOT_DIR).mkdirs();

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Safe Railway Automation Report");
        spark.config().setReportName("Safe Railway - Selenium Java Test Report");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
        spark.config().setCss(
                ".featherlight .featherlight-content { width: 95vw !important; max-width: 95vw !important; }"
                        + ".featherlight .featherlight-content img { width: 100% !important; max-width: 100% !important; height: auto !important; }"
        );

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(spark);
        reports.setSystemInfo("Application", "Safe Railway");
        reports.setSystemInfo("Browser", "Chrome");
        reports.setSystemInfo("OS", System.getProperty("os.name"));
        reports.setSystemInfo("Java", System.getProperty("java.version"));
        reports.setSystemInfo("Tester", System.getProperty("user.name"));
        return reports;
    }
}