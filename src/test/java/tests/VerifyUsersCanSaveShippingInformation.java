package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import patterns.DriverManager;
import patterns.pageobject.*;
import utilities.Utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class VerifyUsersCanSaveShippingInformation {
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");

    @BeforeTest
    public void beforeTest() throws FindFailed{
        driver = DriverManager.getDriver(DriverManager.BrowserType.CHROME); // replace with your desired browser
        //Login as admin to unlock functionalities
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
    @Parameters({"email","password","firstName","lastName","address1","city","postalCode","countryValue","regionValue"})
    public void UsersCanSaveShippingInformation(String email, String password,String firstName, String lastName,String address1,String city, String postalCode,String countryValue, String regionValue) throws FindFailed {
        final int MAX_ATTEMPTS = 20;
        boolean passedLogin = false;
        boolean passedCreateAddress = false;
        int trySelectRegion = 0;
        int numberOfAddress = 0;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                AddressBookPage addressBook = new AddressBookPage(driver);
                addressBook.clickNavMyAccountDropdown();
                if(passedLogin){
                    addressBook.clickNavMyAccountOption();
                }else{
                    addressBook.clickNavLogin();
                    passedLogin = addressBook.login(email,password);
                    Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/SaveShippingInformationResults/1-loginSuccessful.png");
                }
                if(passedCreateAddress) {
                    addressBook.clickAddressBookLink();
                }else{
                    addressBook.clickAddressBookLink();
                    numberOfAddress = addressBook.getAllAddressesLength().size();
                    while(!passedCreateAddress && trySelectRegion<5){
                        passedCreateAddress = addressBook.createNewAddress(firstName, lastName, address1, city, postalCode, countryValue, regionValue);
                        trySelectRegion = trySelectRegion + 1;
                    }
                    Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/SaveShippingInformationResults/2-createNewAddress.png");
                }
                int newNumberOfAddress = addressBook.getAllAddressesLength().size();
                Assert.assertEquals(newNumberOfAddress,(numberOfAddress+1));
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
    @AfterTest
    public void afterTest(){
        //driver.quit();
        DriverManager.quitDriver();
    }
}

