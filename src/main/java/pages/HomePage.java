package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private WebDriver driver;
    
    @FindBy(name = "fromPort")
    private WebElement fromDropdown;
    
    @FindBy(name = "toPort")
    private WebElement toDropdown;
    
    @FindBy(xpath = "//input[@value='Find Flights']")
    private WebElement findFlightsButton;
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public boolean areDropdownsPresent() {
        return fromDropdown.isDisplayed() && toDropdown.isDisplayed();
    }
    
    public void selectDepartureCity(String city) {
        new Select(fromDropdown).selectByValue(city);
    }
    
    public void selectDestinationCity(String city) {
        new Select(toDropdown).selectByValue(city);
    }
    
    public void clickFindFlights() {
        findFlightsButton.click();
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
}