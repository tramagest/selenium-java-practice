package pageObjects.Railway;

import dataObjects.Message;
import dataObjects.SeatType;
import dataObjects.Station;
import dataObjects.TableColumn;
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

    public String getTicketInfo(TableColumn column) {
        return find(By.xpath(String.format(TICKET_INFO_XPATH, column.getName()))).getText().trim();
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

    public String getDepartStation() { return getTicketInfo(TableColumn.DEPART_STATION); }
    public String getArriveStation() { return getTicketInfo(TableColumn.ARRIVE_STATION); }
    public String getSeatType()      { return getTicketInfo(TableColumn.SEAT_TYPE); }
    public String getDepartDate()    { return getTicketInfo(TableColumn.DEPART_DATE); }
    public String getBookDate()      { return getTicketInfo(TableColumn.BOOK_DATE); }
    public String getExpiredDate()   { return getTicketInfo(TableColumn.EXPIRED_DATE); }
    public String getAmount()        { return getTicketInfo(TableColumn.AMOUNT); }
    public String getTotalPrice()    { return getTicketInfo(TableColumn.TOTAL_PRICE); }

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


    public boolean verifyBookedTicket(Ticket ticket) {
        return (ticket.getDepartDate() == null || ticket.getDepartDate().equals(getDepartDate()))
                && (ticket.getArriveStation() == null || ticket.getArriveStationName().equals(getArriveStation()))
                && (ticket.getSeatType() == null || ticket.getSeatTypeName().equals(getSeatType()))
                && (ticket.getAmount() == null || ticket.getAmount().equals(Integer.valueOf(getAmount())))
                && (ticket.getDepartStation() == null || ticket.getDepartStationName().equals(getDepartStation()));
    }
}