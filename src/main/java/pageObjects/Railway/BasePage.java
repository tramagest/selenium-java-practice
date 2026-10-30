package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.WindowType;
import dataObjects.Message;


public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private final String LOGIN = "Login";
    private final String FAQ = "FAQ";
    private final String TIMETABLE = "Timetable";
    private final String BOOK_TICKET = "Book ticket";
    private final String MY_TICKET = "My ticket";
    private final String LOG_OUT = "Log out";
    private final String REGISTER = "Register";

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
    protected void clickTab(String tab) {
        click(By.xpath(String.format(TAB_XPATH, tab)));
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
        clickTab(LOG_OUT);
    }


    public boolean isLoggedOut() {
        return driver.findElements(By.xpath(String.format(TAB_XPATH, LOG_OUT))).isEmpty();
    }


    public FAQPage gotoFAQPage() {
        clickTab(FAQ);
        return new FAQPage(driver);
    }

    public LoginPage gotoLoginPage() {
        clickTab(LOGIN);
        return new LoginPage(driver);
    }

    public TimetablePage gotoTimetablePage() {
        clickTab(TIMETABLE);
        return new TimetablePage(driver);
    }

    public BookTicketPage gotoBookTicketPage() {
        clickTab(BOOK_TICKET);
        return new BookTicketPage(driver);
    }

    public MyTicketPage gotoMyTicketPage() {
        clickTab(MY_TICKET);
        return new MyTicketPage(driver);
    }

    public RegisterPage gotoRegisterPage() {
        clickTab(REGISTER);
        return new RegisterPage(driver);
    }


    public String getWelcomeMessage() {
        return find(lblWelcome).getText().trim();
    }

    public boolean isWelcomeUserMessageDisplayed(String email) {
        List<WebElement> els = driver.findElements(lblWelcome);
        if (els.isEmpty() || !els.get(0).isDisplayed()) {
            return false;
        }
        return getWelcomeMessage().equals(Message.WELCOME_USER.format(email).trim());
    }

    public boolean verifyWelcomeMessage(String expectedMessage) {
        String actualMessage = getWelcomeMessage();
        return actualMessage.equals(expectedMessage);
    }

    public void navigatetoRailway(){
        driver.get(Constant.RAILWAY_URL);
    }

    public void navigatetoGuerrilamail(){
        driver.get(Constant.GUERILLAMAIL_URL);
    }

    public void openNewTab() {
        driver.switchTo().newWindow(WindowType.TAB);
    }

    public void switchToTab(String tabHandle) {
        driver.switchTo().window(tabHandle);
    }

    public String getCurrentTab() {
        return driver.getWindowHandle();
    }





}