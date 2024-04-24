package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends PageHeader{

    @FindBy(css = "#content h1")
    private WebElement shoppingCartTitle;

    @FindBy(css = "#content h2")
    private WebElement shoppingCartSubtitle;

    @FindBy(css = "#shopping-cart")
    private WebElement contentsTable;

    @FindBy(css = "#checkout-total")
    private WebElement totalTable;

    @FindBy(css = "button[data-bs-original-title='Update']")
    private WebElement updateButton;

    @FindBy(css = "button[data-bs-original-title='Remove']")
    private WebElement removeButton;

    @FindBy(css = "a[href='https://demo.opencart.com/index.php?route=product/product&amp;language=en-gb&amp;product_id=49']>img")
    WebElement imageLink;

    @FindBy(css = "#accordion.accordion")
    private WebElement accordion;

    @FindBy(css = "button[data-bs-target='#collapse-shipping']")
    private WebElement estimateShippingButton;

    @FindBy(css = "#input-country")
    private WebElement countryInput;

    @FindBy(css = "#input-zone")
    private WebElement zoneInput;

    @FindBy(css = "#input-postcode")
    private WebElement postCodeInput;

    @FindBy(css = "button[data-bs-target='#collapse-coupon']")
    private WebElement couponCodeButton;

    @FindBy(css = "#input-coupon")
    private WebElement couponInput;

    @FindBy(css = "label[for='input-coupon']")
    private WebElement couponLabel;

    @FindBy(css = "button[data-bs-target='#collapse-voucher']")
    private WebElement giftCertificateButton;

    @FindBy(css = "#input-voucher")
    private WebElement giftInput;

    @FindBy(css = "label[for='input-voucher']")
    private WebElement giftLabel;

    @FindBy(tagName = "footer")
    private WebElement footer;

    @FindBy(tagName = "header")
    private WebElement header;

    @FindBy(css = "#logo")
    private WebElement logo;

    @FindBy(css = "a[href='https://demo.opencart.com/index.php?route=product/product&language=en-gb&product_id=49']")
    private WebElement productImage;

    @FindBy(css = "a[href='https://demo.opencart.com/index.php?route=product/product&amp;language=en-gb&amp;product_id=49']")
    private WebElement productName;

    @FindBy(xpath = "//td[@class='text-start' and contains(text(),'SAM1')]")
    private WebElement productModel;

    @FindBy(css = "input[name='quantity'].form-control")
    private WebElement productQuantity;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public String getCartPageTitleText() {
        return shoppingCartTitle.getText();
    }

    public WebElement getCartPageTitle() {
        return shoppingCartTitle;
    }

    public WebElement getShoppingCartTitle() {
        return shoppingCartTitle;
    }

    public WebElement getShoppingCartSubtitle() {
        return shoppingCartSubtitle;
    }

    public WebElement getContentsTable() {
        return contentsTable;
    }

    public WebElement getTotalTable() {
        return totalTable;
    }

    public WebElement getUpdateButton() {
        return updateButton;
    }

    public WebElement getRemoveButton() {
        return removeButton;
    }

    public WebElement getImageLink() {
        return imageLink;
    }

    public WebElement getAccordion() {
        return accordion;
    }

    public WebElement getEstimateShippingButton() {
        return estimateShippingButton;
    }

    public WebElement getCountryInput() {
        return countryInput;
    }

    public WebElement getZoneInput() {
        return zoneInput;
    }

    public WebElement getPostCodeInput() {
        return postCodeInput;
    }

    public WebElement getCouponCodeButton() {
        return couponCodeButton;
    }

    public WebElement getCouponInput() {
        return couponInput;
    }

    public WebElement getCouponLabel() {
        return couponLabel;
    }

    public WebElement getGiftCertificateButton() {
        return giftCertificateButton;
    }

    public WebElement getGiftInput() {
        return giftInput;
    }

    public WebElement getGiftLabel() {
        return giftLabel;
    }

    public WebElement getFooter() {
        return footer;
    }

    public WebElement getHeader() {
        return header;
    }

    public WebElement getLogo() {
        return logo;
    }

    public WebElement getProductImage() {
        return productImage;
    }

    public WebElement getProductName() {
        return productName;
    }

    public WebElement getProductModel() {
        return productModel;
    }

    public WebElement getProductQuantity() {
        return productQuantity;
    }

    
    
}