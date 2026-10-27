
package Exercise3;

import pageObjects.Railway.*;
import common.Constant.Constant;
import testcases.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import dataObjects.Ticket;

public class Exercise3 extends BaseTest {

    @Test
    public void Exercise3() {

        SoftAssert softAssert = new SoftAssert();

        ticket = new Ticket(null, "Phan Thiết", null, 2, null);

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        BookTicketPage bookTicketPage = new BookTicketPage(driver);

        String email= registerPage.createActivatedAccount();

        basePage.navigatetoRailway();

        basePage.gotoLoginPage();

        LoginPage loginPage = basePage.gotoLoginPage();

        loginPage.login(email, Constant.PASSWORD);

        basePage.gotoBookTicketPage();

        bookTicketPage.bookTicket(ticket);


        softAssert.assertTrue(bookTicketPage.verifySuccessBookTicketMessage("Ticket booked successfully!"));

        softAssert.assertTrue(bookTicketPage.verifyBookedTicket(null, ticket.getArriveStation(), null, ticket.getAmount(), null));

        softAssert.assertAll();


    }
}
