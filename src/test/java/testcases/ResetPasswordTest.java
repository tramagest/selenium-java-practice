package testcases;

import common.Utilities.Log;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.*;
import pageObjects.guerrilamaill.EmailPage;
import org.testng.annotations.BeforeMethod;


public class ResetPasswordTest extends BaseTest {

    protected BasePage basePage;
    protected LoginPage loginPage;
    protected EmailPage emailPage;
    protected RegisterPage registerPage;
    protected ForgotPasswordPage forgotPasswordPage;

    @BeforeMethod
    public void initPageObjects() {
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        emailPage = new EmailPage(driver);
        registerPage = new RegisterPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
    }

    @Test (description = "TC10: Reset password shows error if the new password is same as current")
    public void TC10_MessageErrorDisplaysWhenNewPasswordIsSameAsCurrent(){


        SoftAssert softAssert = new SoftAssert();

        String email = registerPage.createActivatedAccount();

        Log.step("1. Navigate to QA Railway Login page");

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        Log.step("2. Click on \"Forgot Password page\" link");

        loginPage.clickForgotPasswordLink();

        Log.step("3. Enter the email address of the activated account");
        Log.step("4. Click on \"Send Instructions\" button");

        forgotPasswordPage.enterEmail(email);

        Log.step("5. Login to the mailbox (the same mailbox when creating account)");

        emailPage.switchToMailTab();

        Log.step("6. Open email with subject contaning \"Please reset your password\" and the email of the account at step 3");

        emailPage.clickmessageResetPassword();

        String expectedToken= emailPage.getEmailResetToken();

        Log.step("7. Click on reset link");

        emailPage.clickLinkResetPassword();

        Log.info("reset password token");

        softAssert.assertEquals(forgotPasswordPage.getTitleFormResetPassword(),"Password Change Form");

        String actualToken = forgotPasswordPage.getForgotResetResetToken();

        softAssert.assertEquals(actualToken,expectedToken);

        Log.step("8. Input same password into 2 fields  \"new password\" and \"confirm password\"");
        Log.step("9. Click Reset Password");

        forgotPasswordPage.enterResetPassword(Constant.PASSWORD, Constant.PASSWORD);

        Log.info("Verify: Message \"The new password cannot be the same with the current password\" is shown");

        softAssert.assertEquals(forgotPasswordPage.getMessageResetPasswordError(),"The new password cannot be the same with the current password");

        softAssert.assertAll();

    }

    @Test (description = "TC11: Reset password shows error if the new password and confirm password doesn't match")
    public void TC11_ResetPasswordShowErrorWhenConfirmPasswordNotMatchNewPassWord(){

        SoftAssert softAssert = new SoftAssert();

        String email = registerPage.createActivatedAccount();

        Log.step("1. Navigate to QA Railway Login page");

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        Log.step("2. Click on \"Forgot Password page\" link");

        loginPage.clickForgotPasswordLink();

        Log.step("3. Enter the email address of the activated account");
        Log.step("4. Click on \"Send Instructions\" button");

        forgotPasswordPage.enterEmail(email);

        Log.step("5. Login to the mailbox (the same mailbox when creating account)");

        emailPage.switchToMailTab();

        Log.step("6. Open email with subject contaning \"Please reset your password\" and the email of the account at step 3");

        emailPage.clickmessageResetPassword();

        String expectedToken= emailPage.getEmailResetToken();

        Log.step("7. Click on reset link");

        emailPage.clickLinkResetPassword();

        Log.info("reset password token");

        softAssert.assertEquals(forgotPasswordPage.getTitleFormResetPassword(),"Password Change Form");

        String actualToken = forgotPasswordPage.getForgotResetResetToken();

        softAssert.assertEquals(actualToken,expectedToken);

        Log.step("8. Input different input into 2 fields  \"new password\" and \"confirm password\"");

        forgotPasswordPage.enterResetPassword(Constant.PASSWORD, Constant.INVALIDPASSWORD);

        Log.info("Verify: Error message \"Could not reset password. Please correct the errors and try again.\" displays above the form.");

        String actualMessage = forgotPasswordPage.getMessageResetPasswordError();

        softAssert.assertEquals(actualMessage,"Could not reset password. Please correct the errors and try again.");

        Log.info("VERIFY:Error message \"The password confirmation did not match the new password.\" displays next to the confirm password field.\n");

        softAssert.assertEquals(forgotPasswordPage.getMessageConfirmPasswordError(),"The password confirmation did not match the new password.","The message is incorrect");

        softAssert.assertAll();

    }



}
