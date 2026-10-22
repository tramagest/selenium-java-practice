package pageObjects.Railway;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyTicketPage extends BasePage {
    public MyTicketPage(WebDriver driver) {
        super(driver);
    }



    private final By btnCancelAll =
            By.xpath("//table[contains(@class,'MyTable')]//input[@value='Cancel']");

    public String getLatestTicketId() {
        find(btnCancelAll);

        int maxId = 0;
        for (WebElement btn : driver.findElements(btnCancelAll)) {
            int id = Integer.parseInt(btn.getAttribute("onclick").replaceAll("\\D", ""));
            if (id > maxId) {
                maxId = id;
            }
        }
        return String.valueOf(maxId);
    }

    private By cancelButtonOf(String ticketId) {
        return By.xpath("//input[@value='Cancel' and contains(@onclick,'DeleteTicket(" + ticketId + ")')]");
    }

    public void cancelTicketById(String ticketId) {
        click(cancelButtonOf(ticketId));
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent())
                .accept();
    }

    public boolean isTicketCanceled(String ticketId) {
        return driver.findElements(cancelButtonOf(ticketId)).isEmpty();
    }

}
