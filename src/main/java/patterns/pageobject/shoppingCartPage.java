package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class shoppingCartPage extends homePageHeader{
    //Shopping Cart page title
    @FindBy(css="#content h1")
    private WebElement shoppingCartTitle;

    //Shopping Cart link
    @FindBy(css="a[title=\"Shopping Cart\"]")
    private WebElement shoppingCartLink;

    //Name of product displayed on shopping cart
    @FindBy(css=".img-thumbnail")
    private WebElement productName;

    // Constructor


    public shoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public String getCartPageTitleText (){return shoppingCartTitle.getText();}
    public WebElement getCartPageTitle (){return shoppingCartTitle;}
    public WebElement getShoppingCartLink(){return shoppingCartLink;}
    public WebElement getProductName(){return productName;}
}