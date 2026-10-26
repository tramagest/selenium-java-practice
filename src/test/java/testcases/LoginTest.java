package testcases;

import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.RegisterPage;
import pageObjects.guerrilamaill.EmailPage;

public class LoginTest extends BaseTest {

    @Test
    public void TC01_UserCanLoginWithValidAccount() {

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Assert.assertTrue(basePage.verifyWelcomeMessage("Welcome " + Constant.USERNAME), "Welcome message is not displayed. User might not be logged in.");

    }


    @Test
    public void TC02_UserCannotLoginWithBlankUsername() {

        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login("", Constant.PASSWORD);

        softAssert.assertTrue(loginPage.verifyLoginErrorMessage("There was a problem with your login and/or errors exist in your form. "));
        Assert.assertFalse(basePage.isWelcomeUserMessageDisplayed(), "Welcome message is displayed. User might be logged in.");

        softAssert.assertAll();
    }

    @Test

    public void TC03_UserCannotLogIntoRailwayWithInvalidPassword() {

        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, "1234567899");

        softAssert.assertTrue(loginPage.verifyLoginErrorMessage("There was a problem with your login and/or errors exist in your form."));

        softAssert.assertAll();
    }

    @Test
    public void TC04_SystemShowsMessageWhenUserEntersWrongPasswordManyTimes() {

        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, "invalidpassword");

        softAssert.assertTrue(loginPage.verifyLoginErrorMessage("Invalid username or password. Please try again"));

        loginPage.loginWithManyTimes(3, Constant.USERNAME, Constant.PASSWORD);

        softAssert.assertTrue(loginPage.verifyLoginErrorMessage("You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes."));

        softAssert.assertAll();

    }

    @Test
    public void TC05_UserCannotLoginWithAnAccountHasntBeenActivated() {

        SoftAssert softAssert = new SoftAssert();

        EmailPage emailPage = new EmailPage(driver);
        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        basePage.navigatetoRailway();

        emailPage.setUseAlias(false);

        String email = emailPage.getEmailAddress();

        driver.switchTo().newWindow(WindowType.TAB);

        basePage.navigatetoRailway();

        basePage.gotoRegisterPage();
        registerPage.register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        basePage.gotoLoginPage();

        loginPage.login(email, Constant.PASSWORD);

        softAssert.assertTrue(loginPage.verifyLoginErrorMessage("Invalid username or password. Please try again."));

        softAssert.assertAll();

    }


}
