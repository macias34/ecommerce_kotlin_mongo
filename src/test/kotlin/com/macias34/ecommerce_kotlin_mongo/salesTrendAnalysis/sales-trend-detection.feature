Feature: Sales Trend Detection
  Determines a product's sales trend by comparing its current sales rate to a base sales rate.
  It publishes significant sales activity events when the change in trend factor exceeds defined thresholds.

  Background:
    Given the system defines trend statuses based on the trend factor:
      | status          | trend_factor_range |
      | Underperforming | 0 to 1.0           |
      | Stable          | > 1.0 to 2.0       |
      | Trending        | > 2.0              |

  Scenario: Positive trend detection - entity, domain service
    Given the product 'iPhone 16' has a base sales rate of 10 sales/hour
    When the current sales rate for the 'iPhone 16' product becomes 25 sales/hour
    Then a significant sales activity is detected for the 'iPhone 16' product, indicating a trend factor of 2.5 and a 'Trending' status

  Scenario: Negative trend detection - entity, domain service
    Given the product 'iPhone 16' has a base sales rate of 20 sales/hour
    When the current sales rate for the 'iPhone 16' product drops to 8 sales/hour
    Then a significant sales activity is detected for the 'iPhone 16' product, indicating a trend factor of 0.4 and an 'Underperforming' status

  Scenario: Insignificant sales activity change is ignored - domain service
    Given the system is configured with a detection threshold of 0.2
    And the product 'iPhone 16' has a base sales rate of 10 sales/hour
    When the current sales rate for the 'iPhone 16' product changes to 11 sales/hour
    Then no significant sales activity is detected for the 'iPhone 16' product

#    todo scenario where latest sales rate is there
  Scenario: Sales activity change crossing the threshold is detected - domain service
    Given the system is configured with a detection threshold of 0.2
    And the product 'iPhone 16' has a base sales rate of 10 sales/hour
    When the current sales rate for the 'iPhone 16' product changes to 12 sales/hour
    Then a significant sales activity is detected for the 'iPhone 16' product, indicating a trend factor of 1.2 and a 'Stable' status

  Scenario: Repeated detection on a growing trend - domain service
    Given the product 'iPhone 16' has a base sales rate of 10 sales/hour
    And the system's last known current sales rate for this product was 25 sales/hour
    When the current sales rate for the 'iPhone 16' product increases further to 30 sales/hour
    Then a significant sales activity is detected for the 'iPhone 16' product, indicating a trend factor of 3.0 and a 'Trending' status

  Scenario: Detecting return to stability from a trending state - domain service
    Given the product 'iPhone 16' has a base sales rate of 10 sales/hour
    And the system's last known current sales rate for this product was 25 sales/hour
    When the current sales rate for the 'iPhone 16' product drops to 15 sales/hour
    Then a significant sales activity is detected for the 'iPhone 16' product, indicating a trend factor of 1.5 and a 'Stable' status

  Scenario: Detecting return to stability from an underperforming state - domain service
    Given the product 'iPhone 16' has a base sales rate of 20 sales/hour
    And the system's last known current sales rate for this product was 8 sales/hour
    When the current sales rate for the 'iPhone 16' product increases to 24 sales/hour
    Then a significant sales activity is detected for the 'iPhone 16' product, indicating a trend factor of 1.2 and a 'Stable' status

  Scenario: First sale for a product with no previous sales history - domain service
    Given the product 'iPhone 16' has a base sales rate of 0 sales/hour
    When the current sales rate for the 'iPhone 16' product becomes 10 sales/hour
    Then a significant sales activity is detected for the 'iPhone 16' product, indicating a trend factor of 2.1 and a 'Trending' status

  Scenario: Sales stop for a previously active product - entity, domain service
    Given the product 'iPhone 16' has a base sales rate of 20 sales/hour
    When the current sales rate for the 'iPhone 16' product becomes 0 sales/hour
    Then a significant sales activity is detected for the 'iPhone 16' product, indicating a trend factor of 0.0 and an 'Underperforming' status

  Scenario: A dormant product remains dormant - domain service
    Given the product 'iPhone 16' has a base sales rate of 0 sales/hour
    When the current sales rate for the 'iPhone 16' product is 0 sales/hour
    Then no significant sales activity is detected for the 'iPhone 16' product