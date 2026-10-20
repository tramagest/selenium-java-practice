package pageObjects.guerrilamaill;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class EmailPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private final By aliasCheckbox = By.id("use-alias");


    private final By emailWidget = By.id("email-widget");


    public EmailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );
    }



    public void setUseAlias(boolean useAlias) {
        WebElement checkbox = wait.until(
                ExpectedConditions.elementToBeClickable(aliasCheckbox)
        );

        if (checkbox.isSelected() != useAlias) {
            checkbox.click();
        }
    }
    public void openInbox() {
        driver.get(Constant.GUERILLAMAIL_URL);
    }

    public String getEmailAddress() {
        WebElement emailElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailWidget)
        );

        String email = emailElement.getAttribute("value");

        if (email == null || email.isBlank()) {
            email = emailElement.getText();
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalStateException(
                    "Không lấy được địa chỉ email từ email-widget."
            );
        }

        return email.trim();
    }
}
