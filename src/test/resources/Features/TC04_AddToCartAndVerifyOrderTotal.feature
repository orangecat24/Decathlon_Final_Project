Feature: Add to cart and verify order total

  Scenario: Adding three products to cart produces a correct order total
    Given the user navigates to the Men's Down Jackets collection page
    When the user adds three products to the cart
    And the user opens the cart
    Then the order total should match the sum of item prices