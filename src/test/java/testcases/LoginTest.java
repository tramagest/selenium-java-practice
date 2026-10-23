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

        System.out.println("Step 1: Navigate to QA Railway Website");

        driver.get(Constant.RAILWAY_URL);

        System.out.println("Step 2: Click on \"Login\" tab");


        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();

        System.out.println("Step 3. Enter valid Email and Password");
        System.out.println("Step 4. Click on \"Login\" button");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        //Verify User is logged into Railway. Welcome user message is displayed.

        Assert.assertTrue(basePage.verifyWelcomeMessage("Welcome " + Constant.USERNAME), "Welcome message is not displayed. User might not be logged in.");

    }


    @Test
    public void TC02_UserCannotLoginWithBlankUsername() {


        System.out.println("Step 1: Navigate to QA Railway Website");

        driver.get(Constant.RAILWAY_URL);

        System.out.println("Step 2: Click on \"Login\" tab");


        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();

        System.out.println("Step 3. Enter blank Username and valid Password");
        System.out.println("Step 4. Click on \"Login\" button");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", Constant.PASSWORD);

        //Verify error message is displayed
        Assert.assertEquals(loginPage.getLoginErrorMessage(), "There was a problem with your login and/or errors exist in your form. ", "Error message is not displayed correctly.");
        Assert.assertFalse(basePage.isWelcomeUserMessageDisplayed(), "Welcome message is displayed. User might be logged in.");

    }

    @Test

    public void TC03_UserCannotLogIntoRailwayWithInvalidPassword() {

        //1. Navigate to QA Railway Website
        driver.get(Constant.RAILWAY_URL);

        //2. Click on "Login" tab
        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();

        //3. Enter valid Email and invalid Password
        //4. Click on "Login" button
       LoginPage loginPage = new LoginPage(driver);
       loginPage.login(Constant.USERNAME, "1234567899");

       //Verify error message is displayed
         Assert.assertEquals(loginPage.getLoginErrorMessage(), "There was a problem with your login and/or errors exist in your form.");

    }

    @Test
    public void TC04_SystemShowsMessageWhenUserEntersWrongPasswordManyTimes() {

        SoftAssert softAssert = new SoftAssert();

        //1. Navigate to QA Railway Website
        driver.get(Constant.RAILWAY_URL);

        //2. Click on "Login" tab
        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();


        //3. Enter valid information into "Username" textbox except "Password" textbox.
        //4. Click on "Login" button

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(Constant.USERNAME, "invalidpassword");
        //Verify error message is displayed
        softAssert.assertEquals(loginPage.getLoginErrorMessage(), "Invalid username or password. Please try again");



        //5. Repeat step 3 and 4 three more times.

        for (int i = 0; i < 3; i++) {
            loginPage.login(Constant.USERNAME, "invalidpassword");
            //Verify error message is displayed
            softAssert.assertEquals(loginPage.getLoginErrorMessage(), "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.");
        }

        softAssert.assertAll();

    }

    @Test
    public void TC05_UserCannotLoginWithAnAccountHasntBeenActivated() {


        //Precondition: Create a new account but don't activate it.

        EmailPage emailPage = new EmailPage(driver);
        driver.get(Constant.GUERILLAMAIL_URL);

        emailPage.setUseAlias(false);

        // Get the temporary email
        String email = emailPage.getEmailAddress();
        System.out.println("Temporary email: " + email);

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);

        //Register a new account with the temporary email

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = basePage.gotoRegisterPage();
        registerPage.register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);


        //1. Navigate to QA Railway Website
        //2. Click on "Login" tab
        basePage.gotoLoginPage();

        //3. Enter username and password of account hasn't been activated.
        //4. Click on "Login" button

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, Constant.PASSWORD);

        //Verify error message is displayed
        Assert.assertEquals(loginPage.getLoginErrorMessage(), "Invalid username or password. Please try again.");

    }






}
