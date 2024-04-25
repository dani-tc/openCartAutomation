package patterns.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import patterns.DriverManager;

import java.time.Duration;
import java.util.List;

public class AddressBookPage extends AccountPage {
    //Create new address
    @FindBy(css=".d-inline-block .float-end a")
    private WebElement createNewAddressButton;
    //Address 1
    @FindBy(id="input-address-1")
    private WebElement address1Input;
    //City
    @FindBy(id="input-city")
    private WebElement addressCity;
    //City
    @FindBy(id="input-postcode")
    private WebElement addressPostCode;
    //Country
    @FindBy(css="#input-country")
    private WebElement addressCountry;
    //Country
    @FindBy(css="#input-country option[value='222']")
    private WebElement addressCountryOption;
    //Region
    @FindBy(css="#input-zone")
    private WebElement addressRegion;
    //Region
    @FindBy(css="#input-zone option:nth-of-type(2)")
    private WebElement addressRegionOption;
    //Title
    @FindBy(css="#content h1")
    private WebElement pageTitle;
    //Delete address button
    @FindBy(css="#content tr:nth-of-type(2) a[href*='delete']")
    private WebElement deleteAddressButton;
    //Default address
    @FindBy(css="#address tr:nth-of-type(1) td:nth-of-type(1)")
    private WebElement defaultAddress;
    //Edit address button
    @FindBy(css="#content tr:nth-of-type(1) a:nth-of-type(1)")
    private WebElement editDefaultAddressButton;
    //All addresses
    @FindBy(css="#address .text-start")
    private List<WebElement> allAddresses;
    //Account Created Title
    @FindBy(css="#content h1")
    private WebElement accountCreatedMessage;



    private WebDriver driver;

    // Constructor
    public AddressBookPage(WebDriver driver) {
        super(driver);
        this.driver = DriverManager.getDriver(DriverManager.BrowserType.CHROME); // replace with desired browser (CHROME, EDGE, FIREFOX)
        PageFactory.initElements(driver, this);
    }

    public WebElement getAccountCreatedMessage() {
        return accountCreatedMessage;
    }

    public boolean deleteAddress (){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.elementToBeClickable(deleteAddressButton));
        deleteAddressButton.click();
        try{
            explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            explicitWait.until(ExpectedConditions.visibilityOf(getAddressBookLink()));
        }catch(Exception e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.F5);
            return false;
        }
        return true;
    }

    public WebElement getDefaultAddress() {
        return defaultAddress;
    }

    public void editDefaultAddress (){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.elementToBeClickable(editDefaultAddressButton));
        editDefaultAddressButton.click();
    }

    public List<WebElement> getAllAddressesLength() {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(allAddresses));
        return allAddresses;
    }

    public boolean createNewAddress(String firstName, String lastName, String address1,String city,String postalCode, String countryValue, String regionValue){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOf(createNewAddressButton));
        createNewAddressButton.sendKeys(Keys.ENTER);
        getFirstName().sendKeys(firstName);
        getLastName().sendKeys(lastName);
        address1Input.sendKeys(address1);
        addressCity.sendKeys(city);
        addressPostCode.sendKeys(postalCode);
        addressCountry.click();
        driver.findElement(By.cssSelector("#input-country option[value='"+countryValue+"']")).click();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.elementToBeClickable(addressRegion));
        addressRegion.click();
        try{
            driver.findElement(By.cssSelector("#input-zone option[value='"+regionValue+"']")).click();
            explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("#input-zone option[value='"+regionValue+"']")));
        }catch (Exception e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.F5);
            return false;
        }

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", getContinueButton());
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(getAllAddressesLength()));
        if(pageTitle.getText().equals("Add Address")){
            driver.findElement(By.tagName("body")).sendKeys(Keys.F5);
            return false;
        }
        return true;
    }

    public boolean updateDefaultAddress(String firstNameUpdate, String lastNameUpdate, String address1Update, String cityUpdate, String postalCodeUpdate,String countryValueUpdate, String regionValueUpdate) {
        getFirstName().clear();
        getFirstName().sendKeys(firstNameUpdate);
        getLastName().clear();
        getLastName().sendKeys(lastNameUpdate);
        address1Input.clear();
        address1Input.sendKeys(address1Update);
        addressCity.clear();
        addressCity.sendKeys(cityUpdate);
        addressPostCode.clear();
        addressPostCode.sendKeys(postalCodeUpdate);
        addressCountry.click();
        driver.findElement(By.cssSelector("#input-country option[value='" + countryValueUpdate + "']")).click();
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.elementToBeClickable(addressRegion));
        addressRegion.click();
        try {
            driver.findElement(By.cssSelector("#input-zone option[value='" + regionValueUpdate + "']")).click();
            explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("#input-zone option[value='" + regionValueUpdate + "']")));
        }catch (Exception e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.F5);
            return false;
        }

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", getContinueButton());
        try {
            explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            explicitWait.until(ExpectedConditions.textToBePresentInElement(pageTitle,"Address Book Entries"));
        }catch (Exception e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.F5);
            return false;
        }

        return true;
    }
}

