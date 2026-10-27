package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pageObjects.Railway.MyTicketPage;
import dataObjects.Ticket;




public class BaseTest {

    protected WebDriver driver;
    protected Ticket ticket;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver == null) {
            return;
        }
        try {
            new MyTicketPage(driver).cancelTicket(ticket);
        } finally {
            ticket = null;
            driver.quit();
        }
    }


}




