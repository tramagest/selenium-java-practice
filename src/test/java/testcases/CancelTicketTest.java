package testcases;


import common.Utilities.Log;
import dataObjects.Ticket;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.MyTicketPage;
import pageObjects.Railway.LoginPage;


public class CancelTicketTest extends BaseTest {
    @Test (description = "TC16: User can cancel a ticket")
    public void TC16_UserCanCancelATicket() {

        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket("Nha Trang","Sài Gòn","Soft seat with air conditioner",1,null);

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);
        MyTicketPage myTicketPage = new MyTicketPage(driver);

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        basePage.gotoBookTicketPage();

        Log.step("3. Book a ticket");

        bookTicketPage.bookTicket(ticket);

        Log.step("4. Click on \"My ticket\" tab");

        basePage.gotoMyTicketPage();

        Log.step("5. Click on \"Cancel\" button of ticket which user want to cancel.");
        Log.step("6. Click on \"OK\" button on Confirmation message \"Are you sure?\"");

        String ticketId = myTicketPage.cancelTicket(ticket);

        Log.info("Verify the canceled ticket is disappeared.");

        softAssert.assertFalse(myTicketPage.verifyTicketExist(ticketId), "Ticket was not canceled.");

        softAssert.assertAll();

    }
}
