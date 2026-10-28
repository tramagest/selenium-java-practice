package testcases;

import common.Utilities.Log;
import dataObjects.InvalidData;
import dataObjects.Message;
import dataObjects.ValidData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.*;
import pageObjects.guerrilamaill.EmailPage;

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

    @Test(description = "TC01: User can log into Railway with valid username and password")
    public void TC01_UserCanLoginWithValidAccount() {

        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Click on Login tab");
        basePage.gotoLoginPage();

        Log.step("3. Enter valid Email and Password");
        Log.step("4. Click on Login button");
        loginPage.login(ValidData.EMAIL.getValue(), ValidData.PASSWORD.getValue());

        Log.verify(
                softAssert,
                "Welcome message",
                Message.WELCOME_USER.format(ValidData.EMAIL.getValue()),
                basePage.getWelcomeMessage()
        );

        softAssert.assertAll();
    }

    @Test(description = "TC02: User cannot login with blank Username textbox")
    public void TC02_UserCannotLoginWithBlankUsername() {
        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Click on Login tab");
        basePage.gotoLoginPage();

        Log.step("3. Enter Password and leave Username blank");
        Log.step("4. Click on Login button");
        loginPage.login(InvalidData.EMPTY.getValue(), ValidData.PASSWORD.getValue());

        Log.verify(
                softAssert,
                "Blank username login error message",
                Message.LOGIN_BLANK_USERNAME_ERROR.getText(),
                loginPage.getLoginErrorMessage()
        );

        Log.verify(
                softAssert,
                "Welcome message displayed",
                false,
                basePage.isWelcomeUserMessageDisplayed(ValidData.EMAIL.getValue())
        );

        softAssert.assertAll();
    }

    @Test(description = "TC03: User cannot log into Railway with invalid password")
    public void TC03_UserCannotLogIntoRailwayWithInvalidPassword() {
        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Click on Login tab");
        basePage.gotoLoginPage();

        Log.step("3. Enter valid Email and invalid Password");
        Log.step("4. Click on Login button");
        loginPage.login(
                ValidData.EMAIL.getValue(),
                InvalidData.INVALID_PASSWORD.getValue()
        );

        Log.verify(softAssert,
                "Invalid password login error message",
                Message.LOGIN_FORM_ERROR.getText(),
                loginPage.getLoginErrorMessage());

        softAssert.assertAll();
    }

    @Test(description = "TC04: System shows message when user enters invalid password many times")
    public void TC04_SystemShowsMessageWhenUserEntersInvalidPasswordManyTimes() {
        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Click on Login tab");
        basePage.gotoLoginPage();

        Log.step("3. Enter valid Email and invalid Password");
        Log.step("4. Click on Login button");
        loginPage.login(
                ValidData.EMAIL.getValue(),
                InvalidData.INVALID_PASSWORD.getValue()
        );

        Log.verify(
                softAssert,
                "Wrong password login error message",
                Message.LOGIN_WRONG_PASSWORD_ERROR.getText(),
                loginPage.getLoginErrorMessage()
        );

        Log.step("5. Repeat the login attempt three more times");

        loginPage.loginWithManyTimes(
                3,
                ValidData.EMAIL.getValue(),
                InvalidData.INVALID_PASSWORD.getValue()
        );

        Log.verify(
                softAssert,
                "Login attempts warning",
                Message.LOGIN_ATTEMPTS_WARNING.getText(),
                loginPage.getLoginErrorMessage()
        );

        softAssert.assertAll();
    }

    @Test(description = "TC05: User can't login with an account that hasn't been activated")
    public void TC05_UserCannotLoginWithAnAccountHasntBeenActivated() {
        SoftAssert softAssert = new SoftAssert();

        basePage.navigatetoGuerrilamail();
        emailPage.setUseAlias(false);

        String email = emailPage.getEmailAddress();

        basePage.openNewTab();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        basePage.gotoRegisterPage();
        registerPage.register(
                email,
                ValidData.PASSWORD.getValue(),
                ValidData.PASSWORD.getValue(),
                ValidData.PID.getValue()
        );

        Log.step("2. Click on Login tab");
        basePage.gotoLoginPage();

        Log.step("3. Enter credentials of an account that hasn't been activated");
        Log.step("4. Click on Login button");
        loginPage.login(email, ValidData.PASSWORD.getValue());

        Log.verify(
                softAssert,
                "Login error message for an unactivated account",
                Message.LOGIN_NOT_ACTIVATED_ERROR.getText(),
                loginPage.getLoginErrorMessage()
        );

        softAssert.assertAll();
    }
}