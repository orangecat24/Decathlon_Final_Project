Feature: Empty cart verification

  Scenario: Removing all items from the cart shows the empty cart message
    Given the cart contains three products for removal
    When the user removes the first item from the cart
    Then the cart row count should decrease by 1
    When the user removes all remaining items from the cart
    Then the empty cart message should be displayed 