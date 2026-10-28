package testcases;

import common.Utilities.Log;
import dataObjects.Account;
import dataObjects.InvalidData;
import dataObjects.Message;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.*;
import pageObjects.guerrilamaill.EmailPage;

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

    @Test(description = "TC10: Reset password shows error if the new password is the same as current")
    public void TC10_MessageErrorDisplaysWhenNewPasswordIsSameAsCurrent() {
        SoftAssert softAssert = new SoftAssert();

        String email = registerPage.createActivatedAccount();

        Log.step("1. Navigate to QA Railway Login page");
        basePage.navigatetoRailway();
        basePage.gotoLoginPage();

        Log.step("2. Click on Forgot Password link");
        loginPage.clickForgotPasswordLink();

        Log.step("3. Enter the email address of the activated account");
        Log.step("4. Click on Send Instructions button");
        forgotPasswordPage.enterEmail(email);

        Log.step("5. Switch to the mailbox");
        emailPage.switchToMailTab();

        Log.step("6. Open the password reset email");
        emailPage.clickmessageResetPassword();

        String expectedToken = emailPage.getEmailResetToken();

        Log.step("7. Click on the reset link");
        emailPage.clickLinkResetPassword();

        Log.info("Verify reset password form and token");

        softAssert.assertEquals(
                forgotPasswordPage.getTitleFormResetPassword(),
                Message.RESET_FORM_TITLE.getText()
        );

        String actualToken = forgotPasswordPage.getForgotResetResetToken();

        softAssert.assertEquals(actualToken, expectedToken);

        Log.step("8. Enter the current password into both fields");
        Log.step("9. Click Reset Password");

        forgotPasswordPage.enterResetPassword(
                Account.VALID.getPassword(),
                Account.VALID.getPassword()
        );

        Log.info("Verify same password error message");

        softAssert.assertEquals(
                forgotPasswordPage.getMessageResetPasswordError(),
                Message.RESET_SAME_PASSWORD.getText()
        );

        softAssert.assertAll();
    }

    @Test(description = "TC11: Reset password shows error if the new password and confirmation do not match")
    public void TC11_ResetPasswordShowErrorWhenConfirmPasswordNotMatchNewPassWord() {
        SoftAssert softAssert = new SoftAssert();

        String email = registerPage.createActivatedAccount();

        Log.step("1. Navigate to QA Railway Login page");
        basePage.navigatetoRailway();
        basePage.gotoLoginPage();

        Log.step("2. Click on Forgot Password link");
        loginPage.clickForgotPasswordLink();

        Log.step("3. Enter the email address of the activated account");
        Log.step("4. Click on Send Instructions button");
        forgotPasswordPage.enterEmail(email);

        Log.step("5. Switch to the mailbox");
        emailPage.switchToMailTab();

        Log.step("6. Open the password reset email");
        emailPage.clickmessageResetPassword();

        String expectedToken = emailPage.getEmailResetToken();

        Log.step("7. Click on the reset link");
        emailPage.clickLinkResetPassword();

        Log.info("Verify reset password form and token");

        softAssert.assertEquals(
                forgotPasswordPage.getTitleFormResetPassword(),
                Message.RESET_FORM_TITLE.getText()
        );

        String actualToken = forgotPasswordPage.getForgotResetResetToken();

        softAssert.assertEquals(actualToken, expectedToken);

        Log.step("8. Enter different values into the new password and confirmation fields");
        forgotPasswordPage.enterResetPassword(
                Account.VALID.getPassword(),
                InvalidData.INVALID_PASSWORD.getValue()
        );

        Log.info("Verify reset password error message");

        softAssert.assertEquals(
                forgotPasswordPage.getMessageResetPasswordError(),
                Message.RESET_FAILED.getText(),
                "Reset password error message is incorrect"
        );

        Log.info("Verify password confirmation error message");

        softAssert.assertEquals(
                forgotPasswordPage.getMessageConfirmPasswordError(),
                Message.RESET_CONFIRM_MISMATCH.getText(),
                "Password confirmation error message is incorrect"
        );

        softAssert.assertAll();
    }
}