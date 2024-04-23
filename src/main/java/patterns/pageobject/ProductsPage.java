package patterns.pageobject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage{
    private WebDriver driver;
    
    @FindBy(css = "button[aria-label='Add to Cart']")
    private WebElement addToCartButtons;

    @FindBy(css = "button[aria-label='Add to Cart']")
    private List<WebElement> random;

    public ProductsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver(){
        return driver;
    }

    public void addProductsToCart(){
        new Actions(driver)
            .scrollToElement(addToCartButtons)
            .click()
            .perform();
    }
    
}
