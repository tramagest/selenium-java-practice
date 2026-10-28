package pageObjects.Railway;

import dataObjects.Message;
import dataObjects.SeatType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TicketPricePage extends BasePage {

    public TicketPricePage(WebDriver driver) {
        super(driver);
    }

    private final String PRICE_VND ="Price (VND)";
    private final String PRICE_SEAT_TYPE= "Seat type";


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
                PRICE_VND,PRICE_SEAT_TYPE,
                seatType.getCode());
        return find(By.xpath(xpath)).getText().trim();
    }

  }