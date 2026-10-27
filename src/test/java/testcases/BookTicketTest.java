package testcases;

import common.Utilities.DateUtils;
import common.Utilities.Log;
import dataObjects.Ticket;
import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.*;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.TimetablePage;
import pageObjects.Railway.TicketPricePage;
import org.testng.annotations.BeforeMethod;

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

    @Test (description = "TC12: User can book 1 ticket at a time")
    public void TC12_UserCanCook1TicketAtATime() {

        SoftAssert softAssert = new SoftAssert();

        String date = DateUtils.getFutureDate(12);

        ticket = new Ticket("Nha Trang","Huế","Soft bed with air conditioner", 1, date);

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        Log.step("2. Login with a valid account ");

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Log.step("3. Click on \"Book ticket\" tab ");

        basePage.gotoBookTicketPage();

        Log.step("4. Select the next 12 days from \"Depart date\"");
        Log.step("5. Select Depart from \"Nha Trang\" and Arrive at \"Huế\"");
        Log.step("6. Select \"Soft bed with air conditioner\" for \"Seat type\"");
        Log.step("7. Select \"1\" for \"Ticket amount\"");
        Log.step("8. Click on \"Book ticket\" button");

        bookTicketPage.bookTicket(ticket);

        Log.info("Verify Book Ticket");

        softAssert.assertEquals(bookTicketPage.getSuccessBookTicketMessage(),"Ticket booked successfully!","Message is incorrect");

        softAssert.assertEquals(bookTicketPage.getDepartStation(),ticket.getDepartStation());
        softAssert.assertEquals(bookTicketPage.getArriveStation(),ticket.getArriveStation());
        softAssert.assertEquals(bookTicketPage.getSeatType(),ticket.getSeatType());
        softAssert.assertEquals(bookTicketPage.getAmount(),String.valueOf(ticket.getAmount()));
        softAssert.assertEquals(bookTicketPage.getDepartDate(),ticket.getDepartDate());

        softAssert.assertAll();

    }


    @Test (description = "TC13: User can book many tickets at a time")
    public void TC13_UserCanBookManyTicketsAtATime() {

        SoftAssert softAssert = new SoftAssert();

        String date = DateUtils.getFutureDate(25);

        ticket = new Ticket("Nha Trang","Sài Gòn","Soft seat with air conditioner", 5, date);

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Log.step("3. Click on \"Book ticket\" tab");

        basePage.gotoBookTicketPage();

        Log.step("4. Select the date 25 days from today for \"Depart date\"");
        Log.step("5. Select \"Nha Trang\" for \"Depart from\" and \"Sài Gòn\" for \"Arrive at\"");
        Log.step("6. Select \"Soft seat with air conditioner\" for \"Seat type\"");
        Log.step("7. Select \"5\" for \"Ticket amount\"");
        Log.step("8. Click on \"Book ticket\" button");


        bookTicketPage.bookTicket(ticket);

        Log.info("Verify Book Ticket");

        softAssert.assertEquals(bookTicketPage.getSuccessBookTicketMessage(),"Ticket booked successfully!","Message is incorrect");

        softAssert.assertEquals(bookTicketPage.getDepartStation(),ticket.getDepartStation());
        softAssert.assertEquals(bookTicketPage.getArriveStation(),ticket.getArriveStation());
        softAssert.assertEquals(bookTicketPage.getSeatType(),ticket.getSeatType());
        softAssert.assertEquals(bookTicketPage.getAmount(),String.valueOf(ticket.getAmount()));
        softAssert.assertEquals(bookTicketPage.getDepartDate(),ticket.getDepartDate());

        softAssert.assertAll();

    }


    @Test (description = "TC14: User can check price of ticket from Timetable")

    public void TC14_UserCanCheckPriceOfTicketFromTimetable(){

        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Log.step("3. Click on \"Timetable\" tab");

        basePage.gotoTimetablePage();

        Log.step("4. Click on the \"check price\" link for the route from \"Đà Nẵng\" to \"Sài Gòn\"");

        timetablePage.clickCheckPrice("Đà Nẵng", "Sài Gòn");

        Log.info("Verify: \"Ticket Price\" page is loaded.");

        softAssert.assertTrue(ticketPricePage.isTicketPricePageDisplayed(), "Ticket Price page is not loaded.");

        //Ticket table shows "Ticket price from Đà Nẵng to Sài Gòn".

        softAssert.assertEquals(ticketPricePage.getTicketPriceTableTitle(), "Ticket price from Đà Nẵng to Sài Gòn", "Ticket price title is not displayed correctly.");

        Log.info("Verify price for each seat displays correctly");
        //HS = 310000, SS = 335000, SSC = 360000, HB = 410000, SB = 460000, SBC = 510000

        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("HS",310000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("SS",335000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("SSC",360000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("HB",410000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("SB",460000 ));
        softAssert.assertTrue(ticketPricePage.verifyPriceForSeatType("SBC",510000 ));
        softAssert.assertAll();

    }


    @Test (description = "TC15: User can book ticket from Timetable")

    public void TC15_UserCanBookTicketFromTimetable(){

        SoftAssert softAssert = new SoftAssert();

        String date = DateUtils.getFutureDate(10);

        ticket = new Ticket("Quảng Ngãi","Huế",null,2, date );

        Log.step("1. Navigate to QA Railway Website");

        basePage.navigatetoRailway();

        Log.step("2. Login with a valid account");

        basePage.gotoLoginPage();

        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Log.step("3. Click on \"Timetable\" tab");

        basePage.gotoTimetablePage();

        Log.step("4. Click on the book ticket link for the route from \"Quảng Ngãi\" to \"Huế\"");

        timetablePage.clickBookTicket(ticket.getDepartStation(), ticket.getArriveStation());

        Log.info("Verify: Book ticket form is shown with the corrected \"depart from\" and \"Arrive at\"");

        softAssert.assertTrue(bookTicketPage.verifyDepartStation(ticket.getDepartStation()));

        softAssert.assertTrue(bookTicketPage.verifyArriveStation(ticket.getArriveStation()));

        String seatType = bookTicketPage.getSelectedSeatType();

        Log.step("5. Select the date 10 days from today for \"Depart date\"");
        Log.step("6. Select \"5\" for \"Ticket amount\"");
        Log.step("7. Click on \"Book ticket\" button");

        bookTicketPage.bookTicket(ticket);

        Log.info("Verify: Message \"Ticket booked successfully!\" displays.");

        softAssert.assertEquals(bookTicketPage.getSuccessBookTicketMessage(),"Ticket booked successfully!","Message is incorrect");

        softAssert.assertEquals(bookTicketPage.getDepartStation(),ticket.getDepartStation());
        softAssert.assertEquals(bookTicketPage.getArriveStation(),ticket.getArriveStation());
        softAssert.assertEquals(bookTicketPage.getSeatType(),seatType);
        softAssert.assertEquals(bookTicketPage.getAmount(),String.valueOf(ticket.getAmount()));
        softAssert.assertEquals(bookTicketPage.getDepartDate(),ticket.getDepartDate());


        softAssert.assertAll();


    }

}