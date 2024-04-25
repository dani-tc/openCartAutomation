package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MonitorsPage extends homePageHeader {

    @FindBy(css="#product-list .col:nth-child(2) button:nth-child(1)")
    private WebElement addToCartButton;

    @FindBy(css="#product-list .col:nth-child(2) h4")
    private WebElement productTitle;

    // Constructor
    public MonitorsPage(WebDriver driver){
        super(driver);
    }

    public WebElement getAddToCartButton(){return addToCartButton;}
    public WebElement getProductTitle(){return productTitle;}
}
