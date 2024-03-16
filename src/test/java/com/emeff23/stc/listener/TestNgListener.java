package com.emeff23.stc.listener;

import com.emeff23.stc.common.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestNgListener implements ITestListener {

    private static final Logger LogThis = LogManager.getLogger(TestNgListener.class.getName());

    @Override
    public void onTestFailure(ITestResult result) {

        try {

            boolean isLogIssue = result.getMethod().getConstructorOrMethod().getMethod()
                    .getAnnotation(JiraThis.class).isCreateIssue();

            if (isLogIssue) {

                List<String> screenshotPathList = new ArrayList<>();

                Map<byte[], String> screenshotObj = FileMgmt.saveScreenshot(result.getTestName());

                for (Map.Entry<byte[], String> entry : screenshotObj.entrySet()) {

                    screenshotPathList.add(entry.getValue());

                }

//Provide proper Jira project URL ex: https://browsertack.atlassian.net
//Jira User name ex: browserstack@gmail.com
//API token copy from Jira dashboard ex: lorelimpusm12uijk
//Project key should be, Short name ex: BS

                JiraMgmt JiraServiceProvider = new JiraMgmt(PropFileMgmt.getPropertyValue(CentralVars.PropNameJiraUrl),
                        PropFileMgmt.getPropertyValue(CentralVars.PropNameJiraUsername),
                        PropFileMgmt.getPropertyValue(CentralVars.PropNameJiraToken),
                        PropFileMgmt.getPropertyValue(CentralVars.PropNameJiraProject));

                String issueDescription = "Failure Reason from Automation Testing\n\n" + result.getThrowable().getMessage()
                        + "\n";

                issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));

                String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName()
                        + " Failed in Automation Testing";

                LogThis.debug("issueSummary = " + issueSummary);

                LogThis.debug("issueDescription = " + issueDescription);

                JiraServiceProvider.createJiraIssue("Bug",
                        UUID.randomUUID() + "-" + issueSummary,
                        issueDescription, screenshotPathList);

            }

        } catch (Exception e) {

            LogThis.error("Exception e = " + e.getMessage());

        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        // TODO Auto-generated method stub


    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

        // TODO Auto-generated method stub


    }

    @Override
    public void onStart(ITestContext context) {

        // TODO Auto-generated method stub


    }

    @Override
    public void onFinish(ITestContext context) {

        // TODO Auto-generated method stub


    }

    @Override
    public void onTestStart(ITestResult result) {

        // TODO Auto-generated method stub


    }

    @Override
    public void onTestSuccess(ITestResult result) {

        // TODO Auto-generated method stub


    }

}
