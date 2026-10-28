package Exercise5And8;

import common.Utilities.DateUtils;
import common.Utilities.Log;
import dataObjects.Account;
import dataObjects.Message;
import dataObjects.SeatType;
import dataObjects.Station;
import dataObjects.Ticket;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.TimetablePage;
import testcases.BaseTest;

public class Exercise5And8 extends BaseTest {

    @Test
    public void Exercise5And8() {
        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket(
                Station.SAI_GON,
                Station.DA_NANG,
                SeatType.SS,
                2,
                DateUtils.getFutureDate(7)
        );

        BasePage basePage = new BasePage(driver);
        TimetablePage timetablePage = new TimetablePage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        Log.step("1. Navigate to Railway");
        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");
        LoginPage loginPage = basePage.gotoLoginPage();
        loginPage.login(Account.VALID.getEmail(), Account.VALID.getPassword());

        Log.step("3. Click on Timetable tab");
        basePage.gotoTimetablePage();

        Log.step("4. Click Book ticket for Soft seat on the Sài Gòn–Đà Nẵng route");
        timetablePage.clickBookTicket(ticket.getDepartStation(),ticket.getArriveStation());

        Log.step("5. Select the departure date and ticket amount 2, then book");
        bookTicketPage.bookTicket(ticket);

        Log.info("Verify booked ticket information");

        softAssert.assertTrue(
                bookTicketPage.verifySuccessBookTicketMessage(Message.BOOK_SUCCESS),
                "Booking success message is incorrect"
        );

        softAssert.assertTrue(
                bookTicketPage.verifyBookedTicket(ticket),
                "Booked ticket information is incorrect"
        );

        softAssert.assertAll();
    }
}