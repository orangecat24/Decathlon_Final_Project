Feature: Search and open product

  Scenario: Search for a product and validate product page details
    Given the user is on the home page ready to search
    When the user searches for "backpack"
    Then the search results should be displayed
    When the user opens the first product tile
    Then the product page should load correctly
    When the user goes back and opens the Arpenaz backpack
    And selects an unavailable option
    Then the notify me button should be displayed
    And the sold out button should be displayed
    And the add to cart button should be disabled