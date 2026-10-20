package Exercise5And8;

import common.Constant.Constant;

import pageObjects.Railway.BasePage;
import pageObjects.Railway.TimetablePage;
import pageObjects.Railway.BookTicketPage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.testng.Assert;

import org.testng.annotations.Test;
import testcases.BaseTest;

public class Exercise5And8 extends BaseTest {
    @Test
    public void Exercise5And8() {

        String departStation = "Sài Gòn";
        String arriveStation = "Đà Nẵng";
        String seatType = "Soft seat";
        Integer amount = 2;

        System.out.println("1. Login Railway System with a valid account");

        Constant.WEBDRIVER = driver;
        driver.get(Constant.RAILWAY_URL);
        BasePage basePage = new BasePage();
        basePage.gotoLoginPage().login(Constant.USERNAME, Constant.PASSWORD);


        System.out.println("2. Click on Timetable tab");

        TimetablePage timetablePage = basePage.gotoTimetablePage();


        System.out.println("3. Click on “check price” of Sai Gon-Da Nang");

        timetablePage.clickCheckPrice(departStation, arriveStation);


        System.out.println("4. Click on “book ticket” of “Soft seat”");
        timetablePage.clickBookTicketBySeat(seatType);

        System.out.println("5. Choose Depart date is next week (next 7 days) and Ticket amount is 2");
        System.out.println("6. Click on “book ticket”");

        LocalDate departDate = LocalDate.now().plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String date = departDate.format(formatter);


        BookTicketPage bookTicketPage = new BookTicketPage();

        bookTicketPage.bookTicket(date, null, null, null, amount);


        String actualMessage = bookTicketPage.getSuccessBookTicketMessage();
        String expectedMessage = "Ticket booked successfully!";

        if (actualMessage.equals(expectedMessage)) {
            System.out.println("PASS: Success message is displayed correctly.");
        } else {
            System.out.println("FAIL: Expected '" + expectedMessage + "' but got '" + actualMessage + "'");
        }

        // Verify
        Assert.assertEquals(actualMessage, expectedMessage, "Success message is not displayed correctly.");

        Assert.assertEquals(bookTicketPage.getTicketInfo("Depart Station"), departStation);
        Assert.assertEquals(bookTicketPage.getTicketInfo("Arrive Station"), arriveStation);
        Assert.assertEquals(bookTicketPage.getTicketInfo("Seat Type"), seatType);
        Assert.assertEquals(bookTicketPage.getTicketInfo("Depart Date"), date);
        Assert.assertEquals(bookTicketPage.getTicketInfo("Amount"), String.valueOf(amount));


    }


}
