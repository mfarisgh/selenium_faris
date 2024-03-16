package com.emeff23.stc.pomartifacts.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchUsersLocators {

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input")
    public WebElement searchUsername;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")
    public WebElement searchBtn;

    @FindBy(xpath = "//p[(text() = 'No Records Found' or . = 'No Records Found')]")
    public WebElement searchErrorMessage;
}
