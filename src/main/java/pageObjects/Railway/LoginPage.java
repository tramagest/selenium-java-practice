package pageObjects.Railway;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By txtUsername =
            By.xpath("//input[@id='username']");

    private final By txtPassword =
            By.xpath("//input[@id='password']");

    private final By btnSubmit =
            By.xpath("//input[@type='submit']");

    public void login(String email, String password) {
        find(txtUsername).sendKeys(email);
        find(txtPassword).sendKeys(password);
        click(btnSubmit);
    }
}