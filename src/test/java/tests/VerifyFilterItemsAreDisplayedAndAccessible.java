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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class VerifyFilterItemsAreDisplayedAndAccessible {

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
        report.setupReport(browserName,"VerifyFilterItemsAreDisplayedAndAccessible.html","Verify that filter options are displayed and accessible", "Verify that filter options are displayed.");


    }

    @Test(priority = 1, description = "Verify can access to the Filters page")
    public void VerifyAccessFilterPage() throws FindFailed {

        final int MAX_ATTEMPTS = 5;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {

                FiltersPage filtersPage = new FiltersPage(driver);
                
                filtersPage.clickDesktopsCategory();
                Assert.assertTrue(filtersPage.getShowAllDesktops().isDisplayed(), "The 'Show All Desktops' button is not displayed");
                Utils.takeSnapShot(driver, "src/resources/VerifyFilterItemsAreDisplayed/1-DesktopsCategoryIsDisplayed.png");

                filtersPage.clickShowAllDesktops();
                Assert.assertTrue(filtersPage.getFiltersMenu().isDisplayed(), "The Filters menu is not displayed");
                Utils.takeSnapShot(driver, "src/resources/VerifyFilterItemsAreDisplayed/2-FiltersMenuIsDisplayed.png");

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

    @Test(priority = 2, description = "Verify the filters by category are displayed")
    public void VerifyFiltersByCategoryDisplayed() throws FindFailed {

        final int MAX_ATTEMPTS = 5;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {

                FiltersPage filtersPage = new FiltersPage(driver);

                for (WebElement category : filtersPage.getFiltersCategories()) {
                    Assert.assertTrue(category.isDisplayed(), "The category named '" + category.getText() + "' is not visible.");
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

    @Test(priority = 3, description = "Verify the filters by category are accessible")
    public void VerifyFiltersByCategoryAccessible() throws FindFailed {

        final int MAX_ATTEMPTS = 5;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                FiltersPage filtersPage = new FiltersPage(driver);
                WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                List<WebElement> categories = filtersPage.getFiltersCategories();

                filtersPage.getFiltersTitle();
                Utils.takeSnapShot(driver, "src/resources/VerifyFilterItemsAreAccessible/0-CategoryIsAccessible.png");
    
                for (int i = 1; i < categories.size(); i++) {

                    categories = filtersPage.getFiltersCategories();
                    WebElement category = categories.get(i);
                    String href = category.getAttribute("href");
    
                    new Actions(driver)
                        .moveToElement(category)
                        .click(category)
                        .perform();

                    try {
                        Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                        }

                    explicitWait.until(ExpectedConditions.attributeContains(By.cssSelector("aside#column-left a[href='"+ href +"']"), "class", "active"));
                    Utils.takeSnapShot(driver, "src/resources/VerifyFilterItemsAreAccessible/" + i +"-CategoryIsAccessible.png");

                    Assert.assertTrue(filtersPage.getFiltersTitle().isDisplayed());

                    driver.navigate().back();
                    explicitWait.until(ExpectedConditions.attributeContains(categories.get(0), "class", "active"));

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