package testcases;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.testng.asserts.SoftAssert;


import common.Constant.Constant;
import org.testng.annotations.Test;
import pageObjects.Railway.RegisterPage;
import pageObjects.Railway.BasePage;
import pageObjects.Railway.HomePage;
import pageObjects.Tempmail.TempMailPage;

public class ResetPasswordTest  extends BaseTest{

    SoftAssert softAssert = new SoftAssert();

    TempMailPage tempMailPage = new TempMailPage(driver);

}
