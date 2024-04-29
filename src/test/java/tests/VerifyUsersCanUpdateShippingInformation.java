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
import patterns.pageobject.AddressBookPage;
import patterns.pageobject.PageFooter;
import reports.ReportMethods;
import utilities.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VerifyUsersCanUpdateShippingInformation {
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");
    ReportMethods report = new ReportMethods();

    @BeforeTest
    public void beforeTest() throws FindFailed{
        driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with your desired browser
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyUsersCanUpdateShippingInformation.html","Verify users can update shipping information", "Verify that the users can update shipping information for future checkouts.");

    }


    @Test
    @Parameters({"email","password","firstNameUpdate","lastNameUpdate","address1Update","cityUpdate","postalCodeUpdate","countryValueUpdate","regionValueUpdate"})
    public void UsersCanUpdateShippingInformation(String email, String password,String firstNameUpdate, String lastNameUpdate,String address1Update,String cityUpdate, String postalCodeUpdate,String countryValueUpdate, String regionValueUpdate) throws FindFailed {
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
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/UpdateShippingInformationResults/1-loginSuccessful.png");
                addressBook.clickAddressBookLink();
                test.log(Status.INFO, "Click Address Book link");
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/UpdateShippingInformationResults/2-seeAddressBook.png");
                addressBook.editDefaultAddress();
                test.log(Status.INFO, "Click edit default address");
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/UpdateShippingInformationResults/3-editDefaultAddress.png");
                addressBook.updateDefaultAddress(firstNameUpdate,lastNameUpdate,address1Update,cityUpdate,postalCodeUpdate,countryValueUpdate,regionValueUpdate);
                test.log(Status.INFO, "Edit address");
                Utils.takeSnapShot(driver, "src/resources/SaveShipping_and_BillingInformationResults/UpdateShippingInformationResults/4-editedDefaultAddress.png");
                Assert.assertEquals(addressBook.getDefaultAddress().getText(),firstNameUpdate+" "+lastNameUpdate+"\n" +
                        address1Update+"\n" +
                        cityUpdate+", Northern Territory "+postalCodeUpdate+"\n" +
                        "Australia");
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

