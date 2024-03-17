package com.emeff23.stc.pomartifacts.actions;

import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import com.emeff23.stc.pomartifacts.locators.SearchUsersLocators;
import org.openqa.selenium.support.PageFactory;

public class SearchUsersActions {

    SearchUsersLocators searchUsersLocators = null;

    public SearchUsersActions() {

        this.searchUsersLocators = new SearchUsersLocators();

        PageFactory.initElements(WebDriverHelper.getDriver(), searchUsersLocators);
    }

    public void search(String searchUsername) {

        searchUsersLocators.searchUsername.sendKeys(searchUsername);

        searchUsersLocators.searchBtn.click();

    }

    public String getSearchErrorMsg() {

        return searchUsersLocators.searchErrorMessage.getText();

    }
}
