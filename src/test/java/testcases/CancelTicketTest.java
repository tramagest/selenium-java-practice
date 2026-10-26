package testcases;


import dataObjects.Ticket;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.MyTicketPage;
import pageObjects.Railway.LoginPage;


public class CancelTicketTest extends BaseTest {
    @Test
    public void TC16_UserCanCancelATicket() {

        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket("Nha Trang","Sài Gòn","Soft seat with air conditioner",1,null);

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);
        MyTicketPage myTicketPage = new MyTicketPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        basePage.gotoBookTicketPage();

        bookTicketPage.bookTicket(ticket);

        basePage.gotoMyTicketPage();

        String ticketId = myTicketPage.cancelTicket(ticket);

        softAssert.assertFalse(myTicketPage.verifyTicketExist(ticketId), "Ticket was not canceled.");

        softAssert.assertAll();

    }
}
