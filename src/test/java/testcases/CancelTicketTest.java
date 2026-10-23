package testcases;


import org.testng.asserts.SoftAssert;
import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.MyTicketPage;
import pageObjects.Railway.LoginPage;


public class CancelTicketTest extends BaseTest {
    @Test
    public void TC16_UserCanCancelATicket() {

        SoftAssert softAssert = new SoftAssert();

        String departStation = "Nha Trang";
        String arriveStation = "Sài Gòn";
        String seatType = "Soft seat with air conditioner";
        Integer amount = 1;
        String date = null;

        //1. Navigate to QA Railway Website
        driver.get(Constant.RAILWAY_URL);

        //2. Login with a valid account

        BasePage basePage = new BasePage(driver);
        basePage.gotoLoginPage();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        //3. Book a ticket

        basePage.gotoBookTicketPage();
        BookTicketPage bookTicketPage = new BookTicketPage(driver);
        bookTicketPage.bookTicket(date, departStation, arriveStation, seatType, amount);

        //4. Click on "My ticket" tab

        basePage.gotoMyTicketPage();

        //5. Click on "Cancel" button of ticket which user want to cancel.
        //6. Click on "OK" button on Confirmation message "Are you sure?"

        MyTicketPage myTicketPage = new MyTicketPage(driver);
        String ticketId = myTicketPage.cancelTicket(departStation, arriveStation, seatType, date, amount);

        softAssert.assertNotNull(ticketId, "No ticket found to cancel.");
        softAssert.assertFalse(myTicketPage.isTicketDisplayed(ticketId), "Ticket was not canceled.");


        softAssert.assertAll();

    }
}
