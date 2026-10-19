package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    private static final String TAB_XPATH = "//*[@id='menu']//li[a[span[text()='%s']]]/a";

    private final By lblWelcome = By.xpath("//*[@id='banner']/div/strong");

    // Wait for 10s
    protected WebElement find(By locator) {
        return new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Scroll
    protected void click(By locator) {
        WebElement element = find(locator);
        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    // ClickTab
    protected void clickTab(String tabName) {
        By tab = By.xpath(String.format(TAB_XPATH, tabName));
        click(tab);
    }



    public LoginPage gotoLoginPage() {
        clickTab("Login");
        return new LoginPage();
    }

    public TimetablePage gotoTimetablePage() {
        clickTab("Timetable");
        return new TimetablePage();
    }

    public BookTicketPage gotoBookTicketPage() {
        clickTab("Book ticket");
        return new BookTicketPage();
    }

    public MyTicketPage gotoMyTicketPage() {
        clickTab("My ticket");
        return new MyTicketPage();
    }
    public RegisterPage gotoRegisterPage(){
        clickTab("Register" );
        return new RegisterPage();
    }

}