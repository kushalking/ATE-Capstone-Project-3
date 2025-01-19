package stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.datatable.DataTable;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import utils.WebDriverManager;
import pages.*;
import java.util.Map;

public class FlightBookingSteps {
    private WebDriver driver;
    private HomePage homePage;
    private ReservePage reservePage;
    private PurchasePage purchasePage;
    private ConfirmationPage confirmationPage;
    
    @Before
    public void setup() {
        driver = WebDriverManager.getDriver();
    }
    
    @After
    public void tearDown() {
        WebDriverManager.quitDriver();
    }
    
    @Given("I am on the BlazeDemo homepage")
    public void navigateToHomepage() {
        driver.get("https://blazedemo.com/");
        homePage = new HomePage(driver);
    }
    
    @When("I verify departure and destination dropdowns are present")
    public void verifyDropdowns() {
        Assert.assertTrue(homePage.areDropdownsPresent(), "Dropdowns are not present on homepage");
    }
    
    @When("I select {string} as departure city")
    public void selectDepartureCity(String city) {
        homePage.selectDepartureCity(city);
    }
    
    @When("I select {string} as destination city")
    public void selectDestinationCity(String city) {
        homePage.selectDestinationCity(city);
    }
    
    @When("I click Find Flights button")
    public void clickFindFlights() {
        homePage.clickFindFlights();
    }
    
    @Then("I should be navigated to reserve page")
    public void verifyReservePage() {
        reservePage = new ReservePage(driver);
        Assert.assertTrue(reservePage.isOnReservePage(), "Not on reserve page");
    }
    
    @When("I select the first available flight")
    public void selectFirstFlight() {
        reservePage.selectFirstFlight();
    }
    
    @Then("I should be on purchase page")
    public void verifyPurchasePage() {
        purchasePage = new PurchasePage(driver);
        Assert.assertTrue(purchasePage.getPageTitle().contains("Purchase"), "Not on purchase page");
    }
    
    @When("I enter passenger details")
    public void enterPassengerDetails(DataTable dataTable) {
        Map<String, String> passengerDetails = dataTable.asMap(String.class, String.class);
        purchasePage.fillPassengerDetails(passengerDetails);
    }
    
    @When("I click Purchase Flight button")
    public void clickPurchaseFlight() {
        purchasePage.clickPurchaseFlight();
    }
    
    @Then("I should see confirmation message")
    public void verifyConfirmationMessage() {
        confirmationPage = new ConfirmationPage(driver);
        Assert.assertTrue(confirmationPage.isConfirmationMessageDisplayed(), 
                        "Confirmation message is not displayed");
    }
}