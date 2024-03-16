package com.emeff23.stc.cucumbertests.definitions;

import com.emeff23.stc.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.emeff23.stc.pomartifacts.common.WebDriverHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WebDriverHooks {

    private static final Logger LogThis = LogManager.getLogger(WebDriverHooks.class.getName());

    @Before
    public static void setUp() {

        WebDriverHelper.setUpDriver();
    }

    @After
    public static void tearDown(Scenario scenario) {

        try {

            LogThis.debug("Scenario : " + scenario.getName() + " (" + scenario.getId() + ")");

            String testResult = scenario.getStatus().toString();

            LogThis.debug(testResult);

            //validate if scenario has failed
            if (scenario.isFailed()) {

                List<String> screenshotPathList = new ArrayList<>();

                Map<byte[], String> screenshotObj = FileMgmt.saveScreenshot(scenario.getName());

                for (Map.Entry<byte[], String> entry : screenshotObj.entrySet()) {

                    scenario.attach(entry.getKey(), "image/png", scenario.getName());

                    screenshotPathList.add(entry.getValue());

                }

                cucumberLogToJira(scenario, screenshotPathList);

            }


        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

        }

        WebDriverHelper.tearDown();
    }

    private static void cucumberLogToJira(Scenario scenario, List<String> attachmentPathList) {

//Provide proper Jira project URL ex: https://browsertack.atlassian.net
//Jira User name ex: browserstack@gmail.com
//API token copy from Jira dashboard ex: lorelimpusm12uijk
//Project key should be, Short name ex: BS

        JiraMgmt JiraServiceProvider = new JiraMgmt(PropFileMgmt.getPropertyValue(CentralVars.PropNameJiraUrl),
                PropFileMgmt.getPropertyValue(CentralVars.PropNameJiraUsername),
                PropFileMgmt.getPropertyValue(CentralVars.PropNameJiraToken),
                PropFileMgmt.getPropertyValue(CentralVars.PropNameJiraProject));

        String issueDescription = "Failure Reason from Automation Testing\n\n" + scenario.getName()
                + " condition does not met. "
                + "\n";

        String issueSummary = scenario.getName()
                + " Failed in Automation Testing";

        LogThis.debug("issueSummary = " + issueSummary);

        LogThis.debug("issueDescription = " + issueDescription);

        JiraServiceProvider.createJiraIssue("Bug",
                UUID.randomUUID() + "-Cucumber-" + issueSummary,
                issueDescription, attachmentPathList);

    }

}
