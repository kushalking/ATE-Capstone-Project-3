package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class FlightReservationApiTest {
    private String flightNumber;
    private String airline;
    private String price;
    
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://blazedemo.com";
        // Enable REST Assured logging
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    
    @Test(priority = 1)
    public void testSearchFlights() {
        // First POST request to search flights
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("fromPort", "Paris");
        searchParams.put("toPort", "Buenos Aires");
        
        Response searchResponse = given()
            .formParams(searchParams)
            .when()
            .post("/reserve.php")
            .then()
            .statusCode(200)
            .extract()
            .response();
            
        // Print full HTML response for debugging
        System.out.println("Search Response HTML:");
        System.out.println(searchResponse.getBody().asString());
            
        // Parse the search results with corrected selectors
        Document searchDoc = Jsoup.parse(searchResponse.getBody().asString());
        
        // Debug print the table content
        System.out.println("Table content:");
        Elements tableRows = searchDoc.select("table.table tbody tr");
        for (int i = 0; i < tableRows.size(); i++) {
            System.out.println("Row " + (i+1) + ": " + tableRows.get(i).text());
        }
        
        // Get first row with corrected indices
        Elements firstRow = searchDoc.select("table.table tbody tr:first-child td");
        if (!firstRow.isEmpty()) {
            // Assuming columns are: Choose Flight | Flight Number | Airline | Depart | Arrive | Price
            flightNumber = firstRow.get(1).text().trim();
            airline = firstRow.get(2).text().trim();
            price = firstRow.get(5).text().trim().replace("$", "").trim();
            
            System.out.println("Parsed values:");
            System.out.println("Flight Number: [" + flightNumber + "]");
            System.out.println("Airline: [" + airline + "]");
            System.out.println("Price: [" + price + "]");
            
            // Verify parsed values
            assertNotNull(flightNumber, "Flight number should not be null");
            assertNotNull(airline, "Airline should not be null");
            assertNotNull(price, "Price should not be null");
        } else {
            throw new AssertionError("No flight rows found in the table");
        }
    }
    
//    @Test(priority = 2, dependsOnMethods = "testSearchFlights")
//    public void testPurchaseFlight() {
//        Map<String, String> purchaseParams = new HashMap<>();
//        purchaseParams.put("flight", flightNumber);
//        purchaseParams.put("price", price);
//        purchaseParams.put("airline", airline);
//        purchaseParams.put("fromPort", "Paris");
//        purchaseParams.put("toPort", "Buenos Aires");
//        purchaseParams.put("inputName", "John Doe");
//        purchaseParams.put("address", "123 Main St");
//        purchaseParams.put("city", "Boston");
//        purchaseParams.put("state", "MA");
//        purchaseParams.put("zipCode", "02108");
//        purchaseParams.put("cardType", "Visa");
//        purchaseParams.put("creditCardNumber", "4111111111111111");
//        purchaseParams.put("creditCardMonth", "12");
//        purchaseParams.put("creditCardYear", "2025");
//        purchaseParams.put("nameOnCard", "John Doe");
//
//        System.out.println("Purchase Parameters:");
//        purchaseParams.forEach((key, value) -> System.out.println(key + ": " + value));
//
//        Response response = given()
//            .formParams(purchaseParams)
//            .when()
//            .post("/purchase.php")
//            .then()
//            .statusCode(200)
//            .extract()
//            .response();
//
//        System.out.println("Purchase Response HTML:");
//        System.out.println(response.getBody().asString());
//
//        Document doc = Jsoup.parse(response.getBody().asString());
//
//        // Validate confirmation message
//        String confirmationMessage = doc.select("h1").text();
//        assertEquals(confirmationMessage, "Thank you for your purchase today!", "Confirmation message mismatch!");
//
//        // Additional validation (optional)
//        String reservedFlight = doc.select("p:contains(Flight Number)").text();
//        assertTrue(reservedFlight.contains(flightNumber), "Reserved flight does not match selected flight!");
//    }
}