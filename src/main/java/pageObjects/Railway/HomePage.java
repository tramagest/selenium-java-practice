package pageObjects.Railway;

import dataObjects.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final By homePageTitle = By.xpath("//*[@id='content']/h1");
    private final By linkCreateAccount = By.xpath("//*[@id=\"content\"]/div/a");

    public boolean isHomePageTitleDisplayed() {
        return find(homePageTitle).getText().trim().equals(Message.HOME_PAGE_TITLE.getText());
    }

    public void clickCreateAccountLink() {
        click(linkCreateAccount);
    }

    public boolean isCreateAccountLinkDisplayed() {
        return find(linkCreateAccount).getText().trim()
                .equals(Message.HOME_CREATE_ACCOUNT_LINK.getText());
    }
}