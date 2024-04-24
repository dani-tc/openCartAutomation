package tests;

import org.openqa.selenium.*;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import patterns.DriverManager;
import patterns.pageobject.addressBookPage;
import utilities.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VerifyUsersCanUpdateShippingInformation {
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
    @Parameters({"email","password","firstNameUpdate","lastNameUpdate","address1Update","cityUpdate","postalCodeUpdate","countryValueUpdate","regionValueUpdate"})
    public void UsersCanUpdateShippingInformation(String email, String password,String firstNameUpdate, String lastNameUpdate,String address1Update,String cityUpdate, String postalCodeUpdate,String countryValueUpdate, String regionValueUpdate) throws FindFailed {
        final int MAX_ATTEMPTS = 20;
        boolean passedLogin = false;
        boolean passedUpdate = false;
        int tryRegionSelect =0;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                addressBookPage addressBook = new addressBookPage(driver);
                addressBook.clickNavMyAccountDropdown();
                if(passedLogin){
                    addressBook.clickNavMyAccountOption();
                }else{
                    addressBook.clickNavLogin();
                    passedLogin = addressBook.login(email,password);
                    Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/UpdateShippingInformationResults/1-loginSuccessful.png");
                }
                if(passedUpdate){
                    addressBook.clickAddressBookLink();
                }else{
                    addressBook.clickAddressBookLink();
                    Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/UpdateShippingInformationResults/2-seeAddressBook.png");
                    addressBook.editDefaultAddress();
                    Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/UpdateShippingInformationResults/3-editDefaultAddress.png");
                    while(!passedUpdate && tryRegionSelect<5){
                        passedUpdate = addressBook.updateDefaultAddress(firstNameUpdate,lastNameUpdate,address1Update,cityUpdate,postalCodeUpdate,countryValueUpdate,regionValueUpdate);
                        tryRegionSelect = tryRegionSelect+1;
                    }
                }
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/UpdateShippingInformationResults/4-editedDefaultAddress.png");
                Assert.assertEquals(addressBook.getDefaultAddress().getText(),firstNameUpdate+" "+lastNameUpdate+"\n" +
                        address1Update+"\n" +
                        cityUpdate+", Arauca "+postalCodeUpdate+"\n" +
                        "Colombia");
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

