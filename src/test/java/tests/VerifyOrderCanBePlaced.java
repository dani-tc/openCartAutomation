package tests;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyOrderCanBePlaced {
    
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");
    ReportMethods report = new ReportMethods();

    @BeforeTest
    public void beforeTest(){

        driver = DriverManager.getDriver(BrowserType.EDGE);

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
                Utils.takeSnapShot(driver, "src/resources/OrderCanBePlacedTest/1-AddProductToCart.png");

                homePage.openCartPage();
                Utils.takeSnapShot(driver, "src/resources/OrderCanBePlacedTest/2-LoadCheckoutPage.png");
                
                checkoutPage.registerCredentials(firstName, lastName, address, postcode, city, country, state, password);
                Utils.takeSnapShot(driver, "src/resources/OrderCanBePlacedTest/3-CheckoutFilledValidation.png");

                checkoutPage.clickConfirmOrderBtn();
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

