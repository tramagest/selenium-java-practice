package testcases;

import common.Utilities.Log;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;

public class LogoutTest extends BaseTest {

    @Test (description = "TC06: User is redirected to Home page after logging out")
    public void TC06_UserIsRedirectedToHomePageAfterLoggingOut() {

        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        Log.step("2. Login with valid Email and Password");

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Log.step("3. Click on \"FAQ\" tab");

        basePage.gotoFAQPage();

        Log.step("4. Click on \"Log out\" tab");

        basePage.logout();

        Log.info("Verify Home page displays.\n" +
                "\"Log out\" tab is disappeared.");

        softAssert.assertTrue(homePage.isHomePageTitleDisplayed(), "Home page is not displayed after logging out.");
        softAssert.assertTrue(basePage.isLoggedOut(), "Logout tab is still displayed after logging out.");

        softAssert.assertAll();

    }
}
