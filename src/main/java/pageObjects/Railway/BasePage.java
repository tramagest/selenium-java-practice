package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import java.util.Set;


public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final String TAB_XPATH = "//*[@id='menu']//li[a[span[text()='%s']]]/a";

    private final By lblWelcome = By.xpath("//*[@id='banner']/div/strong");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Wait for 10s
    protected WebElement find(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // ClickTab
    protected void clickTab(String tabName) {
        By tab = By.xpath(String.format(TAB_XPATH, tabName));
        click(tab);
    }

    protected void switchToNewTab(Set<String> oldTabs) {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.numberOfWindowsToBe(oldTabs.size() + 1));

        for (String tab : driver.getWindowHandles()) {
            if (!oldTabs.contains(tab)) {
                driver.switchTo().window(tab);
                return;
            }
        }
    }





    public void logout() {
        clickTab("Log out");
    }


    public boolean isLoggedOut() {
        return driver.findElements(By.xpath("//*[@id='menu']//li[a[span[text()='Log out']]]/a")).isEmpty();
    }


    public FAQPage gotoFAQPage() {
        clickTab("FAQ");
        return new FAQPage(driver);
    }

    public LoginPage gotoLoginPage() {
        clickTab("Login");
        return new LoginPage(driver);
    }

    public TimetablePage gotoTimetablePage() {
        clickTab("Timetable");
        return new TimetablePage(driver);
    }

    public BookTicketPage gotoBookTicketPage() {
        clickTab("Book ticket");
        return new BookTicketPage(driver);
    }

    public MyTicketPage gotoMyTicketPage() {
        clickTab("My ticket");
        return new MyTicketPage(driver);
    }
    public RegisterPage gotoRegisterPage(){
        clickTab("Register" );
        return new RegisterPage(driver);
    }

    public String getWelcomeMessage() {
        return find(lblWelcome).getText();
    }

    public boolean isWelcomeUserMessageDisplayed() {
        return find(lblWelcome).getText().trim().equals("Welcome "+Constant.USERNAME);
    }

    public boolean verifyWelcomeMessage(String expectedMessage) {
        String actualMessage = getWelcomeMessage();
        return actualMessage.equals(expectedMessage);
    }

}