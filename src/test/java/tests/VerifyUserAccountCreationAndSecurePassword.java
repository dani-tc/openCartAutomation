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
import patterns.pageobject.AddressBookPage;
import reports.ReportMethods;
import utilities.Utils;

import java.util.Random;

public class VerifyUserAccountCreationAndSecurePassword {
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
        report.setupReport(browserName,"VerifyUserAccountCreationAndSecurePassword.html","Verify user can create account with secure password", "Verify the user can create and account and while creating the password it is not shown.");

    }

    @Test
    @Parameters({"firstName","lastName","email","password"})
    public void UsersCanCreateAccountWithSecurePassword(String firstName, String lastName, String email, String password) throws FindFailed {
        ExtentTest test = report.getTest();
        final int MAX_ATTEMPTS = 20;
        AddressBookPage addressBook = new AddressBookPage(driver);
        boolean passedCreateAccount = false;
        Random rand = new Random();
        int randomNumberEmail = rand.nextInt(1000);
        String randomEmail = randomNumberEmail+email+randomNumberEmail;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                if(!passedCreateAccount){
                    addressBook.clickNavMyAccountDropdown();
                    test.log(Status.INFO, "Click My Account link");
                    addressBook.clickNavRegister();
                    test.log(Status.INFO, "Click Register link");
                    addressBook.fillRegister(firstName,lastName,randomEmail,password);
                    test.log(Status.INFO, "Fill register");
                    Assert.assertEquals(addressBook.getPasswordInput().getText(),"");
                    Utils.takeSnapShot(driver, "src/resources/AccountCreation_and_SecurePassword/1-registerData.png");
                    passedCreateAccount = addressBook.register();
                    test.log(Status.INFO, "Register account");
                    Utils.takeSnapShot(driver, "src/resources/AccountCreation_and_SecurePassword/2-accountCreated.png");
                }
                Assert.assertEquals(addressBook.getAccountCreatedMessage().getText(),"Your Account Has Been Created!");
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

