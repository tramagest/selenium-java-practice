
package Exercise3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.RegisterPage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.BookTicketPage;
import common.Constant.Constant;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Exercise3 {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        Constant.WEBDRIVER = driver;

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );


            // Step 1: Open Guerrilla Mail in the first tab
        driver.get("https://www.guerrillamail.com/inbox");

        String mailTab = driver.getWindowHandle();

            // Step 2: Ensure Use Alias is unchecked
        By aliasCheckbox = By.xpath("//*[@id='use-alias']");

        WebElement checkbox = wait.until(
                    ExpectedConditions.elementToBeClickable(aliasCheckbox)
            );

        if (checkbox.isSelected()) {
                checkbox.click();
            }

            // Step 3: Get the temporary email
        By emailLocator = By.xpath("//*[@id='email-widget']");

        WebElement emailElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(emailLocator)
            );

        String email = emailElement.getAttribute("value");

        if (email == null || email.isBlank()) {
                email = emailElement.getText();
            }



        email = email.trim();
        System.out.println("Temporary email: " + email);

        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get("http://saferailway.somee.com/");

        String railwayTab = driver.getWindowHandle();

        BasePage basePage = new BasePage();
        RegisterPage registerPage = basePage.gotoRegisterPage();


        String password = "123456789";
        String pid = "123456789";


        registerPage.register(email, password, pid);

        System.out.println("Registration form submitted: " + email);

        LoginPage loginPage = basePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        BookTicketPage bookTicketPage = basePage.gotoBookTicketPage();

        try{
        bookTicketPage.bookTicket(null, null, "Phan Thiết", null, "2");
        System.out.println("PASS: Ticket booked successfully.");}
        catch (Exception e){
            System.out.println("Error booking ticket: " + e.getMessage());
        }

        driver.quit();


    }
}
