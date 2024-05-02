package tests;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.*;
import patterns.DriverManager;
import patterns.pageobject.CamerasPage;
import patterns.pageobject.ComparisonPage;
import reports.ReportMethods;
import utilities.Utils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestResult;


public class VerifyComparisonToolAppropriateAndDisplaysFeatures {
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
        report.setupReport(browserName,"VerifyComparisonToolAppropriateAndDisplaysFeatures.html","Verify comparison tool table is appropriate", "The comparison tool side-by-side table should be appropriate and displays key specific features");
    }

    @Test
    public void ComparisonToolIsAppropriateAndDisplayKeyFeatures() throws FindFailed {
        ExtentTest test = report.getTest();
        final int MAX_ATTEMPTS = 5;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                CamerasPage camerasPage = new CamerasPage(driver);
                ComparisonPage comparisonPage = new ComparisonPage(driver);
                camerasPage.goToCamerasCategory();
                test.log(Status.INFO, "Go to Cameras Category");
                camerasPage.comparisonToolForTwoProducts();
                test.log(Status.INFO, "Select two products for comparison");
                Utils.takeSnapShot(driver, "src/resources/ComparisonToolIsAppropriateAndDisplayKeyFeatures/1-AddTwoProductsToComparisonTable.png");
                camerasPage.clickComparisonToolButton();
                test.log(Status.INFO, "See Comparison Table");
                Utils.takeSnapShot(driver, "src/resources/ComparisonToolIsAppropriateAndDisplayKeyFeatures/2-ComparisonTable.png");
                Assert.assertTrue(comparisonPage.isComparisonToolTableDisplayed());
                Assert.assertEquals(comparisonPage.getComparisonToolProductTitlesText(),"Canon EOS 5D Nikon D300 ");
                Assert.assertEquals(comparisonPage.getComparisonToolFeaturesTitlesText(),"Product Image Price Model Brand Availability Summary Weight Dimensions (L x W x H)  ");
                Assert.assertEquals(comparisonPage.getComparisonToolFeaturesContentFirstProductText(),"Canon EOS 5D  $98.00 $122.00 Product 3 Canon In Stock Canon's press material for the EOS 5D states that it 'defines (a) new D-SLR category', while we're not typically too concerned with marketing talk this particular statement is clearly pretty accura.. 0.00kg 0.00cm x 0.00cm x 0.00cm Add to Cart Remove ");
                Assert.assertEquals(comparisonPage.getComparisonToolFeaturesContentSecondProductText(),"Nikon D300  $98.00 Product 4  In Stock Engineered with pro-level features and performance, the 12.3-effective-megapixel D300 combines brand new technologies with advanced features inherited from Nikon's newly announced D3 profes.. 0.00kg 0.00in x 0.00in x 0.00in Add to Cart Remove ");
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
        DriverManager.quitDriver();
        // Writing everything to report
        report.writeReport();
    }
}

