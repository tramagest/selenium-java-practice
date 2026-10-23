package pageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ForgotPasswordPage extends BasePage {

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    private final By inputEmail = By.id("email");

    private final By inputNewPassword = By.id("newPassword");

    private final By inputConfirmPassword = By.id("confirmPassword");

    private final By inputResetToken = By.id("resetToken");

    private final By btnResetPassword = By.xpath("//input[@type='submit' and @value='Reset Password']");

    private final By ttlFormResetPassword = By.xpath("//legend");


    private final By messageResetPasswordError = By.xpath("//div[@id='content']//p[@class='message error']");

    private final By messageConfirmPasswordError = By.xpath("//label[@for='confirmPassword' and @class='validation-error']");

    private final By btnSendInstruction = By.xpath("//input[@type='submit' and @value='Send Instructions']");

    public void enterEmail(String email) {
        find(inputEmail).sendKeys(email);
        click(btnSendInstruction);
    }

    public String getForgotResetResetToken() {
        return find(inputResetToken).getAttribute("value");

    }

    public String getTitleFormResetPassword() {

        return find(ttlFormResetPassword).getText();
    }

    public void enterResetPassword(String newpassword, String confirmpassword) {

        find(inputNewPassword).sendKeys(newpassword);
        find(inputConfirmPassword).sendKeys(confirmpassword);

        click(btnResetPassword);
    }

    public boolean isMessageResetPasswordErrorDisplayed() {
        return !driver.findElements(messageResetPasswordError).isEmpty();
    }

    public String getMessageConfirmPasswordError()
    {
        return find(messageConfirmPasswordError).getText();
    }


    public String getMessageResetPasswordError()
    {
        return find(messageResetPasswordError).getText();
    }


}
