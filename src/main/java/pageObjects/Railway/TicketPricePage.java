package pageObjects.Railway;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    private final By btnCancelAll =
            By.xpath("//table[contains(@class,'MyTable')]//input[@value='Cancel']");



    public String getTicketPriceTableTitle() {
        return find(lblTicketPriceTableTitle).getText().trim();
    }

    public boolean isTicketPricePageDisplayed(){
        return find(lblTicketPricePageTitle).getText().trim().equals("Ticket Price");
    }




    public String getPrice(String seatCode) {
        return find(By.xpath(String.format(PRICE_BY_SEAT_XPATH, seatCode))).getText().trim();
    }





}
