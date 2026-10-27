package testcases;

import common.Utilities.Log;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.*;
import pageObjects.guerrilamaill.EmailPage;
import org.testng.annotations.BeforeMethod;

public class LoginTest extends BaseTest {

    protected BasePage basePage;
    protected LoginPage loginPage;
    protected EmailPage emailPage;
    protected RegisterPage registerPage;

    @BeforeMethod
    public void initPageObjects() {
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        emailPage = new EmailPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test (description = "TC01: User can log into Railway with valid username and password")
    public void TC01_UserCanLoginWithValidAccount() {

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Click on \"Login\" tab");

        basePage.gotoLoginPage();

        Log.step("3. Enter valid Email and Password");
        Log.step("4. Click on \"Login\" button");

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Log.info("User is logged into Railway. Welcome user message is displayed.");

        Assert.assertTrue(
                Log.verify(
                        "Welcome message",
                        true,
                        basePage.verifyWelcomeMessage("Welcome " + Constant.USERNAME)
                ),
                "Welcome message is not displayed. User might not be logged in."
        );
    }


    @Test (description = "TC02: User cannot login with blank \"Username\" textbox")
    public void TC02_UserCannotLoginWithBlankUsername() {

        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Click on \"Login\" tab");

        basePage.gotoLoginPage();

        Log.step("3. Enter \"Password\" textbox and leave \"Username\" textbox blank");
        Log.step("4. Click on \"Login\" button");

        loginPage.login("", Constant.PASSWORD);

        Log.info("Verify message  \"There was a problem with your login and/or errors exist in your form. \" appears");


        softAssert.assertEquals(  loginPage.getLoginErrorMessage(),
              "There was a problem with your login and/or errors exist in your form. ",
              "Message is displayed incorrectly");


        Assert.assertFalse(basePage.isWelcomeUserMessageDisplayed(), "Welcome message is displayed. User might be logged in.");

        softAssert.assertAll();
    }

    @Test (description = "TC03: User cannot log into Railway with invalid password ")

    public void TC03_UserCannotLogIntoRailwayWithInvalidPassword() {

        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Click on \"Login\" tab");

        basePage.gotoLoginPage();

        Log.step("3. Enter valid Email and invalid Password");
        Log.step("4. Click on \"Login\" button");

        loginPage.login(Constant.USERNAME, "1234567899");

        Log.info("Verify message \"There was a problem with your login and/or errors exist in your form.\" appears");

        softAssert.assertEquals(loginPage.getLoginErrorMessage(), "There was a problem with your login and/or errors exist in your form.","Message is displayed incorrectly");

        softAssert.assertAll();
    }

    @Test (description = "TC04: System shows message when user enters wrong password many times")
    public void TC04_SystemShowsMessageWhenUserEntersWrongPasswordManyTimes() {

        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Click on \"Login\" tab");

        basePage.gotoLoginPage();

        Log.step("3. Enter \"Username\" textbox and leave \"Password\" textbox blank");
        Log.step("4. Click on \"Login\" button");

        loginPage.login(Constant.USERNAME, Constant.INVALIDPASSWORD);

        Log.info("Verify message \"Invalid username or password. Please try again\" appears");

        softAssert.assertEquals(loginPage.getLoginErrorMessage(), "Invalid username or password. Please try again","Message is displayed incorrectly");

        Log.step("5. Repeat step 3 and 4 three more times.");

        loginPage.loginWithManyTimes(3, Constant.USERNAME, Constant.INVALIDPASSWORD);

        Log.info("Verify message  \"You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.\" appears");

        softAssert.assertEquals(loginPage.getLoginErrorMessage(), "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.","Message is displayed incorrectly");

        softAssert.assertAll();

    }

    @Test (description = "TC05: User can't login with an account hasn't been activated")
    public void TC05_UserCannotLoginWithAnAccountHasntBeenActivated() {

        SoftAssert softAssert = new SoftAssert();

        basePage.navigatetoGuerrilamail();

        emailPage.setUseAlias(false);

        String email = emailPage.getEmailAddress();

        basePage.openNewTab();

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        basePage.gotoRegisterPage();
        registerPage.register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        Log.step("2. Click on \"Login\" tab");

        basePage.gotoLoginPage();

        Log.step("3. Enter username and password of account hasn't been activated.");
        Log.step("4. Click on \"Login\" button");

        loginPage.login(email, Constant.PASSWORD);

        Log.info("Verify message \"Invalid username or password. Please try again.\" appears");

        softAssert.assertEquals(loginPage.getLoginErrorMessage(), "Invalid username or password. Please try again.","Message is displayed incorrectly");

        softAssert.assertAll();

    }


}
