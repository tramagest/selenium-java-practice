package pageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By txtUsername = By.id("username");

    private final By txtPassword = By.id("password");

    private final By loginErrorMessage = By.xpath("//*[@id='content']/p");

    private final By linkForgotPassword = By.xpath("//a[normalize-space()='Forgot Password page']");

    private final By btnSubmit =
            By.xpath("//input[@type='submit']");

    public void login(String email, String password) {
        find(txtUsername).sendKeys(email);
        find(txtPassword).sendKeys(password);
        click(btnSubmit);
    }

    public void clickForgotPasswordLink() {
        click(linkForgotPassword);
    }

    public String getLoginErrorMessage() {
        return find(loginErrorMessage).getText();
    }

    public boolean verifyLoginErrorMessage(String expectedMessage) {
        return find(loginErrorMessage).getText().equals(expectedMessage);
    }

    public void loginWithManyTimes (Integer n, String username, String password) {

        for (int i = 0; i < (n - 1); i++) {
            login(username, password);
        }

    }
}