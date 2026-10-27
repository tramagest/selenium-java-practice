package Exercise5And8;

import common.Constant.Constant;
import common.Utilities.Log;
import dataObjects.Ticket;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.TimetablePage;
import pageObjects.Railway.BookTicketPage;
import common.Utilities.DateUtils;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;
import testcases.BaseTest;

public class Exercise5And8 extends BaseTest {
    @Test
    public void Exercise5And8() {

        SoftAssert softAssert = new SoftAssert();

        String date = DateUtils.getFutureDate(7);
        String departStation = "Sài Gòn";
        String arriveStation = "Đà Nẵng";
        String seatType = "Soft seat";

        ticket = new Ticket(departStation, arriveStation, seatType, 2, date );

        BasePage basePage = new BasePage(driver);
        TimetablePage timetablePage = new TimetablePage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);
        LoginPage loginPage = new LoginPage(driver);


        Log.step("1. Navigate to Railway");

        basePage.navigatetoRailway();

        Log.step("2. Login");

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        //Click on Timetable tab
        //
        //Click on “check price” of Sai Gon-Da Nang
        //
        //Click on “book ticket” of “Soft seat”
        //
        //Choose Depart date is next week (next 7 days) and Ticket amount is 2
        //
        //Click on “book ticket”

        Log.step("3. Click on Timetable tab");

        basePage.gotoTimetablePage();

        Log.step("4. Click on “book ticket” of “Soft seat”");

        timetablePage.clickCheckPrice(departStation, arriveStation);

        timetablePage.clickBookTicketBySeat(seatType);

        bookTicketPage.bookTicket(ticket);

        Log.info("Verify Book Ticket");

        softAssert.assertTrue(bookTicketPage.verifySuccessBookTicketMessage("Ticket booked successfully!"));

        softAssert.assertTrue(bookTicketPage.verifyBookedTicket(ticket.getDepartStation(), ticket.getArriveStation(), ticket.getSeatType(), ticket.getAmount(), ticket.getDepartDate() ));

        softAssert.assertAll();


    }


}
