
package Exercise3;

import pageObjects.Railway.*;
import common.Constant.Constant;
import testcases.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import dataObjects.Ticket;
import common.Utilities.Log;

public class Exercise3 extends BaseTest {

    @Test
    public void Exercise3() {

        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket(null, "Phan Thiết", null, 2, null);

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        String email= registerPage.createActivatedAccount();

        Log.step("1. Navigate to Railway");

        basePage.navigatetoRailway();

        Log.step("2. Login");

        basePage.gotoLoginPage();

        LoginPage loginPage = basePage.gotoLoginPage();

        loginPage.login(email, Constant.PASSWORD);

        basePage.gotoBookTicketPage();

        Log.step("Book 2 tickets to PhanThiet");

        bookTicketPage.bookTicket(ticket);

        softAssert.assertEquals(bookTicketPage.getSuccessBookTicketMessage(),"Ticket booked successfully!");

        softAssert.assertTrue(bookTicketPage.verifyBookedTicket(null, ticket.getArriveStation(), null, ticket.getAmount(), null));

        softAssert.assertEquals(bookTicketPage.getArriveStation(),ticket.getArriveStation());
        softAssert.assertEquals(bookTicketPage.getAmount(),String.valueOf(ticket.getAmount()));
        softAssert.assertAll();


    }
}
