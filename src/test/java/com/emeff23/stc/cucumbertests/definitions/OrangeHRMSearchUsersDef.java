package com.emeff23.stc.cucumbertests.definitions;

import com.emeff23.stc.pomartifacts.actions.LoginActions;
import com.emeff23.stc.pomartifacts.actions.MenuActions;
import com.emeff23.stc.pomartifacts.actions.SearchUsersActions;
import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class OrangeHRMSearchUsersDef {

    private static final Logger LogThis = LogManager.getLogger(OrangeHRMSearchUsersDef.class.getName());

    LoginActions objLogin = new LoginActions();

    MenuActions menuActions = new MenuActions();

    SearchUsersActions searchUsersActions = new SearchUsersActions();

    @Given("User goes on HRMLogin page {string} and login using {string} and {string}")
    public void loginTest(String url, String userName, String passWord) {

        try {
            WebDriverHelper.openPage(url);

            objLogin.login(userName, passWord);

            Thread.sleep(Duration.ofSeconds(2).toMillis());

            menuActions.navigateMenuAdmin();
        } catch (Exception e) {
            LogThis.error("Exception e = " + e.getMessage());
            Assert.fail("Exception e = " + e.getMessage());
        }
    }

    @When("User types username {string}")
    public void searchUsername(String userName) {

        searchUsersActions.search(userName);

    }

    @Then("Username {string} is exist")
    public void searchSuccessful(String userName) {

        try {

            String usernameInListXpath = "(//div[contains(text(),'" + userName + "')])[1]";

            WebDriver driver = WebDriverHelper.getDriver();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(usernameInListXpath)));

            WebElement usernameInList = driver.findElement(By.xpath(usernameInListXpath));

            Assert.assertEquals(usernameInList.getText(), userName);

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());
            Assert.fail("Exception e = " + e.getMessage());

        }

    }

    @Then("User should be able to see following prompt message {string}")
    public void searchFailed(String searchErrorMessage) {

        try {

            Thread.sleep(Duration.ofSeconds(3).toMillis());

            // Verify error message
            Assert.assertEquals(searchUsersActions.getSearchErrorMsg(), searchErrorMessage);

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());
            Assert.fail("Exception e = " + e.getMessage());

        }

    }
}
