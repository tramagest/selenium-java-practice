package testcases;

import common.Utilities.DateUtils;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import common.Utilities.DateUtils;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.*;

import org.openqa.selenium.WindowType;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.TimetablePage;
import pageObjects.Railway.TicketPricePage;

public class BookTicketTest extends BaseTest {
    @Test
    public void TC12_UserCanCook1TicketAtATime() {


        String departStation = "Nha Trang";
        String arriveStation = "Huế";
        String seatType = "Soft bed with air conditioner";
        Integer amount = 1;


        SoftAssert softAssert = new SoftAssert();

        //1. Navigate to QA Railway Website

        driver.get(Constant.RAILWAY_URL);


        //2. Login with a valid account

        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        //3. Click on "Book ticket" tab

        basePage.gotoBookTicketPage();

        //4. Select the next 12 days from "Depart date"
        //5. Select Depart from "Nha Trang" and Arrive at "Huế"
        //6. Select "Soft bed with air conditioner" for "Seat type"
        //7. Select "1" for "Ticket amount"
        //8. Click on "Book ticket" button

        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        String date = DateUtils.getFutureDate(12);

        bookTicketPage.bookTicket(date, departStation, arriveStation, seatType, amount);

        //Verify

        String actualMessage = bookTicketPage.getSuccessBookTicketMessage();
        String expectedMessage = "Ticket booked successfully!";


        // Verify
        Assert.assertEquals(actualMessage, expectedMessage, "Success message is not displayed correctly.");

        Assert.assertEquals(bookTicketPage.getDepartStation(), departStation);
        Assert.assertEquals(bookTicketPage.getArriveStation(), arriveStation);
        Assert.assertEquals(bookTicketPage.getSeatType(), seatType);
        Assert.assertEquals(bookTicketPage.getDepartDate(), date);
        Assert.assertEquals(bookTicketPage.getAmount(), String.valueOf(amount));


        softAssert.assertAll();

    }


    @Test
    public void TC13_UserCanBookManyTicketsAtATime() {

        String departStation = "Nha Trang";
        String arriveStation = "Sài Gòn";
        String seatType = "Soft seat with air conditioner";
        Integer amount = 5;

        SoftAssert softAssert = new SoftAssert();

        //1. Navigate to QA Railway Website
        driver.get(Constant.RAILWAY_URL);


        //2. Login with a valid account

        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        //3. Click on "Book ticket" tab

        basePage.gotoBookTicketPage();

        //4. Select the next 25 days from "Depart date"
        //5. Select "Nha Trang" for "Depart from" and "Sài Gòn" for "Arrive at".
        //6. Select "Soft seat with air conditioner" for "Seat type"
        //7. Select "5" for "Ticket amount"
        //8. Click on "Book ticket" button

        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        String date = DateUtils.getFutureDate(25);

        bookTicketPage.bookTicket(date, departStation, arriveStation, seatType, amount);


        //Verify

        String actualMessage = bookTicketPage.getSuccessBookTicketMessage();
        String expectedMessage = "Ticket booked successfully!";


        // Verify
        Assert.assertEquals(actualMessage, expectedMessage, "Success message is not displayed correctly.");

        Assert.assertEquals(bookTicketPage.getDepartStation(), departStation);
        Assert.assertEquals(bookTicketPage.getArriveStation(), arriveStation);
        Assert.assertEquals(bookTicketPage.getSeatType(), seatType);
        Assert.assertEquals(bookTicketPage.getDepartDate(), date);
        Assert.assertEquals(bookTicketPage.getAmount(), String.valueOf(amount));


        softAssert.assertAll();


    }


    @Test

    public void TC14_UserCanCheckPriceOfTicketFromTimetable(){

        SoftAssert softAssert = new SoftAssert();

        //1. Navigate to QA Railway Website
        driver.get(Constant.RAILWAY_URL);

        //2. Login with a valid account
        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        //3. Click on "Timetable" tab
        basePage.gotoTimetablePage();

        //4. Click on "check price" link of the route from "Đà Nẵng" to "Sài Gòn"
        TimetablePage timetablePage = new TimetablePage(driver);
        timetablePage.clickCheckPrice("Đà Nẵng", "Sài Gòn");

        //Verify
        //"Ticket Price" page is loaded.

        TicketPricePage ticketPricePage = new TicketPricePage(driver);

        softAssert.assertTrue(ticketPricePage.isTicketPricePageDisplayed(), "Ticket Price page is not loaded.");


        //Ticket table shows "Ticket price from Đà Nẵng to Sài Gòn".

        softAssert.assertEquals(ticketPricePage.getTicketPriceTableTitle(), "Ticket price from Đà Nẵng to Sài Gòn", "Ticket price title is not displayed correctly.");

        //Price for each seat displays correctly
        //HS = 310000, SS = 335000, SSC = 360000, HB = 410000, SB = 460000, SBC = 510000
        softAssert.assertEquals(ticketPricePage.getPrice("HS"), "310000", "HS price is incorrect.");
        softAssert.assertEquals(ticketPricePage.getPrice("SS"), "335000", "SS price is incorrect.");
        softAssert.assertEquals(ticketPricePage.getPrice("SSC"), "360000", "SSC price is incorrect.");
        softAssert.assertEquals(ticketPricePage.getPrice("HB"), "410000", "HB price is incorrect.");
        softAssert.assertEquals(ticketPricePage.getPrice("SB"), "460000", "SB price is incorrect.");
        softAssert.assertEquals(ticketPricePage.getPrice("SBC"), "510000", "SBC price is incorrect.");


        softAssert.assertAll();

    }


    @Test

    public void TC15_UserCanBookTicketFromTimetable(){
        SoftAssert softAssert = new SoftAssert();

        String departStation = "Quảng Ngãi";
        String arriveStation = "Huế";
        Integer amount = 5;



        //1. Navigate to QA Railway Website
        driver.get(Constant.RAILWAY_URL);


        //2. Login with a valid account

        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        //3. Click on "Timetable" tab

        basePage.gotoTimetablePage();

        //4. Click on book ticket of route "Quảng Ngãi" to "Huế"

        TimetablePage timetablePage = new TimetablePage(driver);
        timetablePage.clickBookTicket("Quảng Ngãi", "Huế");

        //Verify: Book ticket form is shown with the corrected "depart from" and "Arrive at"

        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        softAssert.assertEquals(bookTicketPage.getSelectedDepartStation(), departStation, "Depart station is not displayed correctly.");
        softAssert.assertEquals(bookTicketPage.getSelectedArriveStation(), arriveStation, "Arrive station is not displayed correctly.");



        //5. Select Depart date next 10 days
        //6. Select Ticket amount = 5
        //7. Click on "Book ticket" button

        String date = DateUtils.getFutureDate(10);

        String seatType = bookTicketPage.getSelectedSeatType();

        bookTicketPage.bookTicket(date, null,null, null, amount);


        //Verify: Message "Ticket booked successfully!" displays.
        //Ticket information display correctly (Depart Date,  Depart Station,  Arrive Station,  Seat Type,  Amount)


        String actualMessage = bookTicketPage.getSuccessBookTicketMessage();
        String expectedMessage = "Ticket booked successfully!";

        Assert.assertEquals(actualMessage, expectedMessage, "Success message is not displayed correctly.");

        Assert.assertEquals(bookTicketPage.getDepartStation(), departStation);
        Assert.assertEquals(bookTicketPage.getArriveStation(), arriveStation);
        Assert.assertEquals(bookTicketPage.getSeatType(), seatType);
        Assert.assertEquals(bookTicketPage.getDepartDate(), date);
        Assert.assertEquals(bookTicketPage.getAmount(), String.valueOf(amount));




        softAssert.assertAll();

    }



}
