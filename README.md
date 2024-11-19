# Mytheresa - QA Technical Challenge

---

## Overview

This repository contains an automation framework that may provide end-to-end testing of a Mytheresa web application.

The framework utilizes:
- **Playwright** for browser and API automation.
- **TestNG** as the testing framework.
- **Page Object Model** to make the code more readable and maintainable.
- **Maven** for managing project dependencies and running tests.
- **Extent Report**  as the reporting tool.

---

## Features

1. **Cross-Browser support**: Default browser is chromium, others can be specified.
2. **Environment selection**: Default environment is production.
3. **Reporting**: As a post execution action, it creates HTML reports.
---

## Getting Started

### Prerequisites

- Java 11+
- Playwright 1.48
- Maven 3.6+
- TestNG 7+

---

### Installation

#### Step-1: Clone the repository
git clone https://github.com/efisek/mytheresa-qa-challenge.git

cd mytheresa-qa-challenge

#### Step-2: Install dependencies
mvn clean install

#### Step-3: Run tests

#If no environment specified
mvn test
#For local environment
mvn test -Denv=local 
#For staging environment
mvn test -Denv=staging
#For browser selection from CLI
mvn test -Dbrowser=firefox or mvn test -Dbrowser=webkit

---

### Key Components:
- The source code is under **main** branch.
- **BaseTest**: Contains setup, teardown logic for Playwright, captures screenshots on failed tests.
- **BasePage**: It works as a foundation of the other pages, including common methods to interact with web elements. 
- **AboutPage**: It is created to check the **consoleError()** method for Test Case-1
- **AccountPage**: It is created for Test Case-3, dynamically fills the login credentials
- **AppwritePage**: It is created for Test Case-4, gets the pull requests
- Reports and screenshots can be found under the **test-output** folder.
- **Jenkinsfile** is created to run tests for local, staging, and production environments, and to archive the **Extent Reports** after the test execution.


---

## License

This project is licensed under the MIT License.

---

## Contributors

Emre Fisek


