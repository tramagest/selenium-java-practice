package testcases;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.testng.asserts.SoftAssert;


import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.RegisterPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;

import org.openqa.selenium.WindowType;

public class RegisterTest extends BaseTest{
    @Test
    public void TC07_UsercannotCreateAccountWithAnAlreadyInUseEmail(){

        SoftAssert softAssert = new SoftAssert();
        //1. Navigate to QA Railway Website

        driver.get(Constant.RAILWAY_URL);


        //2. Click on "Register" tab

        BasePage basePage = new BasePage(driver);
        basePage.gotoRegisterPage();

        //3. Enter information of the created account in Pre-condition
        //4. Click on "Register" button

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(Constant.USERNAME, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        //Verify: Error message "This email address is already in use." displays above the form.

        softAssert.assertEquals(registerPage.getRegisterErrorMessage(), "This email address is already in use.", "Error message is not displayed as expected when trying to register with an already in use email.");
        softAssert.assertTrue( registerPage.isErrorMessageAboveForm(),"Error message is not above the registration form.");



        softAssert.assertAll();

    }

    @Test

    public void TC08_UserCannotCreateAccountWithEmptyFields(){

        SoftAssert softAssert = new SoftAssert();
        //1. Navigate to QA Railway Website

        driver.get(Constant.RAILWAY_URL);

        //2. Click on "Register" tab

        BasePage basePage = new BasePage(driver);
        basePage.gotoRegisterPage();

        //3. Enter valid email address and leave other fields empty
        //4. Click on "Register" button

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(Constant.USERNAME, "", "", "");


        //Verify

        softAssert.assertEquals(registerPage.getRegisterErrorMessage(), "There're errors in the form. Please correct the errors and try again.", "Error message is not displayed as expected when trying to register with empty fields.");
        softAssert.assertTrue( registerPage.isErrorMessageAboveForm(),"Error message is not above the registration form.");
        softAssert.assertEquals(registerPage.getRegisterErrorPasswordMessage(), "Invalid password length.", "Password error message is not displayed as expected.");
        softAssert.assertEquals(registerPage.getRegisterErrorPIDMessage(), "Invalid ID length.", "PID error message is not displayed as expected.");


    }


    /*@Test
    public void TC09_UserCreateAndActivateAccount() {

        SoftAssert softAssert = new SoftAssert();

        TempMailPage tempMailPage = new TempMailPage(driver);
        String mailTab = driver.getWindowHandle();

        driver.get(Constant.TEMPMAIL_URL);


        String tempEmail = tempMailPage.getTempEmail();

        System.out.println("Temporary email: " + tempEmail);


        //1. Navigate to QA Railway Website

        driver.switchTo().newWindow(WindowType.TAB);
        String railwayTab = driver.getWindowHandle();

        driver.get(Constant.RAILWAY_URL);

        //2. Click on "Create an account"

        HomePage homePage = new HomePage(driver);
        homePage.clickCreateAccountLink();

        //3. Enter valid information into all fields
        //4. Click on "Register" button

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(tempEmail, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        //5. Get email information (webmail address, mailbox and password) and navigate to that webmail
        //6. Login to the mailbox
        driver.switchTo().window(mailTab);
        tempMailPage.clickButtonRefresh();


        //7. Open email with subject containing "Please confirm your account"  and the email of the new account at step 3

        tempMailPage.clickMessageConfirmMail();

        //8. Click on the activate link

        tempMailPage.clickLinkConfirmMail();





        softAssert.assertAll();

    }*/

}
