package patterns.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import patterns.DriverManager;

import java.time.Duration;

public class AccountPage extends PageHeader {
    //First Name
    @FindBy(id="input-firstname")
    private WebElement FirstName;
    //Last Name
    @FindBy(id="input-lastname")
    private WebElement LastName;
    //Email Input
    @FindBy(id="input-email")
    private WebElement emailInput;
    //Password Input
    @FindBy(id="input-password")
    private WebElement passwordInput;
    //Agree checkbox
    @FindBy(css="input[type='checkbox']")
    private WebElement agreeCheckbox;
    //Continue button
    @FindBy(css=".float-end button")
    private WebElement continueButton;
    //Login button
    @FindBy(css=".mb-3 + button")
    private WebElement loginButton;
    //Manage address book
    @FindBy(css="#content > ul:nth-of-type(1) > li:nth-of-type(3) > a")
    private WebElement addressBookLink;

    //Retun to home
    @FindBy(css="#account-account ul li.breadcrumb-item:nth-of-type(1) a")
    private WebElement breadcrumbReturnToHome;

    private WebDriver driver;

    // Constructor
    public AccountPage(WebDriver driver) {
        super(driver);
        this.driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with desired browser (CHROME, EDGE, FIREFOX)
        PageFactory.initElements(driver, this);
    }

    public WebElement getFirstName() {
        return FirstName;
    }

    public WebElement getLastName() {
        return LastName;
    }

    public WebElement getContinueButton() {
        return continueButton;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }



    public WebElement getAddressBookLink() {
        return addressBookLink;
    }
    public void clickAddressBookLink(){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOf(getAddressBookLink()));
        getAddressBookLink().click();
    }
    public boolean login (String email, String password){
        boolean passedLogin = false;
        while(!passedLogin) {
            try {
                WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                explicitWait.until(ExpectedConditions.visibilityOf(emailInput));
                emailInput.sendKeys(email);
                passwordInput.sendKeys(password);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", loginButton);
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("location.reload()");
                return passedLogin;
            }
            passedLogin = true;
        }
        return passedLogin;
    }

    public void fillRegister (String firstName, String lastName, String email, String password){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOf(getFirstName()));
        getFirstName().sendKeys(firstName);
        getLastName().sendKeys(lastName);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        new Actions(driver)
                .moveToElement(agreeCheckbox)
                .perform();
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", agreeCheckbox);
    }

    public boolean register (){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", getContinueButton());
        return true;
    }
    public void returnToHome (){
        breadcrumbReturnToHome.click();
    }
}
