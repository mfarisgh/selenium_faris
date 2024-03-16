Feature: Search Users HRM Application

  Background:
    Given User goes on HRMLogin page "https://opensource-demo.orangehrmlive.com/" and login using "Admin" and "admin123"

  @ValidUsername
  Scenario: Users enter valid username

    When User types username "Alice"
    Then Username "Alice" is exist

  @InvalidUsername
  Scenario Outline: Users enter invalid username

    When User types username "<username>"
    Then User should be able to see following prompt message "<errorMessage>"

    Examples:
      | username   | errorMessage                      |
      | Admins        | No Records Found               |
      | admin$$    | No Records Found               |
      | abc123       | No Records Found                    |