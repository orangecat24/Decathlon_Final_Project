Feature: Update cart quantities

  Scenario: Increasing and decreasing item quantity updates the order total correctly
    Given the cart contains three products for quantity updates
    When the user increases the quantity of the first item
    Then the quantity should increase by 1
    And the order total should increase by exactly one unit price
    When the user decreases the quantity of the first item
    Then the quantity should return to its original value
    And the order total should return to its original value