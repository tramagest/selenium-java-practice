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


        Log.verify(
                softAssert,
                "Password Change Form title",
                Message.RESET_FORM_TITLE.getText(),
                forgotPasswordPage.getTitleFormResetPassword()
        );

        String actualToken = forgotPasswordPage.getForgotResetResetToken();

        Log.verify(
                softAssert,
                "Reset password token",
                expectedToken,
                actualToken
        );

        Log.step("8. Enter the current password into both fields");
        Log.step("9. Click Reset Password");

        forgotPasswordPage.enterResetPassword(
                ValidData.PASSWORD.getValue(),
                ValidData.PASSWORD.getValue()
        );

        Log.verify(
                softAssert,
                "Error message when reusing the current password",
                Message.RESET_SAME_PASSWORD.getText(),
                forgotPasswordPage.getMessageResetPasswordError()
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

        Log.verify(
                softAssert,
                "Password Change Form title",
                Message.RESET_FORM_TITLE.getText(),
                forgotPasswordPage.getTitleFormResetPassword()
        );

        String actualToken = forgotPasswordPage.getForgotResetResetToken();

        Log.verify(
                softAssert,
                "Reset password token",
                expectedToken,
                actualToken
        );

        Log.step("8. Enter different values into the new password and confirmation fields");
        forgotPasswordPage.enterResetPassword(
                ValidData.PASSWORD.getValue(),
                InvalidData.INVALID_PASSWORD.getValue()
        );

        Log.verify(
                softAssert,
                "Password reset error message",
                Message.RESET_FAILED.getText(),
                forgotPasswordPage.getMessageResetPasswordError()
        );

        Log.verify(
                softAssert,
                "Password confirmation error message",
                Message.RESET_CONFIRM_MISMATCH.getText(),
                forgotPasswordPage.getMessageConfirmPasswordError()
        );

        softAssert.assertAll();
    }
}