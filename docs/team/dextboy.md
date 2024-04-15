# Tan Dexter - Project Portfolio Page

## Overview

**Future Academic Planner (FAP)** is a robust academic management software designed to streamline the academic journey 
for NUS Computer Engineering (CEG) students. Optimized for those who can type fast, FAP gets your module planning done 
faster than traditional GUI applications.

## Code Contributed

View my contributions to the codebase in this [RepoSense link](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=dextboy&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other).

## New Features

### Implemented Core Classes

- **`FAP.java`**:
  - Developed the main driver class which handles the flow of application execution from start to end.
  - Integrated all separate components of the software to work cohesively.

- **`Storage.java`**:
  - Engineered the storage handling which includes saving and loading user data from disk.
  - Ensured data integrity and implemented robust exception handling to prevent data corruption.

## Enhancements Implemented

### Exception Handling

- **FAP Class**:
  - Implemented comprehensive exception handling within the FAP class to manage runtime errors effectively.

- **Storage Class**:
  - Introduced `StorageException` to handle specific I/O errors, enhancing the robustness of the storage operations.
  - Optimized error handling in the Storage class to gracefully manage exceptions and maintain system stability.

### Testing

- **JUnit Tests**:
  - Developed JUnit tests for `FAP.java` to verify the integration and execution flows within the application.
  - Constructed JUnit tests for `Storage.java` focusing on file handling, data integrity, and exception scenarios.

- **Text-Ui-Test**:
  - Modified text-ui-tests for `FAP.java` to continuously integrate command processing validation and user 
  interaction functionalities.

## Developer Guide Contributions

### Documentation

- **Architecture Design**:
  - Documented the overall architecture of the application, detailing the foundational design decisions and 
  interactions between various components.

- **Implementation Details**:
  - Elaborated on the implementation of `FAP.java`:
    - Explained the command processing mechanism and its interaction with other system components.
  - Detailed the functionality of `Storage.java`:
    - Described the data saving and loading processes, including how data integrity is maintained during 
    these operations.

### Diagrams

- **Class Diagrams**:
  - Created class diagrams for `FAP.java` and `Storage.java` that outline the structural relationships and dependencies.

- **Sequence Diagrams**:
  - Developed sequence diagrams for `Storage.java` illustrating the process flows for saving and loading data, 
  which help in understanding the detailed operations and method calls.

## User Guide Contributions

### Documentation

- **Command Descriptions**:
  - Wrote detailed descriptions and provided examples for the `add` command, enhancing user comprehension and usability.

## Contributions to Team-based Tasks

- **Project Infrastructure Setup**:
  - Set up and configured the GitHub team organization and repository, which included defining branch rules, setting 
  up directories, and integrating continuous integration tools.

- **Issue Tracker Maintenance**:
  - Regularly maintained the issue tracker by creating, updating, and linking issues to specific PRs, which 
  streamlined the development process and enhanced team productivity.

- **Release Management**:
  - Managed the release process for v1.0 and v2.0 by creating release notes, tagging releases, and ensuring 
  that the project adhered to the release schedule, which facilitated the deployment of new features and bug fixes.

### Collaborative Contributions

- **Pull Requests and Code Reviews**:
  - Reviewed and merged pull requests, ensuring high code quality and consistency with project standards.
  - Provided detailed comments and suggestions to improve the proposed changes, fostering a collaborative team 
  environment.

- **Acknowledgments in Developer Guide**:
  - Contributed to the Developer Guide by including a section for acknowledgments, thanking contributors and tools 
  that have facilitated the project's success.

## Additional Contributions

### README Page Creation

- **Overview**:
  - Authored the README page that serves as the front page of our GitHub project repository. This comprehensive page 
  provides essential project information, quick start details, and links to the User and Developer Guides, crucial for onboarding new users and developers.
