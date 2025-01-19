package pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PurchasePage {
    private WebDriver driver;

    @FindBy(id = "inputName")
    private WebElement nameInput;
    
    @FindBy(id = "address")
    private WebElement addressInput;
    
    @FindBy(id = "city")
    private WebElement cityInput;
    
    @FindBy(id = "state")
    private WebElement stateInput;
    
    @FindBy(id = "zipCode")
    private WebElement zipCodeInput;
    
    @FindBy(id = "cardType")
    private WebElement cardTypeDropdown;
    
    @FindBy(id = "creditCardNumber")
    private WebElement cardNumberInput;
    
    @FindBy(id = "creditCardMonth")
    private WebElement cardMonthInput;
    
    @FindBy(id = "creditCardYear")
    private WebElement cardYearInput;
    
    @FindBy(id = "nameOnCard")
    private WebElement nameOnCardInput;
    
    @FindBy(xpath = "//input[@value='Purchase Flight']")
    private WebElement purchaseButton;
    
    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void fillPassengerDetails(Map<String, String> details) {
        nameInput.sendKeys(details.get("Name"));
        addressInput.sendKeys(details.get("Address"));
        cityInput.sendKeys(details.get("City"));
        stateInput.sendKeys(details.get("State"));
        zipCodeInput.sendKeys(details.get("ZipCode"));
        new Select(cardTypeDropdown).selectByVisibleText(details.get("CardType"));
        cardNumberInput.sendKeys(details.get("CardNum"));
        cardMonthInput.sendKeys(details.get("Month"));
        cardYearInput.sendKeys(details.get("Year"));
        nameOnCardInput.sendKeys(details.get("NameOnCard"));
    }
    
    public void clickPurchaseFlight() {
        purchaseButton.click();
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
}