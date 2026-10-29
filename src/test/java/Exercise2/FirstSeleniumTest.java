package Exercise2;

import common.Utilities.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import common.Constant.Constant;
import org.testng.asserts.SoftAssert;
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

        SoftAssert softAssert = new SoftAssert();

        Log.step("1. Create Driver");
        driver = createDriver(browserName);

        driver.manage().window().maximize();

        Log.step("2. Go to Document");
        driver.get(Constant.DOCUMENT_URL);

        Log.verify(softAssert,"Title name",EXPECTED_TITLE,driver.getTitle());

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