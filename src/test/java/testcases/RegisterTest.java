package testcases;

import common.Utilities.Log;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.RegisterPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;
import pageObjects.guerrilamaill.EmailPage;
import org.testng.annotations.BeforeMethod;

public class RegisterTest extends BaseTest{

    protected BasePage basePage;
    protected LoginPage loginPage;
    protected EmailPage emailPage;
    protected RegisterPage registerPage;
    protected HomePage homePage;

    @BeforeMethod
    public void initPageObjects() {
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        emailPage = new EmailPage(driver);
        registerPage = new RegisterPage(driver);
        homePage = new HomePage(driver);
    }


    @Test (description = "TC07: User can't create account with an already in-use email")
    public void TC07_UserCannotCreateAccountWithAnAlreadyInUseEmail(){

        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Click on \"Register\" tab");

        basePage.gotoRegisterPage();

        Log.step("3. Enter information of the created account");
        Log.step("4. Click on \"Register\" button");

        registerPage.register(Constant.USERNAME, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        Log.info("Verify: Error message \"This email address is already in use.\" displays above the form.");

        softAssert.assertTrue(registerPage.verifyRegisterErrorMessage("This email address is already in use."));

        softAssert.assertTrue( registerPage.isErrorMessageAboveForm(),"Error message is not above the registration form.");

        softAssert.assertAll();

    }

    @Test (description = "TC08: User can't create account while password and PID fields are empty")

    public void TC08_UserCannotCreateAccountWithEmptyFields(){

        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Click on \"Register\" tab");

        basePage.gotoRegisterPage();

        Log.step("3. Enter valid email address and leave other fields empty");
        Log.step("4. Click on \"Register\" button");

        registerPage.register(Constant.USERNAME, "", "", "");

        Log.info("Verify register error message");

        softAssert.assertTrue( registerPage.isErrorMessageAboveForm(),"Error message is not above the registration form.");

        softAssert.assertEquals(registerPage.getRegisterErrorMessage(),"There're errors in the form. Please correct the errors and try again.", "Message is displayed incorrectly" );

        softAssert.assertEquals(registerPage.getRegisterErrorPasswordMessage(),"Invalid password length.","Message is displayed incorrectly" );

        softAssert.assertEquals(registerPage.getRegisterErrorPIDMessage(),"Invalid ID length.","Message is displayed incorrectly");

    }

    @Test (description = "TC09: User create and activate account")

    public void TC09_UserCreateAndActivateAccount() {
        
        SoftAssert softAssert = new  SoftAssert();

        basePage.navigatetoGuerrilamail();

        String mailTab = basePage.getCurrentTab();

        emailPage.setUseAlias(false);

        String email = emailPage.getEmailAddress();

        basePage.openNewTab();

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.info("Verify Home page is shown with guide containing href \"create an account\" to \"Register\" page");

        softAssert.assertTrue(homePage.isCreateAccountLinkDisplayed(), "Create account link is not displayed as  expected.");

        Log.step("2. Click on \"Create an account\"\n");

        homePage.clickCreateAccountLink();

        Log.info("Register page is shown");

        softAssert.assertTrue(registerPage.isregisterPageTitleDisplayed(), "RegisterPage is not displayed as  expected.");

        Log.step("3. Enter valid information into all fields");
        Log.step("4. Click on \"Register\" button");

        registerPage.register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        Log.info("\"Thank you for registering your account\" is shown\n");

        softAssert.assertEquals(registerPage.getRegisterSuccessTitle(), "Thank you for registering your account");

        Log.step("5. Get email information (webmail address, mailbox and password) and navigate to that webmail");
        Log.step("6. Login to the mailbox");

        basePage.switchToTab(mailTab);

        Log.step("7. Open email with subject containing \"Please confirm your account\"  and the email of the new account at step 3");

        emailPage.clickMessageConfirm();

        Log.step("8. Click on the activate link");

        emailPage.clickLinkConfirmMail();

        Log.info("Verify registration confirmation message");

        softAssert.assertEquals(registerPage.getmessageConfirmRegister(), "Registration Confirmed! You can now log in to the site","Message is displayed incorrectly");

        softAssert.assertAll();

    }

}
