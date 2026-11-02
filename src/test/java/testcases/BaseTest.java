package testcases;

import common.Utilities.DriverManager;
import dataObjects.Ticket;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected Ticket ticket;

    @Parameters("browser")
    @BeforeMethod
    public void setBrowser(@Optional("Chrome") String browser) {

        switch (browser.toLowerCase()) {
            case "chrome" -> driver = new ChromeDriver();
            case "edge"   -> driver = new EdgeDriver();
            default -> throw new IllegalArgumentException("Invalid browser value: " + browser);
        }

        DriverManager.setDriver(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DriverManager.unload();
            ticket = null;
            driver = null;
        }
    }
}