
package pageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class BookTicketPage extends BasePage {

    public BookTicketPage(WebDriver driver) {
        super(driver);
    }

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

    private final By ttlsuccessBookTicket =
            By.xpath("//h1");

    private static final String TICKET_INFO_XPATH =
            "//table[contains(@class,'MyTable')]//tr[td]"
                    + "/td[count(ancestor::table//th[normalize-space()='%s']/preceding-sibling::th)+1]";

    public String getTicketInfo(String columnName) {
        return find(By.xpath(String.format(TICKET_INFO_XPATH, columnName))).getText().trim();
    }

    public String getSelectedDepartStation() {
        return new Select(find(ddlDepartStation)).getFirstSelectedOption().getText().trim();
    }

    public String getSelectedArriveStation() {
        return new Select(find(ddlArriveStation)).getFirstSelectedOption().getText().trim();
    }

    public String getSelectedSeatType() {
        return new Select(find(ddlSeatType)).getFirstSelectedOption().getText().trim();
    }

    public String getDepartStation() {
        return getTicketInfo("Depart Station");
    }

    public String getArriveStation() {
        return getTicketInfo("Arrive Station");
    }

    public String getSeatType() {
        return getTicketInfo("Seat Type");
    }

    public String getDepartDate() {
        return getTicketInfo("Depart Date");
    }

    public String getAmount() {
        return getTicketInfo("Amount");
    }


    public String getSuccessBookTicketMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBe(
                        ttlsuccessBookTicket,
                        "Ticket booked successfully!"
                ));

        return find(ttlsuccessBookTicket).getText().trim();
    }


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

    public void selectTicketAmount(Integer amount) {
        Select select = new Select(find(ddlTicketAmount));
        select.selectByVisibleText(String.valueOf(amount));
    }

    public void bookTicket(
            String date,
            String departStation,
            String arriveStation,
            String seatType,
            Integer amount) {

        if (date != null) {
            selectDate(date);
        }
        if (departStation != null) {
            WebElement oldOption = find(ddlArriveStation).findElement(By.tagName("option"));
            selectDepartStation(departStation);
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.stalenessOf(oldOption));
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

