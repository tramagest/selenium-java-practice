package testcases;

import common.Utilities.Log;
import dataObjects.Account;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.LoginPage;

public class LogoutTest extends BaseTest {

    @Test(description = "TC06: User is redirected to Home page after logging out")
    public void TC06_UserIsRedirectedToHomePageAfterLoggingOut() {
        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        Log.step("2. Login with valid Email and Password");
        loginPage.login(Account.VALID.getEmail(), Account.VALID.getPassword());

        Log.step("3. Click on FAQ tab");
        basePage.gotoFAQPage();

        Log.step("4. Click on Log out tab");
        basePage.logout();

        Log.info("Verify Home page is displayed and Log out tab is absent");

        softAssert.assertTrue(
                homePage.isHomePageTitleDisplayed(),
                "Home page is not displayed after logging out"
        );

        softAssert.assertTrue(
                basePage.isLoggedOut(),
                "Logout tab is still displayed after logging out"
        );

        softAssert.assertAll();
    }
}