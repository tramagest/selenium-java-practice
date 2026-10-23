
package Exercise3;

import pageObjects.Railway.BasePage;
import pageObjects.Railway.RegisterPage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.BookTicketPage;
import common.Constant.Constant;
import testcases.BaseTest;
import pageObjects.guerrilamaill.EmailPage;
import org.testng.annotations.Test;
import org.testng.Assert;

public class Exercise3 extends BaseTest {

    @Test
    public void Exercise3() {

        driver.get(Constant.RAILWAY_URL);

        // 1.Open Guerrilla Mail in the first tab
        EmailPage emailPage = new EmailPage(driver);
        driver.get(Constant.GUERILLAMAIL_URL);

        String mailTab = driver.getWindowHandle();

        // 2.Ensure Use Alias is unchecked
        emailPage.setUseAlias(false);

        // 3.Get the temporary email
        String email = emailPage.getEmailAddress();
        System.out.println("Temporary email: " + email);

        // 4.Open Railway in a new tab
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);

        String railwayTab = driver.getWindowHandle();

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = basePage.gotoRegisterPage();



        registerPage.register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        System.out.println("Registration form submitted: " + email);

        LoginPage loginPage = basePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        BookTicketPage bookTicketPage = basePage.gotoBookTicketPage();
        bookTicketPage.bookTicket(null, null, "Phan Thiết", null, 2);

        Assert.assertEquals(bookTicketPage.getSuccessBookTicketMessage(), "Ticket booked successfully!");

        Assert.assertEquals(bookTicketPage.getArriveStation(), "Phan Thiết");
        Assert.assertEquals(bookTicketPage.getAmount(), "2");


    }
}
