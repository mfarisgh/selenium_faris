<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">Selenium + TestNG + Cucumber + LambdaTest + Jira</h3>

  <p align="center">
    by M. Faris
    <br />
    <a href="https://github.com/mfarisgh/selenium_faris/issues"><strong>Report Issues »</strong></a>
    <br />
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="about-the-project">About The Project</a>
      <ul>
        <li><a href="built-with">Built With</a></li>
        <li><a href="test-scenarios">Test Scenarios</a></li>
      </ul>
    </li>
    <li>
      <a href="getting-started">Getting Started</a>
      <ul>
        <li><a href="prerequisites">Prerequisites</a></li>
        <li><a href="running-the-automation-tests">Running the Automation Tests</a></li>
        <li><a href="running-the-automation-tests-using-ci">Running the Automation Tests using CI</a></li>
      </ul>
    </li>
    <li><a href="contact">Contact</a></li>
    <li><a href="acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This is a POC Java project for Automation Testing using Selenium to test OrangeHRM website. 
The goal of this project is to demonstrate how an Automation Testing is done using various combination of tools/frameworks. 
Of course this project is not limited to its current conditions. 
You may fork it and customize it further with more tools/frameworks depending on your needs.

<p align="right">(<a href="readme-top">back to top</a>)</p>


### Built With

* Java 17
* Maven 3
* Selenium 4
* Cucumber 7
* TestNG 7
* [Unofficial Jira Client 0.5](https://github.com/bobcarroll/jira-client)

<p align="right">(<a href="readme-top">back to top</a>)</p>


### Test Scenarios

* OrangeHRM Successful Login
* OrangeHRM Failed Login
* OrangeHRM Successful Username Search
* OrangeHRM Failed Username Search

<p align="right">(<a href="readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

The following are the instructions on setting up your project locally.
To get a local copy up and running, follow these simple steps.

### Prerequisites

The following prerequisites that you will need to get started:

1. This repo of course! You may download it as a zip file or "git clone" it to your computer.
2. OpenJDK (I used v17 as a base). [here](https://adoptium.net/temurin/releases/)
3. An IDE (Eclipse or IntelliJ IDEA Ultimate/Community Edition).
4. You're good to go! :)


### Running the Automation Tests

1. Ensure that you have extracted or git-cloned the project folder into your local computer.
2. Open the project folder in your IDE.
3. Look for `local.properties` in `src/test/resources`. Configure it as follows:
   
   ```sh
     location=  # local or lambdatest
     browser=chrome
     browser.mode=  # headful or headless
     lt.username=  # your LambdaTest username. You may leave it blank.
     lt.accesskey=  # your LambdaTest access key. You may leave it blank.
     web.url=https://opensource-demo.orangehrmlive.com/
     jira.enabled=  # true or false
     jira.url=  # your Jira URL. Do not forget the ending slash! You may leave it blank.
     jira.username=  #  your Jira username (email address). You may leave it blank.
     jira.token=  # your Jira user token. You may leave it blank.
     jira.project=  # your Jira project code. e.g. STC. You may leave it blank.
   ```
   
4. Run the tests through the `testng.xml` file.


### Running the Automation Tests using CI

* Coming soon ...

<p align="right">(<a href="readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

M. Faris - [Facebook](https://fb.me/its.me.eff) - mfaris.official@gmail.com

GitHub : [mfarisgh](https://github.com/mfarisgh)

<p align="right">(<a href="readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgements

* [QA Automation Expert](https://qaautomation.expert/2023/10/09/page-object-model-with-selenium-cucumber-and-testng/)
* [selenium.dev](https://www.selenium.dev/documentation/webdriver/troubleshooting/upgrade_to_selenium_4/)
* [Tools QA](https://toolsqa.com/testng/testng-data-provider-excel/)
* [Shreya Bose (BrowserStack)](https://www.browserstack.com/guide/wait-commands-in-selenium-webdriver)
* [Tim Lewis (Stack Overflow)](https://stackoverflow.com/questions/2263929/regarding-application-properties-file-and-environment-variable)
* [Arpan Saini (Stack Overflow)](https://stackoverflow.com/questions/4501215/can-testng-run-multiple-suites)
* [Ganesh Hegde (BrowserStack)](https://www.browserstack.com/guide/how-to-integrate-jira-with-selenium)

<p align="right">(<a href="readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
