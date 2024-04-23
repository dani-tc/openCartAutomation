package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage{
    private WebDriver driver;
    
    @FindBy(className = "col-12")
    private WebElement productItem;

    @FindBy(id = "button-cart")
    private WebElement addToCart;

    public ProductsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver(){
        return driver;
    }

    public void openProduct(){
        productItem.click();
    }

    public void addProductToCart(){
        addToCart.click();
    }
    
}
