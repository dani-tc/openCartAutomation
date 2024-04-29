package patterns.pageobject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FiltersPage extends PageHeader {

    @FindBy(css="aside#column-left")
    private WebElement filtersMenu;

    @FindBy(css="aside#column-left a.list-group-item")
    private List<WebElement> filtersCategories;

    @FindBy(css="div#content h2")
    private WebElement title;

    private WebDriver driver;

    // Constructor
    public FiltersPage(WebDriver driver) {

        super(driver);
        this.driver = driver;

    }

    public void clickDesktopsCategory(){

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOf(getDesktopsCategory()));
        new Actions (driver)
            .moveToElement(getDesktopsCategory())
            .click(getDesktopsCategory())
            .perform();

    }

    public void clickShowAllDesktops(){

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOf(getShowAllDesktops()));
        new Actions (driver)
            .moveToElement(getShowAllDesktops())
            .click(getShowAllDesktops())
            .perform();

    }

    public WebElement getFiltersMenu(){

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOf(filtersMenu));
        return filtersMenu;
    }

    public WebElement getFiltersTitle(){

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOf(title));
        return title;
        
    }

    public List<WebElement> getFiltersCategories(){return filtersCategories;}

}
