Feature: Product category filters

  Scenario: Filtering Men's T-Shirts by color and price
    Given the user is on the home page ready to browse categories
    When the user navigates to the Men's T-Shirts category
    Then the collections page should show the initial product count
    When the user applies the first color filter
    Then the filtered product count should differ from the initial count
    When the user clears all filters
    Then the product count should return to the initial count
    When the user sets the price range to "20" and "30"
    Then every visible price should be within that range