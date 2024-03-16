package com.emeff23.stc.noncucumbertests;

import com.emeff23.stc.common.CentralVars;
import com.emeff23.stc.common.PropFileMgmt;
import com.emeff23.stc.pomartifacts.actions.DashboardActions;
import com.emeff23.stc.pomartifacts.actions.LoginActions;
import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrangeHRMLoginFail {

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
    public void webpageLoginInvalid() {

        objLogin = new LoginActions();

        objHomePage = new DashboardActions();

        String userName = "Admin1";
        String passWord = "admin1234";

        String expectedErrorMessage = "Invalid credentials";

        objLogin.login(userName, passWord);

        Assert.assertEquals(objLogin.getErrorMessage(),expectedErrorMessage);

    }

    @AfterClass
    public static void testNgTearDown() {

        WebDriverHelper.tearDown();

    }

}
