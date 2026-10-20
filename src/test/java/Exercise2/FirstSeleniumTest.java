package Exercise2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import common.Constant.Constant;


public class FirstSeleniumTest  {

    private static final String EXPECTED_TITLE = "The Selenium Browser Automation Project | Selenium";

    public static void main(String[] args) {

        verifyTitle(new ChromeDriver(), "Chrome");


        verifyTitle(new EdgeDriver(), "Edge");
    }

    private static void verifyTitle(WebDriver driver, String browserName) {
        driver.manage().window().maximize();
        driver.get(Constant.DOCUMENT_URL);

        String actual = driver.getTitle();

        if (actual.equals(EXPECTED_TITLE)) {
            System.out.println("PASS [" + browserName + "]: Correct Title  - " + actual);
        } else {
            System.out.println("FAIL [" + browserName + "]: Error Title ");
            System.out.println("Expected: " + EXPECTED_TITLE);
            System.out.println("Actual:   " + actual);
        }

        driver.quit();
    }
}