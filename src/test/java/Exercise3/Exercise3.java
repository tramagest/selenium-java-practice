
package Exercise3;

import pageObjects.Railway.*;
import common.Constant.Constant;
import testcases.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Exercise3 extends BaseTest {

    @Test
    public void Exercise3() {

        SoftAssert softAssert = new SoftAssert();

        String arriveStation = "Phan Thiết";
        Integer amount = 2;

        BasePage basePage = new BasePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        //create new account

        String email= registerPage.createActivatedAccount();

        //Navigate to railway http://saferailway.somee.com/

        driver.get(Constant.RAILWAY_URL);

        basePage.gotoLoginPage();

        LoginPage loginPage = basePage.gotoLoginPage();
        loginPage.login(email, Constant.PASSWORD);

        //Go to “Book ticket”, book 2 tickets to PhanThiet

        BookTicketPage bookTicketPage = basePage.gotoBookTicketPage();
        bookTicketPage.bookTicket(null, null, arriveStation, null, amount);

        //Verify success bookticket message

        softAssert.assertEquals(bookTicketPage.getSuccessBookTicketMessage(), "Ticket booked successfully!");

        //Verify info ticket

        softAssert.assertEquals(bookTicketPage.getArriveStation(), "Phan Thiết");
        softAssert.assertEquals(bookTicketPage.getAmount(), "2");

        //Cleanup data

        MyTicketPage myTicketPage = basePage.gotoMyTicketPage();
        basePage.gotoMyTicketPage();

        myTicketPage.cancelTicket(null, null, arriveStation, null, amount);


        softAssert.assertAll();


    }
}
