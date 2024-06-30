package com.emeff23.stc.noncucumbertests;

import com.emeff23.stc.common.CentralVars;
import com.emeff23.stc.common.PropFileMgmt;
import com.emeff23.stc.pomartifacts.actions.LoginActions;
import com.emeff23.stc.pomartifacts.actions.MenuActions;
import com.emeff23.stc.pomartifacts.actions.SearchUsersActions;
import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class OrangeHRMSearchUsersFail {

    private static final Logger LogThis = LogManager.getLogger(OrangeHRMSearchUsers.class.getName());

    LoginActions objLogin;

    MenuActions menuActions;

    SearchUsersActions searchUsersActions;

    @BeforeClass
    public static void testNgSetup() {

        WebDriverHelper.setUpDriver();

    }

    @BeforeMethod
    public void openWebpage() {

        WebDriverHelper.openPage(PropFileMgmt.getPropertyValue(CentralVars.PropNameWebUrl));

    }

    @Test
    public void searchUsersFail() {

        try {

            objLogin = new LoginActions();

            menuActions = new MenuActions();

            searchUsersActions = new SearchUsersActions();

            String userName = "Admin";
            String passWord = "admin123";

            objLogin.login(userName, passWord);

            Thread.sleep(Duration.ofSeconds(2).toMillis());

            menuActions.navigateMenuAdmin();

            String searchUsername = "Alice";

            searchUsersActions.search(searchUsername);

            String searchErrorMessage = "No Records Found";

            Thread.sleep(Duration.ofSeconds(3).toMillis());

            LogThis.debug(searchUsersActions.getSearchErrorMsg());

            Assert.assertEquals(searchUsersActions.getSearchErrorMsg(), searchErrorMessage);

        } catch (Exception e) {

            LogThis.error("Exception e msg = " + e.getMessage());
            Assert.fail("Exception e msg = " + e.getMessage());

        }

    }

    @AfterClass
    public static void testNgTearDown() {

        WebDriverHelper.tearDown();

    }

}
