package Exercise3;

import common.Utilities.Log;
import dataObjects.Account;
import dataObjects.Message;
import dataObjects.Station;
import dataObjects.Ticket;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.RegisterPage;
import testcases.BaseTest;

public class Exercise3 extends BaseTest {

    @Test
    public void Exercise3() {
        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket(
                null,
                Station.PHAN_THIET,
                null,
                2,
                null
        );

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        String email=registerPage.createActivatedAccount();

        Log.step("1. Navigate to Railway");
        basePage.navigatetoRailway();

        Log.step("2. Login with the newly activated account");
        LoginPage loginPage = basePage.gotoLoginPage();
        loginPage.login(email, Account.VALID.getPassword());

        Log.step("3. Open Book ticket and book 2 tickets to Phan Thiết");
        basePage.gotoBookTicketPage();
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
}