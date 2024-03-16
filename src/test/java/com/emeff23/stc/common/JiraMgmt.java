package com.emeff23.stc.common;

import net.rcarz.jiraclient.*;
import net.rcarz.jiraclient.Issue.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JiraMgmt {

    private static final Logger LogThis = LogManager.getLogger(JiraMgmt.class.getName());

    private JiraClient Jira;

    private String project;

    private String JiraUrl;

    public JiraMgmt(String JiraUrl, String username, String password, String project) {

        this.JiraUrl=JiraUrl;

        // create basic authentication object

        BasicCredentials creds = new BasicCredentials(username, password);

        // initialize the Jira client with the url and the credentials

        Jira = new JiraClient(JiraUrl, creds);

        this.project = project;

    }

    public void createJiraIssue(String issueType, String summary, String description, List<String> attachmentPathList) {

        try {

            //Avoid Creating Duplicate Issue

            Issue.SearchResult sr = Jira.searchIssues("summary ~ \""+summary+"\"");

            if(sr.total!=0) {

                LogThis.debug("Same Issue Already Exists on Jira");

                return;

            }

            //Create issue if not exists

            FluentCreate fluentCreate = Jira.createIssue(project, issueType);

            fluentCreate.field(Field.SUMMARY, summary);

            fluentCreate.field(Field.DESCRIPTION, description);

            Issue newIssue = fluentCreate.execute();

            LogThis.debug("********************************************");

            LogThis.debug("New issue created in Jira with ID : " + newIssue.getKey());

            LogThis.debug("New issue URL is : "+JiraUrl+"/browse/"+newIssue.getKey());

            LogThis.debug("*******************************************");

            Issue issue = Jira.getIssue(newIssue.getKey());

            if (!attachmentPathList.isEmpty()) {

                for (String attachmentPath : attachmentPathList) {

                    issue.addAttachment(new File(attachmentPath));

                    LogThis.debug("An attachment is added for issue : " + newIssue.getKey());

                }

            }

        } catch (JiraException e) {

            LogThis.error("JiraException e = " + e.getMessage());

        } catch (Exception ee) {

            LogThis.error("Exception ee = " + ee.getMessage());

        }

    }

}
