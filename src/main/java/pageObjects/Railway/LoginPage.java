package pageObjects.Railway;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By txtUsername = By.id("username");

    private final By txtPassword = By.id("password");


    private final By btnSubmit =
            By.xpath("//input[@type='submit']");

    public void login(String email, String password) {
        find(txtUsername).sendKeys(email);
        find(txtPassword).sendKeys(password);
        click(btnSubmit);
    }
}