package pageObjects.Railway;

import common.Utilities.VerifyResult;
import dataObjects.Message;
import dataObjects.SeatType;
import dataObjects.Station;
import dataObjects.Ticket;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookTicketPage extends BasePage {

    public BookTicketPage(WebDriver driver) {
        super(driver);
    }

    private final String DEPART_STATION = "Depart Station";
    private final String ARRIVE_STATION = "Arrive Station";
    private final String SEAT_TYPE = "Seat Type";
    private final String DEPART_DATE = "Depart Date";
    private final String AMOUNT = "Amount";

    private final By ddlArriveStation = By.xpath("//select[@name='ArriveStation']");
    private final By ddlTicketAmount = By.xpath("//select[@name='TicketAmount']");
    private final By ddlDate = By.xpath("//select[@name='Date']");
    private final By ddlDepartStation = By.xpath("//select[@name='DepartStation']");
    private final By ddlSeatType = By.xpath("//select[@name='SeatType']");
    private final By btnBookTicket = By.xpath("//input[@type='submit']");
    private final By ttlsuccessBookTicket = By.xpath("//h1");

    private static final String TICKET_INFO_XPATH =
            "//table[contains(@class,'MyTable')]//tr[td]"
                    + "/td[count(ancestor::table//th[normalize-space()='%s']/preceding-sibling::th)+1]";

    public String getTicketInfo(String column) {
        return find(By.xpath(String.format(TICKET_INFO_XPATH, column))).getText().trim();
    }

    public String getSelectedDepartdate() {
        return new Select(find(ddlDate)).getFirstSelectedOption().getText().trim();
    }

    public String getSelectedDepartStation() {
        return new Select(find(ddlDepartStation)).getFirstSelectedOption().getText().trim();
    }

    public boolean verifyDepartStation(Station departStation) {
        return getSelectedDepartStation().equals(departStation.getName());
    }

    public String getSelectedArriveStation() {
        return new Select(find(ddlArriveStation)).getFirstSelectedOption().getText().trim();
    }

    public boolean verifyArriveStation(Station arriveStation) {
        return getSelectedArriveStation().equals(arriveStation.getName());
    }

    public String getSelectedSeatType() {
        return new Select(find(ddlSeatType)).getFirstSelectedOption().getText().trim();
    }

    public String getDepartStation() { return getTicketInfo(DEPART_STATION); }
    public String getArriveStation() { return getTicketInfo(ARRIVE_STATION); }
    public String getSeatType()      { return getTicketInfo(SEAT_TYPE); }
    public String getDepartDate()    { return getTicketInfo(DEPART_DATE); }
    public String getAmount()        { return getTicketInfo(AMOUNT); }


    public String getSuccessBookTicketMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBe(ttlsuccessBookTicket, Message.BOOK_SUCCESS.getText()));
        return find(ttlsuccessBookTicket).getText().trim();
    }

    public boolean verifySuccessBookTicketMessage(Message message) {
        return getSuccessBookTicketMessage().equals(message.getText());
    }

    public void selectDate(String date) {
        new Select(find(ddlDate)).selectByVisibleText(date);
    }

    public void selectDepartStation(Station station) {
        new Select(find(ddlDepartStation)).selectByVisibleText(station.getName());
    }

    public void selectArriveStation(Station station) {
        new Select(find(ddlArriveStation)).selectByVisibleText(station.getName());
    }

    public void selectSeatType(SeatType seatType) {
        new Select(find(ddlSeatType)).selectByVisibleText(seatType.getName());
    }

    public void selectTicketAmount(Integer amount) {
        new Select(find(ddlTicketAmount)).selectByVisibleText(String.valueOf(amount));
    }

    public void bookTicket(Ticket ticket) {
        if (ticket.getDepartDate() != null) {
            selectDate(ticket.getDepartDate());
        }
        if (ticket.getDepartStation() != null
                && !getSelectedDepartStation().equals(ticket.getDepartStationName())) {
            WebElement oldOption = find(ddlArriveStation).findElement(By.tagName("option"));
            selectDepartStation(ticket.getDepartStation());
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.stalenessOf(oldOption));
        }
        if (ticket.getArriveStation() != null) {
            selectArriveStation(ticket.getArriveStation());
        }
        if (ticket.getSeatType() != null) {
            selectSeatType(ticket.getSeatType());
        }
        if (ticket.getAmount() != null) {
            selectTicketAmount(ticket.getAmount());
        }
        click(btnBookTicket);
    }




    public VerifyResult verifyBookedTicket(String departDate, String departStation,
                                           String arriveStation, String seatType, Integer amount) {
        String expected = "";
        String actual = "";

        if (departDate != null) {
            expected += "Depart date: " + departDate + " | ";
            actual += "Depart date: " + getDepartDate() + " | ";
        }

        if (departStation != null) {
            expected += "Depart station: " + departStation + " | ";
            actual += "Depart station: " + getDepartStation() + " | ";
        }

        if (arriveStation != null) {
            expected += "Arrive station: " + arriveStation + " | ";
            actual += "Arrive station: " + getArriveStation() + " | ";
        }

        if (seatType != null) {
            expected += "Seat type: " + seatType + " | ";
            actual += "Seat type: " + getSeatType() + " | ";
        }

        if (amount != null) {
            expected += "Amount: " + amount + " | ";
            actual += "Amount: " + getAmount() + " | ";
        }

        boolean result = expected.equals(actual);

        return new VerifyResult(result, expected, actual);
    }
}