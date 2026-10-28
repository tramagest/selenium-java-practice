package pageObjects.Railway;

import dataObjects.Message;
import dataObjects.SeatType;
import dataObjects.TableColumn;
import dataObjects.TicketPrice;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TicketPricePage extends BasePage {

    public TicketPricePage(WebDriver driver) {
        super(driver);
    }

    private final By lblTicketPricePageTitle = By.xpath("//div[@id='content']/h1");

    private final By lblTicketPriceTableTitle =
            By.xpath("//table[contains(@class,'MyTable')]//tr[@class='TableSmallHeader']/th");

    private static final String PRICE_BY_SEAT_XPATH =
            "//table[contains(@class,'MyTable')]//tr[th[normalize-space()='%s']]"
                    + "/td[count(//table[contains(@class,'MyTable')]//tr[th[normalize-space()='%s']]"
                    + "/td[normalize-space()='%s']/preceding-sibling::td)+1]";

    public String getTicketPriceTableTitle() {
        return find(lblTicketPriceTableTitle).getText().trim();
    }

    public boolean isTicketPricePageDisplayed() {
        return find(lblTicketPricePageTitle).getText().trim()
                .equals(Message.TICKET_PRICE_PAGE_TITLE.getText());
    }

    public String getPrice(SeatType seatType) {
        String xpath = String.format(PRICE_BY_SEAT_XPATH,
                TableColumn.PRICE_VND.getName(),
                TableColumn.PRICE_SEAT_TYPE.getName(),
                seatType.getCode());
        return find(By.xpath(xpath)).getText().trim();
    }

    public boolean verifyPrice(TicketPrice ticketPrice) {
        return Integer.valueOf(getPrice(ticketPrice.getSeatType())).equals(ticketPrice.getPrice());
    }
}