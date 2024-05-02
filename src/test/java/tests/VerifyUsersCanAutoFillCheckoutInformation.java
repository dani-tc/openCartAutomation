package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import patterns.DriverManager;
import patterns.pageobject.AccountPage;
import patterns.pageobject.CheckoutPage;
import patterns.pageobject.HomePage;
import reports.ReportMethods;
import utilities.Utils;

public class VerifyUsersCanAutoFillCheckoutInformation {
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");
    ReportMethods report = new ReportMethods();

    @BeforeTest
    public void beforeTest() throws FindFailed{
        driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with your desired browser
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyUsersCanAutoFillCheckoutInformation.html","Verify users can auto-fill checkout information", "Verify user can auto-fill checkout fields with relevant information");


    }


    @Test
    @Parameters({"email","password"})
    public void UsersCanSaveShippingInformation(String email, String password) throws FindFailed {
        ExtentTest test = report.getTest();
        final int MAX_ATTEMPTS = 20;
        boolean passedLogin = false;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                AccountPage accountPage = new AccountPage(driver);
                HomePage homePage = new HomePage(driver);
                CheckoutPage checkoutPage = new CheckoutPage(driver);
                accountPage.clickNavMyAccountDropdown();
                test.log(Status.INFO, "Click My Account link");
                if(passedLogin){
                    accountPage.clickNavMyAccountOption();
                    test.log(Status.INFO, "Click My Account link");
                }else{
                    accountPage.clickNavLogin();
                    test.log(Status.INFO, "Click Login link");
                    passedLogin = accountPage.login(email,password);
                    test.log(Status.INFO, "Login");
                    Utils.takeSnapShot(driver, "src/resources/AutoFill_in_CheckoutResults/AutoFill_InformationResults/1-loginSuccessful.png");
                }
                accountPage.returnToHome();
                test.log(Status.INFO, "Return to home");
                homePage.addProductToCart();
                test.log(Status.INFO, "Add product");
                Utils.takeSnapShot(driver, "src/resources/AutoFill_in_CheckoutResults/AutoFill_InformationResults/2-addProductToCart.png");
                homePage.openCartPage();
                test.log(Status.INFO, "Open Cart and checkout");
                Utils.takeSnapShot(driver, "src/resources/AutoFill_in_CheckoutResults/AutoFill_InformationResults/3-loadCheckoutPage.png");
                Assert.assertEquals(checkoutPage.getExistingPaymentAddress().getText(),"I want to use an existing address");
                Assert.assertEquals(checkoutPage.getExistingShippingAddress().getText(),"I want to use an existing address");

                break;  // if successful, break the loop
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Attempt " + (attempt + 1) + " failed. Retrying...");
                screen.wait(image, 5);
                screen.click(image);
                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }
            }
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        report.afterMethodReport(result);
    }

    @AfterTest
    public void afterTest(){
        report.writeReport();
        DriverManager.quitDriver();
    }
}

