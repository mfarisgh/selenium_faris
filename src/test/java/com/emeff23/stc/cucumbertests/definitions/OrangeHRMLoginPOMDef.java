package com.emeff23.stc.cucumbertests.definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.emeff23.stc.pomartifacts.actions.*;
import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import org.testng.Assert;

public class OrangeHRMLoginPOMDef {

    LoginActions objLogin = new LoginActions();
    DashboardActions objHomePage = new DashboardActions();

    @Given("User goes on HRMLogin page {string}")
    public void loginTest(String url) {

        WebDriverHelper.openPage(url);

    }

    @When("User types username as {string} and password as {string}")
    public void goToHomePage(String userName, String passWord) {

        // login to application
        objLogin.login(userName, passWord);

        // go the next page

    }

    @Then("User should be able to login successfully and navigate to new page")
    public void verifyLogin() {

        // Verify home page
        Assert.assertTrue(objHomePage.getHomePageText().contains("Dashboard"));

    }

    @Then("User should be able to see following error message {string}")
    public void verifyErrorMessage(String expectedErrorMessage) {

        // Verify error message
        Assert.assertEquals(objLogin.getErrorMessage(),expectedErrorMessage);

    }

    @Then("User should be able to see a message {string} below Username")
    public void verifyMissingUsernameMessage(String message) {

        Assert.assertEquals(objLogin.getMissingUsernameText(),message);
    }

}
