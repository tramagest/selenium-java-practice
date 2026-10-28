package testcases;

import common.Utilities.Log;
import dataObjects.Account;
import dataObjects.InvalidData;
import dataObjects.Message;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.RegisterPage;
import pageObjects.guerrilamaill.EmailPage;

public class RegisterTest extends BaseTest {

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

    @Test(description = "TC07: User can't create account with an already in-use email")
    public void TC07_UserCannotCreateAccountWithAnAlreadyInUseEmail() {
        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Click on Register tab");
        basePage.gotoRegisterPage();

        Log.step("3. Enter information of the created account");
        Log.step("4. Click on Register button");

        registerPage.register(
                Account.VALID.getEmail(),
                Account.VALID.getPassword(),
                Account.VALID.getPassword(),
                Account.VALID.getPid()
        );

        Log.info("Verify that the email already in use message is displayed");

        softAssert.assertTrue(
                registerPage.verifyRegisterErrorMessage(
                        Message.REGISTER_EMAIL_IN_USE.getText()
                ),
                "Email already in use message is incorrect"
        );

        softAssert.assertTrue(
                registerPage.isErrorMessageAboveForm(),
                "Error message is not above the registration form"
        );

        softAssert.assertAll();
    }

    @Test(description = "TC08: User can't create account while password and PID fields are empty")
    public void TC08_UserCannotCreateAccountWithEmptyFields() {
        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Click on Register tab");
        basePage.gotoRegisterPage();

        Log.step("3. Enter valid email and leave other fields empty");
        Log.step("4. Click on Register button");

        registerPage.register(
                Account.VALID.getEmail(),
                InvalidData.EMPTY.getValue(),
                InvalidData.EMPTY.getValue(),
                InvalidData.EMPTY.getValue()
        );

        Log.info("Verify registration error messages");

        softAssert.assertTrue(
                registerPage.isErrorMessageAboveForm(),
                "Error message is not above the registration form"
        );

        softAssert.assertEquals(
                registerPage.getRegisterErrorMessage(),
                Message.REGISTER_FORM_ERROR.getText(),
                "Registration error message is incorrect"
        );

        softAssert.assertEquals(
                registerPage.getRegisterErrorPasswordMessage(),
                Message.INVALID_PASSWORD_LENGTH.getText(),
                "Password error message is incorrect"
        );

        softAssert.assertEquals(
                registerPage.getRegisterErrorPIDMessage(),
                Message.INVALID_PID_LENGTH.getText(),
                "PID error message is incorrect"
        );

        softAssert.assertAll();
    }

    @Test(description = "TC09: User creates and activates an account")
    public void TC09_UserCreateAndActivateAccount() {
        SoftAssert softAssert = new SoftAssert();

        basePage.navigatetoGuerrilamail();
        String mailTab = basePage.getCurrentTab();

        emailPage.setUseAlias(false);
        String email = emailPage.getEmailAddress();

        basePage.openNewTab();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.info("Verify that the Home page displays the create account link");

        softAssert.assertTrue(
                homePage.isCreateAccountLinkDisplayed(),
                "Create account link is not displayed as expected"
        );

        Log.step("2. Click on Create an account");
        homePage.clickCreateAccountLink();

        Log.info("Verify that the Register page is displayed");

        softAssert.assertTrue(
                registerPage.isregisterPageTitleDisplayed(),
                "Register page is not displayed as expected"
        );

        Log.step("3. Enter valid information into all fields");
        Log.step("4. Click on Register button");

        registerPage.register(
                email,
                Account.VALID.getPassword(),
                Account.VALID.getPassword(),
                Account.VALID.getPid()
        );

        Log.info("Verify registration success title");

        softAssert.assertEquals(
                registerPage.getRegisterSuccessTitle(),
                Message.REGISTER_SUCCESS_TITLE.getText()
        );

        Log.step("5. Navigate to the mailbox");
        Log.step("6. Open the mailbox");

        basePage.switchToTab(mailTab);

        Log.step("7. Open the confirmation email");
        emailPage.clickMessageConfirm();

        Log.step("8. Click on the activation link");
        emailPage.clickLinkConfirmMail();

        Log.info("Verify registration confirmation message");

        softAssert.assertEquals(
                registerPage.getmessageConfirmRegister(),
                Message.REGISTER_CONFIRMED.getText(),
                "Registration confirmation message is incorrect"
        );

        softAssert.assertAll();
    }
}