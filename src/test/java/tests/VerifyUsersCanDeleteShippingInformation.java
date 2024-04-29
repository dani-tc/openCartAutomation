package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import patterns.DriverManager;
import patterns.pageobject.AddressBookPage;
import patterns.pageobject.PageFooter;
import reports.ReportMethods;
import utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyUsersCanDeleteShippingInformation {
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");
    ReportMethods report = new ReportMethods();

    @BeforeTest
    public void beforeTest() throws FindFailed{
        driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with your desired browser
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyUsersCanDeleteShippingInformation.html","Verify users can delete shipping information", "Verify that the users can delete shipping information saved in their account");
    }


    @Test
    @Parameters({"email","password"})
    public void UsersCanDeleteShippingInformation(String email, String password) throws FindFailed {
        ExtentTest test = report.getTest();
        final int MAX_ATTEMPTS = 20;
        int numberOfAddresses = 0;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                AddressBookPage addressBook = new AddressBookPage(driver);
                PageFooter pageFooter = new PageFooter(driver);
                pageFooter.clickMyAccountFooter();
                test.log(Status.INFO, "Click My Account link");
                addressBook.login(email,password);
                test.log(Status.INFO, "Login");
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/DeleteShippingInformationResults/1-loginSuccessful.png");
                addressBook.clickAddressBookLink();
                test.log(Status.INFO, "Click Address Book link");
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/DeleteShippingInformationResults/2-seeAddressBook.png");
                numberOfAddresses = addressBook.getAllAddressesLength().size();
                addressBook.deleteAddress();
                test.log(Status.INFO, "Delete Address");
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/DeleteShippingInformationResults/3-deleteAddress.png");
                int newNumberOfAddresses = addressBook.getAllAddressesLength().size();
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/DeleteShippingInformationResults/4-deletedAddress.png");
                Assert.assertEquals(newNumberOfAddresses,(numberOfAddresses-1));
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

