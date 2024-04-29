package tests;

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

import java.text.SimpleDateFormat;
import java.util.*;

public class VerifyFilteringByCategory {

    private WebDriver driver = null;

    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");

    @BeforeTest
    public void beforeTest(){

        driver = DriverManager.getDriver(BrowserType.EDGE);
  
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

    @AfterTest
    public void afterTest(){

        DriverManager.quitDriver();

    }

}