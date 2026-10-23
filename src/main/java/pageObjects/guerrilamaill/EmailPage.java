package pageObjects.guerrilamaill;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.Railway.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;

public class EmailPage  extends BasePage {

    public EmailPage(WebDriver driver) {
        super(driver);
    }

    private final By aliasCheckbox = By.id("use-alias");

    private final By messageConfirm = By.xpath("//tbody[@id='email_list']//td[contains(., 'Please confirm your account')]");

    private final By messageResetPassword = By.xpath("//tbody[@id='email_list']//td[contains(., 'Please reset your password')]");

    private final By emailWidget = By.id("email-widget");
    private final By lnkConfirmMail = By.xpath("//a[contains(@href,'saferailway')]");

    private final By txtResetToken = By.xpath("//div[@id='display_email']//div[@class='email_body']");

    private final By linkResetPassword = By.xpath("//div[@class='email_body']//a[contains(@href,'PasswordReset')]");



    public String getEmailResetToken() {
        String emailBody = find(txtResetToken).getText();

        return emailBody.split("The token is:")[1].split("\\. Visit")[0].trim();
    }


    public void clickLinkResetPassword() {
        click(linkResetPassword);
    }

    public void clickmessageResetPassword() {
        driver.get(Constant.GUERILLAMAIL_URL);
        closeAdIfPresent();

        new WebDriverWait(driver, Duration.ofSeconds(35))
                .until(ExpectedConditions.visibilityOfElementLocated(messageResetPassword)).click();
    }

    public void clickMessageConfirm() {
        closeAdIfPresent();

        WebElement row = new WebDriverWait(driver, Duration.ofSeconds(35))
                .until(ExpectedConditions.presenceOfElementLocated(messageConfirm));

        row.click();
    }

    public void clickLinkConfirmMail() {
        WebElement link = new WebDriverWait(driver, Duration.ofSeconds(35))
                .until(ExpectedConditions.presenceOfElementLocated(lnkConfirmMail));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }



    public void setUseAlias(boolean useAlias) {
        WebElement checkbox = wait.until(
                ExpectedConditions.elementToBeClickable(aliasCheckbox)
        );

        if (checkbox.isSelected() != useAlias) {
            checkbox.click();
        }
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



    public void closeAdIfPresent() {
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('ins.adsbygoogle, iframe[id^=\"google_ads_iframe\"], "
                        + "iframe[id^=\"aswift\"], div[id^=\"aswift\"]').forEach(e => e.remove());"
                        + "document.body.style.overflow='auto';");
    }
}
