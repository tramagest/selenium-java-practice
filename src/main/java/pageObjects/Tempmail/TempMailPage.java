package pageObjects.Tempmail;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import pageObjects.Railway.BasePage;
import java.time.Duration;

public class TempMailPage extends BasePage {
    public TempMailPage(WebDriver driver) {
        super(driver);
    }
    private final By btnRefresh = By.id("click-to-refresh");
    private final By inputEmail = By.id("mail");
    private final By emailList = By.xpath("//div[@class='email-list']//a");
    private final By messageConfirmMail = By.xpath("//a[contains(.,'Please confirm your account')]");
    private final By lnkConfirmMail = By.xpath("//a[contains(@href,'saferailway.somee.com/Account/Confirm')]");


    public void clickMessageConfirmMail() {
        find(messageConfirmMail).click();
    }

    public void clickLinkConfirmMail(){
        find(lnkConfirmMail).click();
    }



    public void clickButtonRefresh() {
        WebElement btn = driver.findElement(By.id("click-to-refresh"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", btn);
        btn.click();
    }

    public String getTempEmail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(d -> {
            String value = d.findElement(inputEmail).getAttribute("value");
            return value != null && value.contains("@");
        });

        return find(inputEmail).getAttribute("value");
    }


}
