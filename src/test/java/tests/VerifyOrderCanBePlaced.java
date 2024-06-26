package tests;

import org.openqa.selenium.support.ui.Select;
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

import org.openqa.selenium.*;

import static org.testng.Assert.assertEquals;

public class VerifyOrderCanBePlaced {
    
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");
    ReportMethods report = new ReportMethods();

    @BeforeTest
    @Parameters("browserType")
    public void beforeTest(String browserType) {

        driver = DriverManager.getDriver(BrowserType.valueOf(browserType));

        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyOrderCanBePlace.html","Verify a user can place an order", "Verify the user can add to their cart, complete checkout and place an order");

    }

    @Test
    @Parameters({"firstName","lastName","address","postcode","city","country","state","password"})
    public void OrderCanBePlaced(String firstName, String lastName, String address, String postcode, String city, String country, String state, String password) throws FindFailed {

        final int MAX_ATTEMPTS = 5;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {

                HomePage homePage = new HomePage(driver);
                CheckoutPage checkoutPage = new CheckoutPage(driver);
                
                homePage.addProductToCart();
                Assert.assertTrue(homePage.getAlertMessage().isDisplayed());
                Utils.takeSnapShot(driver, "src/resources/OrderCanBePlacedTest/1-AddProductToCart.png");

                homePage.openCartPage();
                Assert.assertEquals(homePage.getTitle().getText(), "Checkout");
                Utils.takeSnapShot(driver, "src/resources/OrderCanBePlacedTest/2-LoadCheckoutPage.png");
                
                checkoutPage.registerCredentials(firstName, lastName, address, postcode, city, country, state, password);

                Select payment = new Select(checkoutPage.getpaymentMethodDropdown());
                WebElement selectedOption = payment.getFirstSelectedOption();
                String selectedValue = selectedOption.getAttribute("value");
                assertEquals(selectedValue, "cod", "Selected value is not 'cod'");

                Utils.takeSnapShot(driver, "src/resources/OrderCanBePlacedTest/3-CheckoutFilledValidation.png");

                checkoutPage.clickConfirmOrderBtn();
                Assert.assertEquals(checkoutPage.getOrderTitle().getText(), "Your order has been placed!");
                Utils.takeSnapShot(driver, "src/resources/OrderCanBePlacedTest/4-OrderPlacedValidation.png");

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

