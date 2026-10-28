package testcases;

import common.Utilities.DateUtils;
import common.Utilities.Log;
import dataObjects.Account;
import dataObjects.Message;
import dataObjects.SeatType;
import dataObjects.Station;
import dataObjects.Ticket;
import dataObjects.TicketPrice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.TicketPricePage;
import pageObjects.Railway.TimetablePage;

public class BookTicketTest extends BaseTest {

    protected BasePage basePage;
    protected LoginPage loginPage;
    protected TimetablePage timetablePage;
    protected BookTicketPage bookTicketPage;
    protected TicketPricePage ticketPricePage;

    @BeforeMethod
    public void initPageObjects() {
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        timetablePage = new TimetablePage(driver);
        bookTicketPage = new BookTicketPage(driver);
        ticketPricePage = new TicketPricePage(driver);
    }

    @Test(description = "TC12: User can book 1 ticket at a time")
    public void TC12_UserCanCook1TicketAtATime() {
        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket(
                Station.NHA_TRANG,
                Station.HUE,
                SeatType.SBC,
                1,
                DateUtils.getFutureDate(12)
        );

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        basePage.gotoLoginPage();
        Log.step("2. Login with a valid account");
        loginPage.login(Account.VALID.getEmail(), Account.VALID.getPassword());

        Log.step("3. Click on Book ticket tab");
        basePage.gotoBookTicketPage();

        Log.step("4. Select date, stations, seat type and ticket amount");
        bookTicketPage.bookTicket(ticket);

        Log.info("Verify booked ticket information");

        softAssert.assertEquals(
                bookTicketPage.getSuccessBookTicketMessage(),
                Message.BOOK_SUCCESS.getText(),
                "Booking success message is incorrect"
        );

        softAssert.assertTrue(
                bookTicketPage.verifyBookedTicket(ticket),
                "Booked ticket information is incorrect"
        );

        softAssert.assertAll();
    }

    @Test(description = "TC13: User can book many tickets at a time")
    public void TC13_UserCanBookManyTicketsAtATime() {
        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket(
                Station.NHA_TRANG,
                Station.SAI_GON,
                SeatType.SSC,
                5,
                DateUtils.getFutureDate(25)
        );

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");
        basePage.gotoLoginPage();
        loginPage.login(Account.VALID.getEmail(), Account.VALID.getPassword());

        Log.step("3. Click on Book ticket tab");
        basePage.gotoBookTicketPage();

        Log.step("4. Select the departure date, route, seat type and amount 5");
        bookTicketPage.bookTicket(ticket);

        Log.info("Verify booked ticket information");

        softAssert.assertEquals(
                bookTicketPage.getSuccessBookTicketMessage(),
                Message.BOOK_SUCCESS.getText(),
                "Booking success message is incorrect"
        );

        softAssert.assertTrue(
                bookTicketPage.verifyBookedTicket(ticket),
                "Booked ticket information is incorrect"
        );

        softAssert.assertAll();
    }

    @Test(description = "TC14: User can check ticket price from Timetable")
    public void TC14_UserCanCheckPriceOfTicketFromTimetable() {
        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");
        basePage.gotoLoginPage();
        loginPage.login(Account.VALID.getEmail(), Account.VALID.getPassword());

        Log.step("3. Click on Timetable tab");
        basePage.gotoTimetablePage();

        Log.step("4. Click Check Price for the route from Đà Nẵng to Sài Gòn");
        timetablePage.clickCheckPrice(Station.DA_NANG, Station.SAI_GON);

        Log.info("Verify Ticket Price page and prices");

        softAssert.assertTrue(
                ticketPricePage.isTicketPricePageDisplayed(),
                "Ticket Price page is not loaded"
        );

        softAssert.assertEquals(
                ticketPricePage.getTicketPriceTableTitle(),
                Message.TICKET_PRICE_TABLE_TITLE.format(
                        Station.DA_NANG.getName(),
                        Station.SAI_GON.getName()
                ),
                "Ticket price title is incorrect"
        );

        softAssert.assertTrue(ticketPricePage.verifyPrice(TicketPrice.HS),
                "Incorrect price for Hard seat");

        softAssert.assertTrue(ticketPricePage.verifyPrice(TicketPrice.SS),
                "Incorrect price for Soft seat");

        softAssert.assertTrue(ticketPricePage.verifyPrice(TicketPrice.SSC),
                "Incorrect price for Soft seat with air conditioner");

        softAssert.assertTrue(ticketPricePage.verifyPrice(TicketPrice.HB),
                "Incorrect price for Hard bed");

        softAssert.assertTrue(ticketPricePage.verifyPrice(TicketPrice.SB),
                "Incorrect price for Soft bed");

        softAssert.assertTrue(ticketPricePage.verifyPrice(TicketPrice.SBC),
                "Incorrect price for Soft bed with air conditioner");

        softAssert.assertAll();
    }

    @Test(description = "TC15: User can book ticket from Timetable")
    public void TC15_UserCanBookTicketFromTimetable() {
        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket(
                Station.QUANG_NGAI,
                Station.HUE,
                null,
                2,
                DateUtils.getFutureDate(10)
        );

        Log.step("1. Navigate to QA Railway Website");
        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");
        basePage.gotoLoginPage();
        loginPage.login(Account.VALID.getEmail(), Account.VALID.getPassword());

        Log.step("3. Click on Timetable tab");
        basePage.gotoTimetablePage();

        Log.step("4. Click Book ticket for the route from Quảng Ngãi to Huế");
        timetablePage.clickBookTicket(
                ticket.getDepartStation(),
                ticket.getArriveStation()
        );

        Log.info("Verify departure and arrival stations");

        softAssert.assertTrue(
                bookTicketPage.verifyDepartStation(ticket.getDepartStation()),
                "Departure station is incorrect"
        );

        softAssert.assertTrue(
                bookTicketPage.verifyArriveStation(ticket.getArriveStation()),
                "Arrival station is incorrect"
        );

        String selectedSeatType = bookTicketPage.getSelectedSeatType();

        Log.step("5. Select the departure date and ticket amount 2");
        bookTicketPage.bookTicket(ticket);

        Log.info("Verify booked ticket information");

        softAssert.assertEquals(
                bookTicketPage.getSuccessBookTicketMessage(),
                Message.BOOK_SUCCESS.getText(),
                "Booking success message is incorrect"
        );

        softAssert.assertTrue(
                bookTicketPage.verifyBookedTicket(ticket),
                "Booked ticket information is incorrect"
        );

        softAssert.assertEquals(
                bookTicketPage.getSeatType(),
                selectedSeatType,
                "Seat type changed unexpectedly"
        );

        softAssert.assertAll();
    }
}