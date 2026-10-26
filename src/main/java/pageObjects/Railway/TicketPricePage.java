package pageObjects.Railway;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class TicketPricePage extends BasePage {

    public TicketPricePage(WebDriver driver) {
        super(driver);
    }
    private final By lblTicketPricePageTitle =
            By.xpath("//div[@id='content']/h1");

    private final By lblTicketPriceTableTitle =
            By.xpath("//table[contains(@class,'MyTable')]//tr[@class='TableSmallHeader']/th");


    private static final String PRICE_BY_SEAT_XPATH =
            "//table[contains(@class,'MyTable')]//tr[th[normalize-space()='Price (VND)']]"
                    + "/td[count(//table[contains(@class,'MyTable')]//tr[th[normalize-space()='Seat type']]"
                    + "/td[normalize-space()='%s']/preceding-sibling::td)+1]";

    public String getTicketPriceTableTitle() {
        return find(lblTicketPriceTableTitle).getText().trim();
    }

    public boolean isTicketPricePageDisplayed(){
        return find(lblTicketPricePageTitle).getText().trim().equals("Ticket Price");
    }

    public String getPrice(String seatCode) {
        return find(By.xpath(String.format(PRICE_BY_SEAT_XPATH, seatCode))).getText().trim();
    }

    public boolean verifyPriceForSeatType(String seatCode, Integer price) {
        return Integer.valueOf(getPrice(seatCode)).equals(price);
    }


}
