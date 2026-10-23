package pageObjects.Railway;

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


    private String columnCondition(String columnName, String value) {
        if (value == null) {
            return "";
        }
        return String.format(
                " and normalize-space(td[count(%s//th[normalize-space()='%s']/preceding-sibling::th)+1])='%s'",
                TABLE, columnName, value);
    }

    private By cancelButtonOfRow(String departStation, String arriveStation, String seatType, String departDate, Integer amount) {
        String xpath = TABLE + "//tr[.//input[@value='Cancel']"
                + columnCondition("Depart Station", departStation)
                + columnCondition("Arrive Station", arriveStation)
                + columnCondition("Seat Type", seatType)
                + columnCondition("Depart Date", departDate)
                + columnCondition("Amount", amount == null ? null : String.valueOf(amount))
                + "]//input[@value='Cancel']";
        return By.xpath(xpath);
    }

    private By cancelButtonOf(String ticketId) {
        return By.xpath("//input[@value='Cancel' and contains(@onclick,'DeleteTicket(" + ticketId + ")')]");
    }

    // Get LatestID Ticket If Tickets have same info
    private String getTicketId(String departStation, String arriveStation, String seatType,
                               String departDate, Integer amount) {
        By row = cancelButtonOfRow(departStation, arriveStation, seatType, departDate, amount);
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


    public String cancelTicket(String departStation, String arriveStation, String seatType,String departDate, Integer amount) {
        if (driver.findElements(
                cancelButtonOfRow(departStation, arriveStation, seatType, departDate, amount)).isEmpty()) {
            return null;
        }
        String ticketId = getTicketId(departStation, arriveStation, seatType, departDate, amount);
        cancelTicketById(ticketId);
        return ticketId;
    }


    public boolean isTicketDisplayed(String ticketId) {
        return !driver.findElements(cancelButtonOf(ticketId)).isEmpty();
    }
}