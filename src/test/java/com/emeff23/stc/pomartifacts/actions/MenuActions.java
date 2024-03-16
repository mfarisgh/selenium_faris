package com.emeff23.stc.pomartifacts.actions;

import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import com.emeff23.stc.pomartifacts.locators.MenuLocators;
import org.openqa.selenium.support.PageFactory;

public class MenuActions {

    MenuLocators menuLocators = null;

    public MenuActions() {

        this.menuLocators = new MenuLocators();

        PageFactory.initElements(WebDriverHelper.getDriver(), menuLocators);
    }

    public void navigateMenuAdmin() {

        menuLocators.mainMenuAdmin.click();
    }
}
