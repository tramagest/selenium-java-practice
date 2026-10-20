
package pageObjects.Railway;

import org.openqa.selenium.By;

public class RegisterPage extends BasePage {

    private final By txtEmail = By.id("email");

    private final By txtPassword = By.id("password");

    private final By txtConfirmPassword = By.id("confirmPassword");

    private final By txtPID = By.id("pid");

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
