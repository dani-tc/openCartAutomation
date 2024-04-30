package patterns.pageobject;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.Random;

public class CheckoutPage extends PageHeader {

    //Title field
    @FindBy(css ="#checkout-register")
    private WebElement title;

    //First Name Field
    @FindBy(css ="#input-firstname")
    private WebElement firstNameInput;

    //Last Name Field
    @FindBy(css ="#input-lastname")
    private WebElement lastNameInput;

    //Email Field
    @FindBy(css ="#input-email")
    private WebElement emailInput;

    //Address Field
    @FindBy(css ="#input-payment-address-1")
    private WebElement addressInput;

    //City Field
    @FindBy(css ="#input-payment-city")
    private WebElement cityInput;

    //Postcode Field
    @FindBy(css ="#input-payment-postcode")
    private WebElement postcodeInput;

    //Countries Dropdown
    @FindBy(css ="#input-payment-country")
    private WebElement countriesDrop;

    //State Dropdown
    @FindBy(css ="#input-payment-zone")
    private WebElement stateDrop;

    //Password Field
    @FindBy(css ="#input-password")
    private WebElement passwordInput;

    //Privacy Policy Checkbox
    @FindBy(css ="#input-register-agree")
    private WebElement policyCheckbox;

    //Continue Button
    @FindBy(css ="#button-register")
    private WebElement continueButton;

    //Shipping Method Dropdown
    @FindBy(css = "#input-shipping-method")
    private WebElement shippingMethodDropdown;

    //Payment Method Dropdown
    @FindBy(css = "#input-payment-method")
    private WebElement paymentMethodDropdown;

    //Alert Popup
    @FindBy(css ="div#alert div.alert-success")
    private WebElement alert;

    //Existing Payment Address label
    @FindBy(css ="#input-payment-existing + label")
    private WebElement existingPaymentAddress;

    //Existing Shipping Address label
    @FindBy(css ="#input-shipping-existing + label")
    private WebElement existingShippingAddress;

    //Confirm Order Button
    @FindBy(css ="#button-confirm")
    private WebElement orderButton;

    //Order Page Title
    @FindBy(css ="div#content h1")
    private WebElement ordertitle;

    //Order Page Continue Button
    @FindBy(css ="div.float-end a.btn-primary")
    private WebElement orderPageBtn;

    //Shipping Address Selector
    @FindBy(css ="#shipping-existing #input-shipping-address")
    private WebElement shippingAddressSelector;

    private WebDriver driver;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public WebElement getExistingPaymentAddress() {
        return existingPaymentAddress;
    }

    public WebElement getExistingShippingAddress() {
        return existingShippingAddress;
    }
    public WebElement getpaymentMethodDropdown() { return paymentMethodDropdown; }

    public void registerCredentials(String firstName, String lastName, String address, String postcode, String city, String country, String state, String password) {

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        String randomEmail = generateRandomEmail();
        int MAX_ATTEMPTS = 5;
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
                explicitWait.until(ExpectedConditions.visibilityOf(title));
                
                firstNameInput.sendKeys(firstName);
                lastNameInput.sendKeys(lastName);
                emailInput.sendKeys(randomEmail);
        
                Select countries = new Select(countriesDrop);
                countries.selectByVisibleText(country);
        
                addressInput.sendKeys(address);
                postcodeInput.sendKeys(postcode);
                cityInput.sendKeys(city);
         
                explicitWait.until(ExpectedConditions.textToBePresentInElement(stateDrop, state));
                Select states = new Select(stateDrop);
                states.selectByVisibleText(state);
        
                passwordInput.sendKeys(password);
                
                explicitWait.until(ExpectedConditions.elementToBeClickable(policyCheckbox));
                new Actions (driver)
                    .moveToElement(policyCheckbox)
                    .click(policyCheckbox)
                    .perform();
        
                clickContinueBtn();
        
                explicitWait.until(ExpectedConditions.elementToBeClickable(shippingMethodDropdown));
                Select shipping = new Select(shippingMethodDropdown);
                shipping.selectByValue("flat.flat");
                explicitWait.until(ExpectedConditions.visibilityOf(alert));
        
                selectPaymentMethod();
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                driver.navigate().refresh();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }
            }
        }
        
    }

    private static String generateRandomEmail() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder email = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(index);
            email.append(randomChar);
        }

        email.append("@testing.com");
        return email.toString();
    }

    public void clickContinueBtn() {
        
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(continueButton));
        int MAX_ATTEMPTS = 2;
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", continueButton);
            explicitWait.until(ExpectedConditions.visibilityOf(alert));
            try {
                Thread.sleep(2000);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }
            }
        }

    }

    public void selectPaymentMethod() {
        
        int MAX_ATTEMPTS = 5;
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            explicitWait.until(ExpectedConditions.elementToBeClickable(paymentMethodDropdown));
            Select payment = new Select(paymentMethodDropdown);
            payment.selectByValue("cod");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                driver.navigate().refresh();

                WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement dropdown = explicitWait.until(ExpectedConditions.visibilityOf(shippingAddressSelector));
                Select select = new Select(dropdown);
                select.selectByIndex(1);
                explicitWait.until(ExpectedConditions.visibilityOf(alert));

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }
            }
        }
    }

    public void clickConfirmOrderBtn() {
        
        int MAX_ATTEMPTS = 5;
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
            WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            explicitWait.until(ExpectedConditions.elementToBeClickable(orderButton));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            orderButton.click();
            explicitWait.until(ExpectedConditions.visibilityOf(orderPageBtn));
            assertEquals(ordertitle.getText(), "Your order has been placed!");
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                driver.navigate().refresh();

                selectPaymentMethod();
                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }
            }
        }
    }

}
