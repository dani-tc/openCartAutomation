package patterns.pageobject;
//import patterns.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public abstract class homePageHeader {
    private WebDriver driver;

    //NavBar MyAccount dropdown
    @FindBy(css=".float-end ul > li:nth-of-type(2) a.dropdown-toggle")
    private WebElement navMyAccountDropdown;

    //NavBar Register
    @FindBy(css=".float-end ul > li:nth-of-type(2) ul > li:nth-of-type(1) a")
    private WebElement navRegister;

    //NavBar Login
    @FindBy(css=".float-end ul > li:nth-of-type(2) ul > li:nth-of-type(2) a")
    private WebElement navLogin;

    //NavBar Shopping Cart button
    @FindBy(css=".float-end ul > li:nth-of-type(4) a")
    private WebElement navShoppingCart;

    //NavBar Checkout button
    @FindBy(css=".float-end ul > li:nth-of-type(5) a")
    private WebElement navCheckout;

    //Homepage logo button
    @FindBy(css="#logo a")
    private WebElement homeLogoButton;

    //Search bar
    @FindBy(name="search")
    private WebElement searchBar;

    //Search bar button
    @FindBy(css="#search button")
    private WebElement searchBarButton;

    //Cart Dropdown button
    @FindBy(css="#header-cart .btn-block")
    private WebElement cartDropdownButton;

    //List for remove product buttons in the dropdown cart
    @FindBy(css="ul.dropdown-menu-right .btn-danger")
    private List<WebElement> cartRemoveButtonsList;

    //Desktop category
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(1)")
    private WebElement desktopsCategory;

    //PC subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(1) ul > li:nth-of-type(1) a")
    private WebElement pcSubcategory;

    //Mac subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(1) ul > li:nth-of-type(2) a")
    private WebElement macSubcategory;

    //Show all desktops subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(1) .dropdown-inner + a")
    private WebElement showAllDesktops;

    //Laptops and notebooks category
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(2)")
    private WebElement laptopsCategory;

    //Mac laptop subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(2) ul > li:nth-of-type(1) a")
    private WebElement macsLaptopSubcategory;

    //Windows laptop subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(2) ul > li:nth-of-type(2) a")
    private WebElement windowsLaptopSubcategory;

    //Show all laptops subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(2) .dropdown-inner + a")
    private WebElement showAllLaptops;

    //Components category
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(3)")
    private WebElement componentsCategory;

    //Mice and trackballs subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(3) ul > li:nth-of-type(1) a")
    private WebElement miceSubcategory;

    //Monitors subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(3) ul > li:nth-of-type(2) a")
    private WebElement monitorsSubcategory;

    //Printers subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(3) ul > li:nth-of-type(3) a")
    private WebElement printersSubcategory;

    //Web cameras subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(3) ul > li:nth-of-type(5) a")
    private WebElement webCamerasSubcategory;

    //Scanners subcategory
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(3) ul > li:nth-of-type(4) a")
    private WebElement scannersSubcategory;

    //Show all Components category
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(3) .dropdown-inner + a")
    private WebElement showAllComponents;

    //Tablets
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(4) a")
    private WebElement tabletsCategory;

    //Software
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(5) a")
    private WebElement softwareCategory;

    //Phones category
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(6) a")
    private WebElement phonesCategory;

    //Cameras category
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(7) a")
    private WebElement camerasCategory;

    //MP3 category
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(8)")
    private WebElement mp3Category;

    //MP3 subcategories
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(8) ul li a")
    private List<WebElement> mp3subcategoriesList;

    //MP3 show all
    @FindBy(css="#narbar-menu ul.navbar-nav > li:nth-of-type(8) .dropdown-inner + a")
    private WebElement showAllMp3;

    // Constructor
    public homePageHeader(WebDriver driver){
        this.driver = driver;
        //this.driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with desired browser (CHROME, EDGE, FIREFOX)
        PageFactory.initElements(driver, this);
    }

    public void clickNavRegister(){
        navMyAccountDropdown.click();
        navRegister.click();
    }
    public void clickNavLogin(){
        navMyAccountDropdown.click();
        navLogin.click();
    }
    public void clickNavShoppingCart(){
        navShoppingCart.click();
    }

    public WebElement getNavShoppingCart() {
        return navShoppingCart;
    }

    public void clickNavCheckout(){
        navCheckout.click();
    }

    public void clickHomeLogoButton(){
        homeLogoButton.click();
    }

    public void sendKeysSearchBar(String keys){
        searchBar.sendKeys(keys);
    }

    public void clickSearchBarButton(){
        searchBarButton.click();
    }
    public void clickCartDropdownButton(){
        cartDropdownButton.click();
    }

    public List<WebElement> getCartRemoveButtonsList() {
        clickCartDropdownButton();
        return cartRemoveButtonsList;
    }

    public void goToPcSubcategory(){
        desktopsCategory.click();
        pcSubcategory.click();
    }

    public void goToMacSubcategory(){
        desktopsCategory.click();
        macSubcategory.click();
    }

    public void goToAllDesktops(){
        showAllDesktops.click();
    }

    public void goToMacsLaptopsSubcategory(){
        laptopsCategory.click();
        macsLaptopSubcategory.click();
    }
    public void goToWindowsLaptopsSubcategory(){
        laptopsCategory.click();
        windowsLaptopSubcategory.click();
    }

    public void goToAllLaptops(){
        showAllLaptops.click();
    }

    public void goToMiceSubcategory(){
        componentsCategory.click();
        miceSubcategory.click();
    }

    public void goToMonitorsSubcategory(){
        componentsCategory.click();
        monitorsSubcategory.click();
    }

    public void goToPrintersSubcategory(){
        componentsCategory.click();
        printersSubcategory.click();
    }
    public void goToScannersSubcategory(){
        componentsCategory.click();
        scannersSubcategory.click();
    }
    public void goToWebCamerasSubcategory(){
        componentsCategory.click();
        webCamerasSubcategory.click();
    }

    public void goToAllComponents(){
        showAllComponents.click();
    }

    public void goToTabletsCategory(){
        tabletsCategory.click();
    }
    public void goToSoftwareCategory(){
        softwareCategory.click();
    }
    public void goToPhonesCategory(){
        phonesCategory.click();
    }
    public void goToCamerasCategory(){
        camerasCategory.click();
    }

    public List<WebElement> getMp3subcategoriesList() {
        mp3Category.click();
        return mp3subcategoriesList;
    }

    public void goToAllMP3s(){
        showAllMp3.click();
    }

}