
package pageObjects.Railway;

import org.openqa.selenium.By;

public class RegisterPage extends BasePage {

    private final By txtEmail =
            By.xpath("//input[@id='email']");

    private final By txtPassword =
            By.xpath("//input[@id='password']");

    private final By txtConfirmPassword =
            By.xpath("//input[@id='confirmPassword']");

    private final By txtPID =
            By.xpath("//input[@id='pid']");

    private final By btnSubmit =
            By.xpath("//input[@type='submit']");

    public void register(String email, String password, String pid) {
        find(txtEmail).sendKeys(email);
        find(txtPassword).sendKeys(password);
        find(txtConfirmPassword).sendKeys(password);
        find(txtPID).sendKeys(pid);
        click(btnSubmit);
    }
}
