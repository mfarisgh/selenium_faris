package com.emeff23.stc.pomartifacts.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardLocators {

    @FindBy(xpath = "//span[@class='oxd-topbar-header-breadcrumb']/h6")
    public WebElement homePageUserName;
}
