package tests;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import patterns.DriverManager;
import patterns.pageobject.CamerasPage;
import patterns.pageobject.ComparisonPage;
import utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyComparisonToolAppropriateAndDisplaysFeatures {
    private WebDriver driver = null;
    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");

    @BeforeTest
    public void beforeTest() throws FindFailed{
        driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with your desired browser
        //Login as admin to unlock functionalities
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatedDate = format.format(today);
        driver.get("https://demo.opencart.com/");
        driver.manage().addCookie(new Cookie("OCSESSID","11c0f931cf"+formatedDate+"ec"));
        driver.manage().addCookie(new Cookie("_ga","GA1.1.2123778129.1713796835"));
        driver.manage().addCookie(new Cookie("_ga_X8G0BRFSDF","GS1.1.1713796835.1.0.1713796835.0.0.0"));
        driver.manage().addCookie(new Cookie("_gcl_au","1.1.534898992.1713796834"));
        driver.manage().addCookie(new Cookie("_gid","GA1.2.438931849.1713796835"));
        driver.manage().addCookie(new Cookie("cf_clearance","zJ9wxfXGd6JiMI3czkXFs4.kzRi6IqvPGPR1BaphLjM-1713852454-1.0.1.1-XKiVE5CVgEaZJ6pwxaPFZvAbzObkzBLWVzgfCCZoPHgWbHPgp6V.HROlod2Rr0jRzg2O5vNoDLVqbRP0JC8Gnw"));
        driver.manage().addCookie(new Cookie("currency","USD"));
    }

    @Test
    public void ComparisonToolIsAppropriateAndDisplayKeyFeatures() throws FindFailed {
        long startTime = System.currentTimeMillis();
        final int MAX_ATTEMPTS = 20;

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                CamerasPage camerasPage = new CamerasPage(driver);
                ComparisonPage comparisonPage = new ComparisonPage(driver);
                camerasPage.goToCamerasCategory();
                camerasPage.comparisonToolForTwoProducts();
                Utils.takeSnapShot(driver, "src/resources/ComparisonToolIsAppropriateAndDisplayKeyFeatures/1-AddTwoProductsToComparisonTable.png");
                camerasPage.clickComparisonToolButton();
                Utils.takeSnapShot(driver, "src/resources/ComparisonToolIsAppropriateAndDisplayKeyFeatures/2-ComparisonTable.png");
                Assert.assertTrue(comparisonPage.isComparisonToolTableDisplayed());
                Assert.assertEquals(comparisonPage.getComparisonToolProductTitlesText(),"Canon EOS 5D Nikon D300 ");
                Assert.assertEquals(comparisonPage.getComparisonToolFeaturesTitlesText(),"Product Image Price Model Brand Availability Summary Weight Dimensions (L x W x H)  ");
                Assert.assertEquals(comparisonPage.getComparisonToolFeaturesContentFirstProductText(),"Canon EOS 5D  $98.00 $122.00 Product 3 Canon In Stock Canon's press material for the EOS 5D states that it 'defines (a) new D-SLR category', while we're not typically too concerned with marketing talk this particular statement is clearly pretty accura.. 0.00kg 0.00cm x 0.00cm x 0.00cm Add to Cart Remove ");
                Assert.assertEquals(comparisonPage.getComparisonToolFeaturesContentSecondProductText(),"Nikon D300  $98.00 Product 4  In Stock Engineered with pro-level features and performance, the 12.3-effective-megapixel D300 combines brand new technologies with advanced features inherited from Nikon's newly announced D3 profes.. 0.00kg 0.00in x 0.00in x 0.00in Add to Cart Remove ");
                long endTime = System.currentTimeMillis();
                double timeTakenSeconds = (endTime - startTime) / 1000.0; // time taken in seconds
                System.out.println("Time taken to execute the test: " + timeTakenSeconds + " seconds");
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

