package com.emeff23.stc.pomartifacts.actions;

import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import com.emeff23.stc.pomartifacts.locators.LoginLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginActions {

    LoginLocators loginPageLocators;

    public LoginActions() {

        this.loginPageLocators = new LoginLocators();

        PageFactory.initElements(WebDriverHelper.getDriver(),loginPageLocators);
    }

    // Get the error message when username is blank
    public String getMissingUsernameText() {
        return loginPageLocators.missingUsernameErrorMessage.getText();
    }

    // Get the Error Message
    public String getErrorMessage() {
        return loginPageLocators.errorMessage.getText();
    }

    public void login(String strUserName, String strPassword) {

        // Fill user name
        loginPageLocators.userName.sendKeys(strUserName);

        // Fill password
        loginPageLocators.password.sendKeys(strPassword);

        // Click Login button
        loginPageLocators.login.click();

    }
}
