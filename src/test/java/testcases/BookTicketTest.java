package testcases;

import common.Utilities.DateUtils;
import dataObjects.Ticket;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.*;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.TimetablePage;
import pageObjects.Railway.TicketPricePage;

public class BookTicketTest extends BaseTest {
    @Test
    public void TC12_UserCanCook1TicketAtATime() {

        SoftAssert softAssert = new SoftAssert();

        String date = DateUtils.getFutureDate(12);

        ticket = new Ticket("Nha Trang","Huế","Soft bed with air conditioner", 1, date);

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        basePage.gotoBookTicketPage();

        bookTicketPage.bookTicket(ticket);

       // Verify
        softAssert.assertTrue(bookTicketPage.verifySuccessBookTicketMessage("Ticket booked successfully!"));

        softAssert.assertTrue(bookTicketPage.verifyBookedTicket(ticket.getDepartStation(), ticket.getArriveStation(), ticket.getSeatType(), ticket.getAmount(), ticket.getDepartDate() ));

        softAssert.assertAll();

    }


    @Test
    public void TC13_UserCanBookManyTicketsAtATime() {

        SoftAssert softAssert = new SoftAssert();

        String date = DateUtils.getFutureDate(25);

        ticket = new Ticket("Nha Trang","Sài Gòn","Soft seat with air conditioner", 5, date);

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        basePage.gotoBookTicketPage();

        bookTicketPage.bookTicket(ticket);

        softAssert.assertTrue(bookTicketPage.verifySuccessBookTicketMessage("Ticket booked successfully!"));

        softAssert.assertTrue(bookTicketPage.verifyBookedTicket(ticket.getDepartStation(), ticket.getArriveStation(), ticket.getSeatType(), ticket.getAmount(), ticket.getDepartDate() ));

        softAssert.assertAll();

    }


    @Test

    public void TC14_UserCanCheckPriceOfTicketFromTimetable(){

        SoftAssert softAssert = new SoftAssert();

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TimetablePage timetablePage = new TimetablePage(driver);
        TicketPricePage ticketPricePage = new TicketPricePage(driver);

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        basePage.gotoTimetablePage();

        timetablePage.clickCheckPrice("Đà Nẵng", "Sài Gòn");

        //Verify: "Ticket Price" page is loaded.

        softAssert.assertTrue(ticketPricePage.isTicketPricePageDisplayed(), "Ticket Price page is not loaded.");

        //Ticket table shows "Ticket price from Đà Nẵng to Sài Gòn".

        softAssert.assertEquals(ticketPricePage.getTicketPriceTableTitle(), "Ticket price from Đà Nẵng to Sài Gòn", "Ticket price title is not displayed correctly.");

        //Price for each seat displays correctly
        //HS = 310000, SS = 335000, SSC = 360000, HB = 410000, SB = 460000, SBC = 510000

        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("HS",310000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("SS",335000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("SSC",360000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("HB",410000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("SB",460000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("SBC",510000 ));
        softAssert.assertAll();

    }


    @Test

    public void TC15_UserCanBookTicketFromTimetable(){

        SoftAssert softAssert = new SoftAssert();


        String date = DateUtils.getFutureDate(10);

        ticket = new Ticket("Quảng Ngãi","Huế",null,2, date );

        BasePage basePage = new BasePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TimetablePage timetablePage = new TimetablePage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);


        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        basePage.gotoTimetablePage();

        timetablePage.clickBookTicket(ticket.getDepartStation(), ticket.getArriveStation());

        //Verify: Book ticket form is shown with the corrected "depart from" and "Arrive at"

        softAssert.assertTrue(bookTicketPage.verifyDepartStation(ticket.getDepartStation()));
        softAssert.assertTrue(bookTicketPage.verifyArriveStation(ticket.getArriveStation()));

        String seatType = bookTicketPage.getSelectedSeatType();

        bookTicketPage.bookTicket(ticket);


        //Verify: Message "Ticket booked successfully!" displays.

        softAssert.assertTrue(bookTicketPage.verifySuccessBookTicketMessage("Ticket booked successfully!"));


        softAssert.assertTrue(bookTicketPage.verifyBookedTicket(ticket.getDepartStation(), ticket.getArriveStation(), seatType, ticket.getAmount(), ticket.getDepartDate() ));

       softAssert.assertAll();


    }

}