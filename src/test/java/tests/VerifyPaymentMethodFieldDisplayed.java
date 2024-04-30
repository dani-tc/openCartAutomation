package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import reports.ReportMethods;
import utilities.Utils;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import patterns.pageobject.*;
import patterns.*;
import patterns.DriverManager.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class VerifyPaymentMethodFieldDisplayed {
    
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");
    ReportMethods report = new ReportMethods();
    @BeforeTest
    public void beforeTest(){

        driver = DriverManager.getDriver(BrowserType.EDGE);
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyPaymentMethodFieldDisplayed.html","Verify payment method field is displayed", "Verify the user can see the payment method field displayed");

    }

    @Test
    public void PaymentFieldDisplayed() throws FindFailed {

        final int MAX_ATTEMPTS = 5;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {

                HomePage homePage = new HomePage(driver);
                CheckoutPage checkoutPage = new CheckoutPage(driver);
                
                homePage.addProductToCart();
                Assert.assertTrue(homePage.getAlertMessage().isDisplayed());
                Utils.takeSnapShot(driver, "src/resources/PaymentMethodFieldDisplayed/1-AddProductToCart.png");

                homePage.openCartPage();
                Assert.assertTrue(checkoutPage.getpaymentMethodDropdown().isDisplayed(), "The paymentMethodDropdown element is not displayed");
                Utils.takeSnapShot(driver, "src/resources/PaymentMethodFieldDisplayed/2-PaymentFieldDisplayed.png");

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                driver.navigate().refresh();
                try {
                screen.wait(image, 10);
                screen.click(image);
                } catch (Exception e1) {
                    System.out.println("Attempt " + (attempt + 1) + " failed. Retrying...");
                }
                
                if (attempt == MAX_ATTEMPTS - 1) {
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
        // Writing everything to report
        report.writeReport();
        DriverManager.quitDriver();

    }

}
