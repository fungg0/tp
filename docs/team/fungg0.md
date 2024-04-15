# Fung Wen Wu - Project Portfolio Page

## Overview

Future Academic Planner (FAP) is a **robust academic management software** designed to help **streamline NUS Computer
Engineering (CEG) student’s academic journey at NUS**. If you can type fast, FAP can get your module planning done
faster than your traditional GUI app.

### New Feature

- Implemented Grade Command
  - User can input the grade attained for a module
  - Will not succeed if module is placed in a semester
  - Will check the module's Grading Basis and validate if the appropriate grade is inputted
- Implemented GPA Command
    - User can view their current GPA based on the modules they have taken and grades attained
- Implemented Desired GPA Command
    - Shows user if their desired GPA is still attainable
    - Shows user the number upper and lower bound grades required to achieve desired GPA
- Implemented the `UI` class
  - Scans for next user input
  - Ignores input if the line is just blank spaces

### Code Contributed: [Reposense](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=fungg0&breakdown=true)

### Enhancements Implemented

- Thorough JUnit testing for `grade` and `gpa` feature
- Obtain Grading Basis of each module from Json file

### Documentation

- User Guide:
  - Added documentation for the features `gpa`, `grade` and `desiredgpa`
- Developer Guide:
  - Added implementation details of `gpa` feature
  - Added implementation details of `desiredgpa` feature
  - Added manual testing instructions for `desiredgpa feature`
  - Added the content page
  - Added the user stories

