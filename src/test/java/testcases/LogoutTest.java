package testcases;

import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;

public class LogoutTest extends BaseTest {

    @Test
    public void TC06_UserIsRedirectedToHomePageAfterLoggingOut() {

        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        basePage.gotoFAQPage();

        basePage.logout();

        softAssert.assertTrue(homePage.isHomePageTitleDisplayed(), "Home page is not displayed after logging out.");
        softAssert.assertTrue(basePage.isLoggedOut(), "Logout tab is still displayed after logging out.");

        softAssert.assertAll();

    }
}
