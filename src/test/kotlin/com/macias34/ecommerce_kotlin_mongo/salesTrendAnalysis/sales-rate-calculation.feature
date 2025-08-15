Feature: Sales Rate Calculation

  Scenario: Calculating sales rate with recent sales
    Given the following sales have been recorded:
      | product   | time_ago        | quantity |
      | iPhone 16 | 10 minutes ago  | 5        |
      | iPhone 16 | 45 minutes ago  | 3        |
      | iPhone 16 | 70 minutes ago  | 2        |
    When the sales rate for the 'iPhone 16' product is calculated over the last hour
    Then the calculated sales rate is 8 sales/hour

  Scenario: Calculating sales rate with a sale on the boundary
    Given the following sales have been recorded:
      | product   | time_ago                | quantity |
      | iPhone 16 | 30 minutes ago          | 6        |
      | iPhone 16 | exactly 60 minutes ago  | 4        |
    When the sales rate for the 'iPhone 16' product is calculated over the last hour
    Then the calculated sales rate is 10 sales/hour

  Scenario: Calculating sales rate with no recent sales
    Given the following sales have been recorded:
      | product   | time_ago         | quantity |
      | iPhone 16 | 90 minutes ago   | 10       |
      | iPhone 16 | 120 minutes ago  | 5        |
    When the sales rate for the 'iPhone 16' product is calculated over the last hour
    Then the calculated sales rate is 0 sales/hour