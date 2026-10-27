package testcases;

import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.RegisterPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;
import pageObjects.guerrilamaill.EmailPage;

public class RegisterTest extends BaseTest{
    @Test
    public void TC07_UserCannotCreateAccountWithAnAlreadyInUseEmail(){

        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoRegisterPage();

        registerPage.register(Constant.USERNAME, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        //Verify: Error message "This email address is already in use." displays above the form.

        softAssert.assertTrue(registerPage.verifyRegisterErrorMessage("This email address is already in use."));

        softAssert.assertTrue( registerPage.isErrorMessageAboveForm(),"Error message is not above the registration form.");

        softAssert.assertAll();

    }

    @Test

    public void TC08_UserCannotCreateAccountWithEmptyFields(){

        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoRegisterPage();

        registerPage.register(Constant.USERNAME, "", "", "");

        softAssert.assertTrue( registerPage.isErrorMessageAboveForm(),"Error message is not above the registration form.");
        softAssert.assertTrue(registerPage.verifyRegisterErrorMessage("There're errors in the form. Please correct the errors and try again."));
        softAssert.assertTrue(registerPage.verifyRegisterErrorPasswordMessage("Invalid password length."));
        softAssert.assertTrue(registerPage.verifyRegisterErrorPIDMessage("Invalid ID length."));
    }

    @Test

    public void TC09_UserCreateAndActivateAccount() {
        
        SoftAssert softAssert = new  SoftAssert();

        EmailPage emailPage = new EmailPage(driver);
        HomePage homePage = new HomePage(driver);
        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);


        basePage.navigatetoGuerrilamail();

        String mailTab = basePage.getCurrentTab();

        emailPage.setUseAlias(false);

        String email = emailPage.getEmailAddress();

        basePage.openNewTab();

        basePage.navigatetoRailway();

        softAssert.assertTrue(homePage.isCreateAccountLinkDisplayed(), "Create account link is not displayed as  expected.");

        homePage.clickCreateAccountLink();

        softAssert.assertTrue(registerPage.isregisterPageTitleDisplayed(), "RegisterPage is not displayed as  expected.");

        registerPage.register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        softAssert.assertEquals(registerPage.getRegisterSuccessTitle(), "Thank you for registering your account");

        basePage.switchToTab(mailTab);

        emailPage.clickMessageConfirm();

        emailPage.clickLinkConfirmMail();

        // Verify registration confirmation message

        softAssert.assertTrue(registerPage.verifymessageConfirmRegister("Registration Confirmed! You can now log in to the site"));

        softAssert.assertAll();

    }

}
