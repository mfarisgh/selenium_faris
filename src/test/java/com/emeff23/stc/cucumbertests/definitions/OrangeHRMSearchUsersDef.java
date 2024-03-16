package com.emeff23.stc.cucumbertests.definitions;

import com.emeff23.stc.pomartifacts.actions.LoginActions;
import com.emeff23.stc.pomartifacts.actions.MenuActions;
import com.emeff23.stc.pomartifacts.actions.SearchUsersActions;
import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class OrangeHRMSearchUsersDef {

    LoginActions objLogin = new LoginActions();

    MenuActions menuActions = new MenuActions();

    SearchUsersActions searchUsersActions = new SearchUsersActions();

    @Given("User goes on HRMLogin page {string} and login using {string} and {string}")
    public void loginTest(String url, String userName, String passWord) {

        WebDriverHelper.openPage(url);

        objLogin.login(userName, passWord);

        menuActions.navigateMenuAdmin();
    }

    @When("User types username {string}")
    public void searchUsername(String userName) {

        searchUsersActions.search(userName);

    }

    @Then("Username {string} is exist")
    public void searchSuccessful(String userName) {

        WebDriver driver = WebDriverHelper.getDriver();
        WebElement usernameInList = driver.findElement(By.xpath("(//div[contains(text(),'" + userName + "')])[1]"));
        Assert.assertEquals(usernameInList.getText(), userName);

    }

    @Then("User should be able to see following prompt message {string}")
    public void searchFailed(String searchErrorMessage) {

        // Verify error message
        Assert.assertEquals(searchUsersActions.getSearchErrorMsg(), searchErrorMessage);

    }
}
