package testcases;

import common.Utilities.Log;
import dataObjects.SeatType;
import dataObjects.Station;
import dataObjects.Ticket;
import dataObjects.ValidData;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.MyTicketPage;

public class CancelTicketTest extends BaseTest {

    @Test(description = "TC16: User can cancel a ticket")
    public void TC16_UserCanCancelATicket() {
        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket(
                Station.NHA_TRANG,
                Station.SAI_GON,
                SeatType.SSC,
                1,
                null
        );

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);
        MyTicketPage myTicketPage = new MyTicketPage(driver);

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");
        basePage.gotoLoginPage();
        loginPage.login(ValidData.EMAIL.getValue(), ValidData.PASSWORD.getValue());

        basePage.gotoBookTicketPage();

        Log.step("3. Book a ticket");
        bookTicketPage.bookTicket(ticket);

        Log.step("4. Click on My Ticket tab");
        basePage.gotoMyTicketPage();

        Log.step("5. Click Cancel for the ticket");
        Log.step("6. Confirm the cancellation");

        String ticketId = myTicketPage.cancelTicket(ticket);

        Log.verify(
                softAssert,
                "Ticket found and canceled",
                true,
                ticketId != null
        );

        Log.verify(
                softAssert,
                "Canceled ticket is displayed",
                false,
                myTicketPage.verifyTicketExist(ticketId)
        );

        softAssert.assertAll();
    }
}