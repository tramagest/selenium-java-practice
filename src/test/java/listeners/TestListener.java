package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import common.Utilities.ExtentManager;
import common.Utilities.Log;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testcases.BaseTest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(getTestName(result), result.getMethod().getDescription());
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        Log.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable error = result.getThrowable();
        WebDriver driver = getDriver(result);

        String details = "<b>Error:</b> " + escape(error.getMessage())
                + "<br><b>Failed at:</b> " + findFailedLocation(error)
                + "<br><b>Page URL:</b> " + getCurrentUrl(driver);

        Log.get().fail(details);

        String screenshot = takeScreenshotBase64(driver);

        if (screenshot != null) {

            Log.get().fail(error,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot, "Screenshot when failed").build());

            saveScreenshot(screenshot, getTestName(result));
        } else {
            Log.get().fail(error);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (Log.get() == null) {
            onTestStart(result);
        }
        if (result.getThrowable() != null) {
            Log.get().skip(result.getThrowable());
        } else {
            Log.get().skip("Test skipped");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private String getTestName(ITestResult result) {
        Object[] params = result.getParameters();
        if (params.length > 0 && params[0] instanceof String) {
            return (String) params[0];
        }
        return result.getMethod().getMethodName();
    }

    private WebDriver getDriver(ITestResult result) {
        Object instance = result.getInstance();
        if (instance instanceof BaseTest) {
            return ((BaseTest) instance).getDriver();
        }
        return null;
    }

    private String getCurrentUrl(WebDriver driver) {
        if (driver == null) {
            return "N/A";
        }
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            return "N/A";
        }
    }


    private String findFailedLocation(Throwable error) {
        for (StackTraceElement e : error.getStackTrace()) {
            String cls = e.getClassName();
            if (cls.startsWith("testcases.") || cls.startsWith("pageObjects.") || cls.startsWith("Exercise")) {
                return cls + "." + e.getMethodName() + "() - " + e.getFileName() + ":" + e.getLineNumber();
            }
        }
        return "N/A";
    }

    private String escape(String text) {
        if (text == null) {
            return "N/A";
        }
        return text.replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>");
    }


    private String takeScreenshotBase64(WebDriver driver) {
        if (driver == null) {
            return null;
        }
        try {
            try {
                ((JavascriptExecutor) driver).executeScript(
                        "window.scrollTo(0, Math.max(document.body.scrollHeight, document.documentElement.scrollHeight));");
            } catch (Exception ignored) {

            }
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            return null;
        }
    }


    private void saveScreenshot(String base64, String testName) {
        try {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName.replaceAll("[^a-zA-Z0-9_-]", "_") + "_" + time + ".png";
            Files.write(Paths.get(ExtentManager.SCREENSHOT_DIR, fileName), Base64.getDecoder().decode(base64));
        } catch (Exception e) {

        }
    }
}