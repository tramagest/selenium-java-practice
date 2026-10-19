
package pageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class BookTicketPage extends BasePage {

    private final By ddlArriveStation =
            By.xpath("//select[@name='ArriveStation']");

    private final By ddlTicketAmount =
            By.xpath("//select[@name='TicketAmount']");

    private final By ddlDate =
            By.xpath("//select[@name='Date']");

    private final By ddlDepartStation =
            By.xpath("//select[@name='DepartStation']");

    private final By ddlSeatType =
            By.xpath("//select[@name='SeatType']");

    private final By btnBookTicket =
            By.xpath("//input[@type='submit']");


    public void selectDate(String date) {
        Select select = new Select(find(ddlDate));
        select.selectByVisibleText(date);
    }

    public void selectDepartStation(String departStation) {
        Select select = new Select(find(ddlDepartStation));
        select.selectByVisibleText(departStation);
    }


    public void selectArriveStation(String arriveStation) {
        Select select = new Select(find(ddlArriveStation));
        select.selectByVisibleText(arriveStation);
    }

    public void selectSeatType(String seatType) {
        Select select = new Select(find(ddlSeatType));
        select.selectByVisibleText(seatType);
    }

    public void selectTicketAmount(String amount) {
        Select select = new Select(find(ddlTicketAmount));
        select.selectByVisibleText(amount);
    }

    public void bookTicket(
            String date,
            String departStation,
            String arriveStation,
            String seatType,
            String amount) {

        if (date != null) {
            selectDate(date);
        }
        if (departStation != null) {
            selectDepartStation(departStation);
        }
        if (arriveStation != null) {
            selectArriveStation(arriveStation);
        }
        if (seatType != null) {
            selectSeatType(seatType);
        }
        if (amount != null) {
            selectTicketAmount(amount);
        }
        click(btnBookTicket);
    }
}
