package pageObjects.Railway;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;


public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }


    private final By homePageTitle = By.xpath("//*[@id='content']/h1");

    //private final By linkCreateAccount = By.xpath("//*[@id=\"content\"]/div/a");
    private final By linkCreateAccount = By.xpath("//*[@id=\"content\"]/div/a");

    public boolean isHomePageTitleDisplayed() {
        return find(homePageTitle ).getText().trim().equals("Welcome to Safe Railway");
    }

    public void clickCreateAccountLink() {

        click(linkCreateAccount);
    }
}
