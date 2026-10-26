package Exercise5And8;

import common.Constant.Constant;
import dataObjects.Ticket;
import pageObjects.Railway.BasePage;
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


        basePage.navigatetoRailway();

        basePage.gotoLoginPage().login(Constant.USERNAME, Constant.PASSWORD);

        basePage.gotoTimetablePage();

        timetablePage.clickCheckPrice(departStation, arriveStation);

        timetablePage.clickBookTicketBySeat(seatType);

        bookTicketPage.bookTicket(ticket);



        softAssert.assertTrue(bookTicketPage.verifySuccessBookTicketMessage("Ticket booked successfully!"));

        softAssert.assertTrue(bookTicketPage.verifyBookedTicket(ticket.getDepartStation(), ticket.getArriveStation(), ticket.getSeatType(), ticket.getAmount(), ticket.getDepartDate() ));

        softAssert.assertAll();


    }


}
