
package pageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private  final By formRegister = By.id("RegisterForm");

    private final By txtEmail = By.id("email");

    private final By txtPassword = By.id("password");

    private final By txtConfirmPassword = By.id("confirmPassword");

    private final By txtPID = By.id("pid");

    private final By btnSubmit =
            By.xpath("//input[@type='submit']");


    private final By registerSuccessMessage = By.xpath("//*[@id='content']/p");


    private final By registerErrorMessage = By.xpath("//*[@id='content']/p[2]");


    private final By registerErrorPasswordMessage = By.xpath("//label[@for='password' and @class='validation-error']");

    private final By registerErrorPIDMessage = By.xpath("//label[@for='pid' and @class='validation-error']");

    public String getRegisterErrorPasswordMessage() {
        return find(registerErrorPasswordMessage).getText();
    }

    public String getRegisterErrorPIDMessage() {
        return find(registerErrorPIDMessage).getText();
    }

    public String getRegisterErrorMessage() {
        return find(registerErrorMessage).getText();
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


}
