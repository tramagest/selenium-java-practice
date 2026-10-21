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

        //1. Navigate to QA Railway Website
        driver.get(Constant.RAILWAY_URL);

        //2. Login with valid Email and Password
        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        //3. Click on "FAQ" tab
        basePage.gotoFAQPage();

        //4. Click on "Log out" tab
        basePage.logout();


        //Verify: Home page displays. "Log out" tab is disappeared.

        HomePage homePage = new HomePage(driver);

        softAssert.assertTrue(homePage.isHomePageTitleDisplayed(), "Home page is not displayed after logging out.");
        softAssert.assertTrue(basePage.isLoggedOut(), "Logout tab is still displayed after logging out.");


        softAssert.assertAll();




    }
}
