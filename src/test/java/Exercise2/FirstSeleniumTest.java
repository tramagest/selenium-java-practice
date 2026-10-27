package Exercise2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import common.Constant.Constant;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testcases.BaseTest;


public class FirstSeleniumTest extends BaseTest {

    private static final String EXPECTED_TITLE = "The Selenium Browser Automation Project | Selenium";

    @DataProvider(name = "browsers")
    public Object[][] browsers() {
        return new Object[][]{
                // tcName, browserName
                {"Exercise2_Chrome", "Chrome"},
                {"Exercise2_Edge", "Edge"},
                {"Exercise2_Firefox", "Firefox"}
        };
    }


    @Override
    @BeforeMethod
    public void setUp() {
    }

    @Test(dataProvider = "browsers")
    public void verifyTitle(String tcName, String browserName) {

        driver = createDriver(browserName);

        driver.manage().window().maximize();
        driver.get(Constant.DOCUMENT_URL);

        Assert.assertEquals(driver.getTitle(), EXPECTED_TITLE, "Wrong title on " + browserName);
    }

    private WebDriver createDriver(String browserName) {
        switch (browserName) {
            case "Edge":
                return new EdgeDriver();
            case "Firefox":
                return new FirefoxDriver();
            default:
                return new ChromeDriver();
        }
    }
}