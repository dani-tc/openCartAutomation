package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import patterns.DriverManager;
import patterns.pageobject.*;
import reports.ReportMethods;
import utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyUsersCanSaveShippingInformation {
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");
    ReportMethods report = new ReportMethods();

    @BeforeTest
    @Parameters("browserType")
    public void beforeTest(String browserType) throws FindFailed{
        driver = DriverManager.getDriver(DriverManager.BrowserType.valueOf(browserType)); // replace with your desired browser
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyUsersCanSaveShippingInformation.html","Verify users can save shipping information", "Verify that the users can save shipping information for future checkouts.");
    }


    @Test
    @Parameters({"email","password","firstName","lastName","address1","city","postalCode","countryValue","regionValue"})
    public void UsersCanSaveShippingInformation(String email, String password,String firstName, String lastName,String address1,String city, String postalCode,String countryValue, String regionValue) throws FindFailed {
        ExtentTest test = report.getTest();
        final int MAX_ATTEMPTS = 20;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                AddressBookPage addressBook = new AddressBookPage(driver);
                PageFooter pageFooter = new PageFooter(driver);
                pageFooter.clickMyAccountFooter();
                test.log(Status.INFO, "Click My Account link");
                addressBook.login(email,password);
                test.log(Status.INFO, "Login");
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/SaveShippingInformationResults/1-loginSuccessful.png");
                addressBook.clickAddressBookLink();
                test.log(Status.INFO, "Click Address Book link");
                int numberOfAddress = addressBook.getAllAddressesLength().size();
                addressBook.clickCreateNewAddress();
                test.log(Status.INFO, "Click Create New Address Button");
                addressBook.createNewAddress(firstName, lastName, address1, city, postalCode, countryValue, regionValue);
                test.log(Status.INFO, "Create New Address");
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/SaveShippingInformationResults/2-createNewAddress.png");
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

