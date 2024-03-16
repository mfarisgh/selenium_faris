package com.emeff23.stc.pomartifacts.actions;

import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import com.emeff23.stc.pomartifacts.locators.DashboardLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DashboardActions {

    DashboardLocators homePageLocators;

    public DashboardActions() {

        this.homePageLocators = new DashboardLocators();
        PageFactory.initElements(WebDriverHelper.getDriver(),homePageLocators);
    }

    // Get the User name from Home Page
    public String getHomePageText() {
        return homePageLocators.homePageUserName.getText();
    }

}
