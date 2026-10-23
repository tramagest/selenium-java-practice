package testcases;

import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.RegisterPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;
import java.util.Set;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WindowType;
import pageObjects.guerrilamaill.EmailPage;

public class RegisterTest extends BaseTest{
    @Test
    public void TC07_UserCannotCreateAccountWithAnAlreadyInUseEmail(){

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

    @Test

    public void TC09_UserCreateAndActivateAccount() {
        
        SoftAssert softAssert = new  SoftAssert();

        // Open Guerrilla Mail in the first tab

        EmailPage emailPage = new EmailPage(driver);
        driver.get(Constant.GUERILLAMAIL_URL);

        String mailTab = driver.getWindowHandle();

        // Ensure Use Alias is unchecked

        emailPage.setUseAlias(false);

        // Get the temporary email

        String email = emailPage.getEmailAddress();
        System.out.println("Temporary email: " + email);

        //1. Navigate to QA Railway Website

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);

        //2. Click on "Create an account"

        HomePage homePage = new HomePage(driver);

        //Verify Home page is shown with guide containing href "create an account" to "Register" page

        softAssert.assertTrue(homePage.isCreateAccountLinkDisplayed(), "Create account link is not displayed as  expected.");

        homePage.clickCreateAccountLink();

        RegisterPage registerPage = new RegisterPage(driver);

        //Verify Register page is shown

        softAssert.assertTrue(registerPage.isregisterPageTitleDisplayed(), "RegisterPage is not displayed as  expected.");

        //3. Enter valid information into all fields
        //4. Click on "Register" button

        registerPage.register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        //Verify message thank you

        softAssert.assertEquals(registerPage.getRegisterSuccessTitle(), "Thank you for registering your account");

        //5. Get email information (webmail address, mailbox and password) and navigate to that webmail
        //6. Login to the mailbox

        driver.switchTo().window(mailTab);

        //7. Open email with subject containing "Please confirm your account"  and the email of the new account at step 3

        emailPage.clickMessageConfirm();

        // 8. Click on the activate link

        Set<String> oldTabs = driver.getWindowHandles();

        emailPage.clickLinkConfirmMail();

        // 9. Switch to the new Railway tab

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.numberOfWindowsToBe(oldTabs.size() + 1));

        for (String tab : driver.getWindowHandles()) {
            if (!oldTabs.contains(tab)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        // 10. Verify registration confirmation message

        String expectedConfirmMessage ="Registration Confirmed! You can now log in to the site";

        String actualConfirmMessage = registerPage.getmessageConfirmRegister();

        softAssert.assertEquals(actualConfirmMessage,expectedConfirmMessage,
                "Registration confirmation message is not displayed as expected.");

        softAssert.assertAll();

    }

}
