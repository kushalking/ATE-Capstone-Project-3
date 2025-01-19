@ui
Feature: Flight Booking on BlazeDemo

Scenario: Book a flight with valid passenger details
  Given I am on the BlazeDemo homepage
  When I verify departure and destination dropdowns are present
  And I select "Boston" as departure city
  And I select "London" as destination city
  And I click Find Flights button
  Then I should be navigated to reserve page
  When I select the first available flight
  Then I should be on purchase page
  When I enter passenger details
    | Name       | Simpli Learn      |
    | Address    | 123 Test Street   |
    | City       | Boston            |
    | State      | MA                |
    | ZipCode    | 02108             |
    | CardType   | Visa              |
    | CardNum    | 4111111111111111  |
    | Month      | 12                |
    | Year       | 2025              |
    | NameOnCard | John Doe          |
  And I click Purchase Flight button
  Then I should see confirmation message