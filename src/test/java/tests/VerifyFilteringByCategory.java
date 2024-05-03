package tests;

import org.testng.ITestResult;
import reports.ReportMethods;
import utilities.Utils;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import patterns.pageobject.*;
import patterns.*;
import patterns.DriverManager.*;

import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

public class VerifyFilteringByCategory {

    private WebDriver driver = null;

    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");
    ReportMethods report = new ReportMethods();

    @BeforeTest
    @Parameters("browserType")
    public void beforeTest(String browserType) {
        
        driver = DriverManager.getDriver(BrowserType.valueOf(browserType));

        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyFilteringByCategory.html","Verify filtering by category is available", "Verify that the users have the option to choose filtering options");


    }

    @Test
    @Parameters({ "category1", "category2", "category3", "category4", "category5" })
    public void FilterByCategoryDisplayed(String category1, String category2, String category3, String category4, String category5) throws FindFailed {

        final int MAX_ATTEMPTS = 5;
        List<String> categories = Arrays.asList(category1, category2, category3, category4, category5);
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {

                SearchPage searchPage = new SearchPage(driver);
                
                searchPage.openSearchPage();
                Assert.assertEquals(searchPage.getTitle().getText(), "Search");
                Utils.takeSnapShot(driver, "src/resources/VerifyFilteringByCategory/1-OpenSearchPage.png");

                searchPage.clickFilterCategory();
                Utils.takeSnapShot(driver, "src/resources/VerifyFilteringByCategory/2-ClickFilterCategory.png");

                //Close the selector dropdown
                searchPage.clickFilterCategory();

                int index = 3;
                Select categorySelected = new Select(searchPage.getFilterSelect());
                for (String category : categories) {
                    searchPage.selectDesktopsCategory(category);
                    WebElement selectedOption = categorySelected.getFirstSelectedOption();
                    String selectedText = selectedOption.getText();

                    Assert.assertEquals(selectedText, category, "Selected category is not '"+ category +"'");
                    Utils.takeSnapShot(driver, "src/resources/VerifyFilteringByCategory/" + index + "-Select" + category + "Category.png");
                    
                    index++;
                }

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                driver.navigate().refresh();
                try {
                screen.wait(image, 10);
                screen.click(image);
                } catch (Exception e1) {
                    System.out.println("Attempt " + (attempt + 1) + " failed. Retrying...");
                }
                
                if (attempt == MAX_ATTEMPTS - 1) {
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
        // Writing everything to report
        report.writeReport();
        DriverManager.quitDriver();
    }

}
