package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.guerrilamaill.EmailPage;
import pageObjects.Railway.RegisterPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;

import common.Constant.Constant;



public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();



    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected String mailTab;

    protected String createActivatedAccount() {


        EmailPage emailPage = new EmailPage(driver);
        driver.get(Constant.GUERILLAMAIL_URL);
        mailTab = driver.getWindowHandle();

        emailPage.setUseAlias(false);
        String email = emailPage.getEmailAddress();
        System.out.println("Temporary email: " + email);


        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);
        new HomePage(driver).clickCreateAccountLink();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        driver.switchTo().window(mailTab);
        emailPage.clickMessageConfirm();

        Set<String> oldTabs = driver.getWindowHandles();
        emailPage.clickLinkConfirmMail();

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.numberOfWindowsToBe(oldTabs.size() + 1));

        for (String tab : driver.getWindowHandles()) {
            if (!oldTabs.contains(tab)) {
                driver.switchTo().window(tab);
                break;
            }
        }



        return email;
    }
}




