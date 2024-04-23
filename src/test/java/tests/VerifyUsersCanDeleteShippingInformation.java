package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

import java.util.List;

public class VerifyUsersCanDeleteShippingInformation {
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");

    @BeforeTest
    public void beforeTest() throws FindFailed{
        driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with your desired browser
        //Login as admin to unlock functionalities
        driver.get("https://demo.opencart.com/admin/");
        WebElement inputName = driver.findElement(By.id("input-username"));
        inputName.sendKeys("demo");
        WebElement inputPassword = driver.findElement(By.id("input-password"));
        inputPassword.sendKeys("demo");
        WebElement login = driver.findElement(By.cssSelector(".btn"));
        login.click();
    }


    @Test
    @Parameters({"email","password"})
    public void UsersCanSaveShippingInformation(String email, String password) throws FindFailed {
        final int MAX_ATTEMPTS = 20;
        boolean passedLogin = false;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                driver.get("https://demo.opencart.com/");
                addressBookPage addressBook = new addressBookPage(driver);
                addressBook.clickNavMyAccountDropdown();
                if(passedLogin){
                    addressBook.clickNavMyAccountOption();
                }else{
                    addressBook.clickNavLogin();
                    passedLogin = addressBook.login(email,password);
                }
                addressBook.clickAddressBookLink();
                int numberOfAddresses = addressBook.getAllAddressesLength().size();
                System.out.println(numberOfAddresses);
                addressBook.deleteAddress();
                addressBook.clickAddressBookLink();
                int newNumberOfAddresses = addressBook.getAllAddressesLength().size();
                System.out.println(newNumberOfAddresses);
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
    @AfterTest
    public void afterTest(){
        //driver.quit();
        DriverManager.quitDriver();
    }
}

