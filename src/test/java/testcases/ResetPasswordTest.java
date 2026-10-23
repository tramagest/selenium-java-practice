package testcases;

import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.RegisterPage;
import pageObjects.guerrilamaill.EmailPage;
import pageObjects.Railway.ForgotPasswordPage;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResetPasswordTest extends BaseTest {

    @Test
    public void TC10_MessageErrorDisplaysWhenNewPasswordIsSameAsCurrent(){


       SoftAssert softAssert = new SoftAssert();

        //Pre-condition: an actived account is existing

       RegisterPage registerPage = new RegisterPage(driver);

       String email = registerPage.createActivatedAccount();


        //1. Navigate to QA Railway Login page

        driver.get(Constant.RAILWAY_URL);

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        basePage.gotoLoginPage();


        //2. Click on "Forgot Password page" link

        loginPage.clickForgotPasswordLink();


        //3. Enter the email address of the activated account
        //4. Click on "Send Instructions" button

        ForgotPasswordPage forgotPasswordpage = new ForgotPasswordPage(driver);

        forgotPasswordpage.enterEmail(email);

        //5. Login to the mailbox (the same mailbox when creating account)

        new EmailPage(driver).switchToMailTab();

        //6. Open email with subject contaning "Please reset your password" and the email of the account at step 3

        EmailPage emailPage = new EmailPage(driver);
        emailPage.clickmessageResetPassword();
        String expectedToken= emailPage.getEmailResetToken();

        //7. Click on reset link

        emailPage.clickLinkResetPassword();

        Set<String> oldTabs = driver.getWindowHandles();   // trước khi bấm link
        emailPage.clickLinkResetPassword();

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.numberOfWindowsToBe(oldTabs.size() + 1));

        for (String tab : driver.getWindowHandles()) {
            if (!oldTabs.contains(tab)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        //Verify: Redirect to Railways page and
        // Form "Password Change Form" is shown with the reset password token

        softAssert.assertEquals(forgotPasswordpage.getTitleFormResetPassword(),"Password Change Form");


        String actualToken = forgotPasswordpage.getForgotResetResetToken();

        softAssert.assertEquals(actualToken,expectedToken);

        //8. Input same password into 2 fields  "new password" and "confirm password"
        //9. Click Reset Password

        forgotPasswordpage.enterResetPassword(Constant.PASSWORD, Constant.PASSWORD);

        //Verify: Message "The new password cannot be the same with the current password" is shown

        softAssert.assertEquals(forgotPasswordpage.getMessageResetPasswordError(),
                "The new password cannot be the same with the current password",
                "Reset password error message is incorrect.");

        softAssert.assertAll();

    }

    @Test
    public void TC11_ResetPasswordShowErrorWhenConfirmPasswordNotMatchNewPassWord(){


        SoftAssert softAssert = new SoftAssert();

        //Pre-condition: an actived account is existing

        RegisterPage registerPage = new RegisterPage(driver);

        String email = registerPage.createActivatedAccount();

        //1. Navigate to QA Railway Login page

        driver.get(Constant.RAILWAY_URL);

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        basePage.gotoLoginPage();

        //2. Click on "Forgot Password page" link

        loginPage.clickForgotPasswordLink();

        //3. Enter the email address of the activated account
        //4. Click on "Send Instructions" button

        ForgotPasswordPage forgotPasswordpage = new ForgotPasswordPage(driver);

        forgotPasswordpage.enterEmail(email);

        //5. Login to the mailbox (the same mailbox when creating account)

        new EmailPage(driver).switchToMailTab();

        //6. Open email with subject contaning "Please reset your password" and the email of the account at step 3

        EmailPage emailPage = new EmailPage(driver);
        emailPage.clickmessageResetPassword();
        String expectedToken= emailPage.getEmailResetToken();

        //7. Click on reset link

        emailPage.clickLinkResetPassword();

        Set<String> oldTabs = driver.getWindowHandles();
        emailPage.clickLinkResetPassword();

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.numberOfWindowsToBe(oldTabs.size() + 1));

        for (String tab : driver.getWindowHandles()) {
            if (!oldTabs.contains(tab)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        //Verify: Redirect to Railways page and
        // Form "Password Change Form" is shown with the reset password token

        softAssert.assertEquals(forgotPasswordpage.getTitleFormResetPassword(),"Password Change Form");

        String actualToken = forgotPasswordpage.getForgotResetResetToken();

        softAssert.assertEquals(actualToken,expectedToken);

        //8. Input same password into 2 fields  "new password" and "confirm password"
        //9. Click Reset Password

        forgotPasswordpage.enterResetPassword(Constant.PASSWORD, Constant.INVALIDPASSWORD);

        //Verify: Error message "Could not reset password. Please correct the errors and try again." displays above the form.

        String actualMessage = forgotPasswordpage.getMessageResetPasswordError();
        softAssert.assertEquals(actualMessage,"Could not reset password. Please correct the errors and try again.");

        //VERIFY:Error message "The password confirmation did not match the new password." displays next to the confirm password field.

        String actualConfirmPWMessageError = forgotPasswordpage.getMessageConfirmPasswordError();
        softAssert.assertEquals(actualConfirmPWMessageError ,"The password confirmation did not match the new password.");

        softAssert.assertAll();

    }



}
