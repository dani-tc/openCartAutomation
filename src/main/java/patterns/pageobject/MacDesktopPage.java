package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MacDesktopPage extends PageHeader{

    @FindBy(css="#product-list img")
    private WebElement macDesktopImg;

    public MacDesktopPage(WebDriver driver){
        super(driver);
    }

    public WebElement getMacDesktopImg(){return macDesktopImg;}
}
