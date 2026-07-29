Feature: Verify sorting results

  Scenario: Sorting Men's Pants by price high-to-low and low-to-high
    Given the user is on the Men's Pants collection page
    When the user sorts by price high to low
    Then the products should be sorted in descending order
    When the user sorts by price low to high
    Then the products should be sorted in ascending order