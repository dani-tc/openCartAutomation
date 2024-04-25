package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CamerasPage extends homePageHeader{

    @FindBy(css="#content .col:nth-child(1) button:nth-child(1)")
    private WebElement addToCartButtonCanon;

    @FindBy(css="#content .col:nth-child(2) button:nth-child(1)")
    private WebElement addToCartButtonNykon;

    public CamerasPage(WebDriver driver){
        super(driver);
    }

    public WebElement getAddToCartButtonCanon(){return addToCartButtonCanon;}
    public WebElement getAddToCartButtonNykon(){return addToCartButtonNykon;}
}
