package pageObjects.Railway;

import dataObjects.TableColumn;
import dataObjects.Ticket;
import dataObjects.TicketOperation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Comparator;

public class MyTicketPage extends BasePage {

    private static final String TABLE = "//table[contains(@class,'MyTable')]";

    public MyTicketPage(WebDriver driver) {
        super(driver);
    }

    private String columnCondition(TableColumn column, String value) {
        if (value == null) {
            return "";
        }
        return String.format(
                " and normalize-space(td[count(%s//th[normalize-space()='%s']/preceding-sibling::th)+1])='%s'",
                TABLE, column.getName(), value);
    }

    private By cancelButtonOfRow(Ticket ticket) {
        String amount = ticket.getAmount() == null ? null : String.valueOf(ticket.getAmount());
        String cancel = TicketOperation.CANCEL.getName();
        String xpath = TABLE + "//tr[.//input[@value='" + cancel + "']"
                + columnCondition(TableColumn.DEPART_STATION, ticket.getDepartStationName())
                + columnCondition(TableColumn.ARRIVE_STATION, ticket.getArriveStationName())
                + columnCondition(TableColumn.SEAT_TYPE, ticket.getSeatTypeName())
                + columnCondition(TableColumn.DEPART_DATE, ticket.getDepartDate())
                + columnCondition(TableColumn.AMOUNT, amount)
                + "]//input[@value='" + cancel + "']";
        return By.xpath(xpath);
    }

    private By cancelButtonOf(String ticketId) {
        return By.xpath("//input[@value='" + TicketOperation.CANCEL.getName()
                + "' and contains(@onclick,'DeleteTicket(" + ticketId + ")')]");
    }

    private String getTicketId(Ticket ticket) {
        By row = cancelButtonOfRow(ticket);
        find(row);

        return driver.findElements(row).stream()
                .map(btn -> btn.getAttribute("onclick").replaceAll("\\D", ""))
                .max(Comparator.comparingInt(Integer::parseInt))
                .orElseThrow();
    }

    private void cancelTicketById(String ticketId) {
        click(cancelButtonOf(ticketId));
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent())
                .accept();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(cancelButtonOf(ticketId)));
    }

    public String cancelTicket(Ticket ticket) {
        if (ticket == null) { return null; }
        new BasePage(driver).gotoMyTicketPage();
        if (driver.findElements(cancelButtonOfRow(ticket)).isEmpty()) {
            return null;
        }
        String ticketId = getTicketId(ticket);
        cancelTicketById(ticketId);
        return ticketId;
    }

    public boolean verifyTicketExist(String ticketId) {
        return !driver.findElements(cancelButtonOf(ticketId)).isEmpty();
    }
}