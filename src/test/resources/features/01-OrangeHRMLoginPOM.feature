Feature: Login to HRM Application

  Background:
    Given User goes on HRMLogin page "https://opensource-demo.orangehrmlive.com/"

  @ValidCredentials
  Scenario: Login using valid credentials

    When User types username as "Admin" and password as "admin123"
    Then User should be able to login successfully and navigate to new page

  @InvalidCredentials
  Scenario Outline: Login using invalid credentials

    When User types username as "<username>" and password as "<password>"
    Then User should be able to see following error message "<errorMessage>"

    Examples:
      | username   | password  | errorMessage                      |
      | Admin        | admin12$$ | Invalid credentials               |
      | admin$$    | admin123  | Invalid credentials               |
      | abc123       | xyz$$     | Invalid credentials                    |

  @MissingUsername
  Scenario: Login using blank username

    When User types username as " " and password as "admin123"
    Then User should be able to see a message "Required" below Username