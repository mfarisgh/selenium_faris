package com.emeff23.stc.noncucumbertests;

import com.emeff23.stc.common.CentralVars;
import com.emeff23.stc.common.PropFileMgmt;
import com.emeff23.stc.pomartifacts.actions.DashboardActions;
import com.emeff23.stc.pomartifacts.actions.LoginActions;
import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import org.testng.Assert;
import org.testng.annotations.*;

public class OrangeHRMLogin {

    LoginActions objLogin;
    DashboardActions objHomePage;

    @BeforeClass
    public static void testNgSetup() {

        WebDriverHelper.setUpDriver();

    }

    @BeforeMethod
    public void openWebpage() {

        WebDriverHelper.openPage(PropFileMgmt.getPropertyValue(CentralVars.PropNameWebUrl));

    }

    @Test
    public void webpageLogin() {

        objLogin = new LoginActions();

        objHomePage = new DashboardActions();

        String userName = "Admin";
        String passWord = "admin123";

        objLogin.login(userName, passWord);

        Assert.assertTrue(objHomePage.getHomePageText().contains("Time at Work"));

    }

    @AfterClass
    public static void testNgTearDown() {

        WebDriverHelper.tearDown();

    }

}
