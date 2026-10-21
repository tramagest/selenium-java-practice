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

    public String getLoginErrorMessage() {
        return find(loginErrorMessage).getText();
    }


    private final By btnSubmit =
            By.xpath("//input[@type='submit']");

    public void login(String email, String password) {
        find(txtUsername).sendKeys(email);
        find(txtPassword).sendKeys(password);
        click(btnSubmit);
    }
}