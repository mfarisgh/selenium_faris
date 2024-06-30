package com.emeff23.stc.noncucumbertests;

import com.emeff23.stc.common.CentralVars;
import com.emeff23.stc.common.JiraThis;
import com.emeff23.stc.common.PropFileMgmt;
import com.emeff23.stc.common.TestNgExcelMgmt;
import com.emeff23.stc.pomartifacts.actions.LoginActions;
import com.emeff23.stc.pomartifacts.actions.MenuActions;
import com.emeff23.stc.pomartifacts.actions.SearchUsersActions;
import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Arrays;

public class OrangeHRMSearchUsers {

    private static final Logger LogThis = LogManager.getLogger(OrangeHRMSearchUsers.class.getName());

    private String testCaseName;

    private int testCaseRow;

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

    @JiraThis(isCreateIssue = true)
    @Test(dataProvider = "SearchUsersTestData")
    public void searchUsers(String searchUsername) {

        try {

            objLogin = new LoginActions();

            menuActions = new MenuActions();

            searchUsersActions = new SearchUsersActions();

            String userName = "Admin";
            String passWord = "admin123";

            objLogin.login(userName, passWord);

            Thread.sleep(Duration.ofSeconds(2).toMillis());

            menuActions.navigateMenuAdmin();

            //String searchUsername = "Alice";

            searchUsersActions.search(searchUsername);

            String usernameInListXpath = "(//div[contains(text(),'" + searchUsername + "')])[1]";

            WebDriver driver = WebDriverHelper.getDriver();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(usernameInListXpath)));

            WebElement usernameInList = driver.findElement(By.xpath(usernameInListXpath));

            Assert.assertEquals(usernameInList.getText(), searchUsername);

        } catch (Exception e) {

            LogThis.error("Exception e msg = " + e.getMessage());
            Assert.fail("Exception e msg = " + e.getMessage());

        }

    }

    @DataProvider
    public Object[][] SearchUsersTestData() throws Exception {

        // Setting up the Test Data Excel file

        TestNgExcelMgmt
                .setExcelFile("excel/td/TD-OrangeHRMSearchUsers.xlsx", "Sheet1");

        testCaseName = this.toString();

        LogThis.info("testCaseName = " + testCaseName);

        // From above method we get long test case name including package and class name etc.

        // The below method will refine your test case name, exactly the name use have used

        testCaseName = TestNgExcelMgmt.getTestCaseName(this.toString());

        LogThis.info("refined testCaseName = " + testCaseName);

        // Fetching the Test Case row number from the Test Data Sheet

        // Getting the Test Case name to get the TestCase row from the Test Data Excel sheet

        testCaseRow = TestNgExcelMgmt.getRowContains(testCaseName, 0);

        LogThis.info("testCaseRow = " + testCaseRow);

        Object[][] testObjArray = TestNgExcelMgmt
                .getTableArray("excel/td/TD-OrangeHRMSearchUsers.xlsx",
                        "Sheet1", testCaseRow);

        LogThis.debug("testObjArray = " + Arrays.deepToString(testObjArray));

        return (testObjArray);

    }

    @AfterClass
    public static void testNgTearDown() {

        WebDriverHelper.tearDown();

    }

}
