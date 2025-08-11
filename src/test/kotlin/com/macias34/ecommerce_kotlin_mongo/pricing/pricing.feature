Feature: Product Price Calculation
  To ensure fair and predictable pricing, the system calculates the final price of a product
  using a two-phase model. First, all cumulative Cumulative Policies are applied to the base price
  to get a List Price. Second, the single best Exclusive Policy is applied to the List Price
  to determine the Final Price.
  Applicability is abstract, however for easier understanding we will onlly use vendor as an applicability
  condition.

  Scenario: Calculating the price with no active policies
    Given a product has a base price of $100.00
    And no Cumulative Policies or Exclusive Policies are applicable
    When the price is requested
    Then the final price is $100.00

  Scenario: A single Cumulative Policy is applied
    Given a product has a base price of $100.00
    And a Cumulative Policy exists that adds 15%, which is applicable if vendor is 'Allegro'
    When the price is requested with a context that makes the Cumulative Policy applicable
    Then the final price is $115.00

  Scenario: Multiple Cumulative Policies are cumulative
    Given a product has a base price of $100.00
    And a Cumulative Policy exists that adds 10%
    And another Cumulative Policy exists that adds a flat amount of $20.00
    When the price is requested with a context that makes both Cumulative Policies applicable
    Then the final price is $130.00

  Scenario: A single Exclusive Policy (discount) is applied
    Given a product has a base price of $200.00
    And an Exclusive Policy exists that subtracts 25%
    When the price is requested with a context that makes the Exclusive Policy applicable
    Then the final price is $150.00

  Scenario: The lowest price option is chosen from multiple Exclusive Policies
    Given a product has a base price of $100.00
    And an Exclusive Policy exists that subtracts 20% ("Weekend Sale")
    And another Exclusive Policy exists that subtracts a flat amount of $25.00 ("Flash Coupon")
    When the price is requested with a context that makes both Exclusive Policies applicable
    Then the final price is $75.00

  Scenario: The best Exclusive Policy is applied after all Cumulative Policies
    Given a product has a base price of $100.00
    And a Cumulative Policy exists that adds 10%
    And an Exclusive Policy exists that subtracts 20%
    And another Exclusive Policy exists that subtracts a flat amount of $25.00
    When the price is requested with a context that makes all policies applicable
    Then the final price is $85.00

  Scenario: The final price cannot be negative
    Given a product has a base price of $10.00
    And an Exclusive Policy exists that subtracts a flat amount of $15.00
    When the price is requested with a context that makes the Exclusive Policy applicable
    Then the final price is $0.00