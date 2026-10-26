
package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.*;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.guerrilamaill.EmailPage;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private final By registerPageTitle = By.xpath("//*[@id='content']/h1");

    private  final By formRegister = By.id("RegisterForm");

    private final By txtEmail = By.id("email");

    private final By txtPassword = By.id("password");

    private final By txtConfirmPassword = By.id("confirmPassword");

    private final By txtPID = By.id("pid");

    private final By messageConfirmRegister = By.id("content");

    private final By btnSubmit =  By.xpath("//input[@type='submit']");

    private final By registerErrorMessage = By.xpath("//*[@id='content']/p[2]");

    private final By registerErrorPasswordMessage = By.xpath("//label[@for='password' and @class='validation-error']");

    private final By registerErrorPIDMessage = By.xpath("//label[@for='pid' and @class='validation-error']");

    public String getRegisterErrorPasswordMessage() {
        return find(registerErrorPasswordMessage).getText();
    }

    public boolean verifyRegisterErrorPasswordMessage(String message) {
        return find(registerErrorPasswordMessage).isDisplayed();
    }

    public String getRegisterErrorPIDMessage() {
        return find(registerErrorPIDMessage).getText();
    }

    public boolean verifyRegisterErrorPIDMessage(String message) {
        return find(registerErrorPIDMessage).isDisplayed();
    }

    public String getRegisterErrorMessage() {
        return find(registerErrorMessage).getText();
    }


    public boolean verifyRegisterErrorMessage(String expectedErrorMessage) {
        return find(registerErrorMessage).getText().equals(expectedErrorMessage);
    }

    public void register(String email, String password, String confirmPassword, String pid) {
        find(txtEmail).sendKeys(email);
        find(txtPassword).sendKeys(password);
        find(txtConfirmPassword).sendKeys(confirmPassword);
        find(txtPID).sendKeys(pid);
        click(btnSubmit);
    }

    public boolean isErrorMessageAboveForm() {
        WebElement errorMessage = driver.findElement(registerErrorMessage);
        WebElement form = driver.findElement(formRegister);

        int errorY = errorMessage.getLocation().getY();
        int errorHeight = errorMessage.getSize().getHeight();
        int formY = form.getLocation().getY();

        return errorY + errorHeight <= formY;
    }


    public boolean isregisterPageTitleDisplayed() {
        return find(registerPageTitle).getText().trim().equals("Create account");
    }

    public String getRegisterSuccessTitle() {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    String text = d.findElement(registerPageTitle).getText().trim();
                    return text.contains("Thank you") ? text : null;
                });
    }

    public String getmessageConfirmRegister(){

        return find(messageConfirmRegister).getText();
    }

    public boolean verifymessageConfirmRegister(String message) {

        return find(messageConfirmRegister).getText().equals(message);
    }

    public String createActivatedAccount() {
        EmailPage emailPage = new EmailPage(driver);
        String email = emailPage.openMailbox();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.RAILWAY_URL);
        new HomePage(driver).clickCreateAccountLink();

        register(email, Constant.PASSWORD, Constant.PASSWORD, Constant.PID);

        emailPage.switchToMailTab();
        emailPage.clickMessageConfirm();

        Set<String> oldTabs = driver.getWindowHandles();
        emailPage.clickLinkConfirmMail();
        switchToNewTab(oldTabs);

        return email;
    }


}
