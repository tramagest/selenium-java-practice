package testcases;

import common.Utilities.DateUtils;
import common.Utilities.Log;
import dataObjects.Account;
import dataObjects.Message;
import dataObjects.SeatType;
import dataObjects.Station;
import dataObjects.Ticket;
import dataObjects.TicketPrice;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.*;

public class BookTicketTest extends BaseTest {

    protected BasePage basePage;
    protected LoginPage loginPage;
    protected TimetablePage timetablePage;
    protected BookTicketPage bookTicketPage;
    protected TicketPricePage ticketPricePage;
    private Ticket ticket;

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



        Log.verify(
                softAssert,
                "Booking success message",
                Message.BOOK_SUCCESS.getText(),
                bookTicketPage.getSuccessBookTicketMessage()
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



        Log.verify(
                softAssert,
                "Booking success message",
                Message.BOOK_SUCCESS.getText(),
                bookTicketPage.getSuccessBookTicketMessage()
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



        Log.verify(
                softAssert,
                "Ticket Price page displayed",
                true,
                ticketPricePage.isTicketPricePageDisplayed()
        );

        Log.verify(
                softAssert,
                "Ticket price table title",
                Message.TICKET_PRICE_TABLE_TITLE.format(Station.DA_NANG.getName(), Station.SAI_GON.getName()),
                ticketPricePage.getTicketPriceTableTitle()
        );

        Log.verify(
                softAssert,
                "Hard seat price",
                String.valueOf(TicketPrice.HS.getPrice()),
                ticketPricePage.getPrice(TicketPrice.HS.getSeatType())
        );

        Log.verify(
                softAssert,
                "Soft seat price",
                String.valueOf(TicketPrice.SS.getPrice()),
                ticketPricePage.getPrice(TicketPrice.SS.getSeatType())
        );

        Log.verify(
                softAssert,
                "Soft seat with air conditioner price",
                String.valueOf(TicketPrice.SSC.getPrice()),
                ticketPricePage.getPrice(TicketPrice.SSC.getSeatType())
        );

        Log.verify(
                softAssert,
                "Hard bed price",
                String.valueOf(TicketPrice.HB.getPrice()),
                ticketPricePage.getPrice(TicketPrice.HB.getSeatType())
        );

        Log.verify(
                softAssert,
                "Soft bed price",
                String.valueOf(TicketPrice.SB.getPrice()),
                ticketPricePage.getPrice(TicketPrice.SB.getSeatType())
        );

        Log.verify(
                softAssert,
                "Soft bed with air conditioner price",
                String.valueOf(TicketPrice.SBC.getPrice()),
                ticketPricePage.getPrice(TicketPrice.SBC.getSeatType())
        );

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



        Log.verify(
                softAssert,
                "Departure station on booking form",
                ticket.getDepartStationName(),
                bookTicketPage.getSelectedDepartStation()
        );

        Log.verify(
                softAssert,
                "Arrival station on booking form",
                ticket.getArriveStationName(),
                bookTicketPage.getSelectedArriveStation()
        );

        String selectedSeatType = bookTicketPage.getSelectedSeatType();

        Log.step("5. Select the departure date and ticket amount 2");
        bookTicketPage.bookTicket(ticket);



        Log.verify(
                softAssert,
                "Booking success message",
                Message.BOOK_SUCCESS.getText(),
                bookTicketPage.getSuccessBookTicketMessage()
        );

        softAssert.assertTrue(
                bookTicketPage.verifyBookedTicket(ticket),
                "Booked ticket information is incorrect"
        );

        Log.verify(
                softAssert,
                "Seat type remains unchanged",
                selectedSeatType,
                bookTicketPage.getSeatType()
        );

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpTicket() {
        if (ticket != null && driver != null) {
            new MyTicketPage(driver).cancelTicket(ticket);
        }
        ticket = null;
    }
}