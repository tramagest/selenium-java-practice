package testcases;

import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.RegisterPage;
import pageObjects.guerrilamaill.EmailPage;
import pageObjects.Railway.ForgotPasswordPage;


public class ResetPasswordTest extends BaseTest {

    @Test
    public void TC10_MessageErrorDisplaysWhenNewPasswordIsSameAsCurrent(){


        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ForgotPasswordPage forgotPasswordpage = new ForgotPasswordPage(driver);
        EmailPage emailPage = new EmailPage(driver);

        RegisterPage registerPage = new RegisterPage(driver);

        String email = registerPage.createActivatedAccount();

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.clickForgotPasswordLink();

        forgotPasswordpage.enterEmail(email);

        emailPage.switchToMailTab();

        emailPage.clickmessageResetPassword();

        String expectedToken= emailPage.getEmailResetToken();

        emailPage.clickLinkResetPassword();


        //Verify: Redirect to Railways page and
        // Form "Password Change Form" is shown with the reset password token

        softAssert.assertEquals(forgotPasswordpage.getTitleFormResetPassword(),"Password Change Form");

        String actualToken = forgotPasswordpage.getForgotResetResetToken();

        softAssert.assertEquals(actualToken,expectedToken);

        forgotPasswordpage.enterResetPassword(Constant.PASSWORD, Constant.PASSWORD);

        //Verify: Message "The new password cannot be the same with the current password" is shown

        softAssert.assertTrue(forgotPasswordpage.verifyMessageResetPasswordError("The new password cannot be the same with the current password"));

        softAssert.assertAll();

    }

    @Test
    public void TC11_ResetPasswordShowErrorWhenConfirmPasswordNotMatchNewPassWord(){

        SoftAssert softAssert = new SoftAssert();

        RegisterPage registerPage = new RegisterPage(driver);
        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ForgotPasswordPage forgotPasswordpage = new ForgotPasswordPage(driver);
        EmailPage emailPage = new EmailPage(driver);

        String email = registerPage.createActivatedAccount();

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.clickForgotPasswordLink();

        forgotPasswordpage.enterEmail(email);

        emailPage.switchToMailTab();

        emailPage.clickmessageResetPassword();
        String expectedToken= emailPage.getEmailResetToken();

        emailPage.clickLinkResetPassword();

        //Verify: Redirect to Railways page and
        // Form "Password Change Form" is shown with the reset password token

        softAssert.assertEquals(forgotPasswordpage.getTitleFormResetPassword(),"Password Change Form");

        String actualToken = forgotPasswordpage.getForgotResetResetToken();

        softAssert.assertEquals(actualToken,expectedToken);

        forgotPasswordpage.enterResetPassword(Constant.PASSWORD, Constant.INVALIDPASSWORD);

        //Verify: Error message "Could not reset password. Please correct the errors and try again." displays above the form.

        String actualMessage = forgotPasswordpage.getMessageResetPasswordError();
        softAssert.assertEquals(actualMessage,"Could not reset password. Please correct the errors and try again.");

        //VERIFY:Error message "The password confirmation did not match the new password." displays next to the confirm password field.

        softAssert.assertTrue(forgotPasswordpage.verifyMessageConfirmPasswordError("The password confirmation did not match the new password."));
        softAssert.assertAll();

    }



}
