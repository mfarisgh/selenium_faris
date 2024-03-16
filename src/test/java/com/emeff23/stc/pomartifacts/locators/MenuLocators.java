package com.emeff23.stc.pomartifacts.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuLocators {

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[1]/aside/nav/div[2]/ul/li[1]/a/span")
    public WebElement mainMenuAdmin;

}
