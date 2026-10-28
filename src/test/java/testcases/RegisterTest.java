package testcases;

import common.Utilities.Log;
import dataObjects.InvalidData;
import dataObjects.Message;
import dataObjects.ValidData;
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
                ValidData.EMAIL.getValue(),
                ValidData.PASSWORD.getValue(),
                ValidData.PASSWORD.getValue(),
                ValidData.PID.getValue()
        );

        Log.verify(
                softAssert,
                "Email already in use error message",
                Message.REGISTER_EMAIL_IN_USE.getText() ,
                registerPage.getRegisterErrorMessage()
        );

        Log.verify(
                softAssert,
                "Registration error message position",
                true,
                registerPage.isErrorMessageAboveForm()
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
                ValidData.EMAIL.getValue(),
                InvalidData.EMPTY.getValue(),
                InvalidData.EMPTY.getValue(),
                InvalidData.EMPTY.getValue()
        );

        Log.verify(
                softAssert,
                "Registration error message position",
                true,
                registerPage.isErrorMessageAboveForm()
        );

        Log.verify(
                softAssert,
                "Registration form error message",
                Message.REGISTER_FORM_ERROR.getText(),
                registerPage.getRegisterErrorMessage()
        );

        Log.verify(
                softAssert,
                "Invalid password length error message",
                Message.INVALID_PASSWORD_LENGTH.getText(),
                registerPage.getRegisterErrorPasswordMessage()
        );

        Log.verify(
                softAssert,
                "Invalid PID length error message",
                Message.INVALID_PID_LENGTH.getText(),
                registerPage.getRegisterErrorPIDMessage()
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

        Log.verify(
                softAssert,
                "Create account link displayed",
                true,
                homePage.isCreateAccountLinkDisplayed()
        );

        Log.step("2. Click on Create an account");
        homePage.clickCreateAccountLink();

        Log.verify(
                softAssert,
                "Register page displayed",
                true,
                registerPage.isregisterPageTitleDisplayed()
        );

        Log.step("3. Enter valid information into all fields");
        Log.step("4. Click on Register button");

        registerPage.register(
                email,
                ValidData.PASSWORD.getValue(),
                ValidData.PASSWORD.getValue(),
                ValidData.PID.getValue()
        );

        Log.verify(
                softAssert,
                "Registration success title",
                Message.REGISTER_SUCCESS_TITLE.getText(),
                registerPage.getRegisterSuccessTitle()
        );

        Log.step("5. Navigate to the mailbox");
        Log.step("6. Open the mailbox");

        basePage.switchToTab(mailTab);

        Log.step("7. Open the confirmation email");
        emailPage.clickMessageConfirm();

        Log.step("8. Click on the activation link");
        emailPage.clickLinkConfirmMail();

        Log.verify(
                softAssert,
                "Registration confirmation message",
                Message.REGISTER_CONFIRMED.getText(),
                registerPage.getmessageConfirmRegister()
        );

        softAssert.assertAll();
    }
}