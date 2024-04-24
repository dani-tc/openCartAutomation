package tests;

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

public class VerifyCheckoutSupportsCashOnDeliveryPayment {
    
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");

    @BeforeTest
    public void beforeTest(){

        driver = DriverManager.getDriver(BrowserType.EDGE);
  
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatedDate = format.format(today);

        driver.get("https://demo.opencart.com/index.php");
        driver.manage().addCookie(new Cookie("OCSESSID","11c0f931cf"+formatedDate+"ec"));
        driver.manage().addCookie(new Cookie("_ga","GA1.1.2123778129.1713796835"));
        driver.manage().addCookie(new Cookie("_ga_X8G0BRFSDF","GS1.1.1713796835.1.0.1713796835.0.0.0"));
        driver.manage().addCookie(new Cookie("_gcl_au","1.1.534898992.1713796834"));
        driver.manage().addCookie(new Cookie("_gid","GA1.2.438931849.1713796835"));
        driver.manage().addCookie(new Cookie("cf_clearance","zJ9wxfXGd6JiMI3czkXFs4.kzRi6IqvPGPR1BaphLjM-1713852454-1.0.1.1-XKiVE5CVgEaZJ6pwxaPFZvAbzObkzBLWVzgfCCZoPHgWbHPgp6V.HROlod2Rr0jRzg2O5vNoDLVqbRP0JC8Gnw"));
        driver.manage().addCookie(new Cookie("currency","USD"));

    }

    @Test
    @Parameters({"firstName","lastName","address","postcode","city","state","password"})
    public void CashOnDeliveryPaymentAllowed(String firstName, String lastName, String address, String postcode, String city, String state, String password) throws FindFailed {

        final int MAX_ATTEMPTS = 20;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {

                HomePage homePage = new HomePage(driver);
                CheckoutPage checkoutPage = new CheckoutPage(driver);
                
                homePage.addProductToCart();
                Utils.takeSnapShot(driver, "src/resources/checkoutCashOnValidationTest/1-addProductToCart.png");

                homePage.openCartPage();
                Utils.takeSnapShot(driver, "src/resources/checkoutCashOnValidationTest/2-loadCheckoutPage.png");
                
                checkoutPage.registerCredentials(firstName, lastName, address, postcode, city, state, password);
                Utils.takeSnapShot(driver, "src/resources/checkoutCashOnValidationTest/3-cashOnValidation.png");

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Attempt " + (attempt + 1) + " failed. Retrying...");
                screen.wait(image, 10);
                screen.click(image);
                if (attempt == MAX_ATTEMPTS - 1) {
                    throw e;
                }
        
            }

        }
    }

    @AfterTest
    public void afterTest(){

        DriverManager.quitDriver();

    }
}

