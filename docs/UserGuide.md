# User Guide for Future Academic Planner (FAP)

## Introduction

Future Academic Planner (FAP) is a **robust academic management software** designed to help **streamline NUS Computer
Engineering (CEG) student’s academic journey at NUS**. If you can type fast, FAP can get your module planning done
faster than your traditional GUI app.

## Content Navigation:

- [Quick Start](#quick-start)
- [Features](#features)
    - [User Management](#user-management)
        - [`set`: Personalizes the bot for the user](#personalization-set)
    - [Course Management](#course-management)
        - [`add`: Adds a module](#add-module-add)
        - [`remove`: Removes a module](#remove-module-remove)
        - [`view`: View modules](#view-modules-view)
        - [`graduate`: View remaining modules required to graduate](#view-remaining-modules-to-graduate-graduate)
    - [Grade Management](#grade-management)
        - [`grade`: Add or edit grades to a module](#change-grade-grade)
        - [`gpa`: View current GPA](#view-gpa-gpa)
        - [`desiredgpa`: Calculate grades required for a gpa goal](#feasibility-of-desired-gpa-desiredgpa)
    - [Miscellaneous Commands](#miscellaneous-commands)
        - [`help`: View list of command syntax available for FAP](#display-help-message-in-FAP-help)
        - [`exit`: Exit Program](#exit-program-bye)
- [FAQ](#faq)
- [Command Summary](#command-summary)

## Quick Start

1. Ensure you have `java 11` or above installed on your computer

2. Download the latest `FAP.jar` from [here](https://github.com/AY2324S2-CS2113-W14-3/tp/releases)

3. Copy the file `FAP.jar` to a new empty folder

4. Open a command terminal in the directory of the folder, and use `java -jar FAP.jar` to run the application

5. Type commands in the terminal and press enter to execute them
    - Some example commands are:
        - `add c/COURSE_CODE w/WHEN`: Add a module (`COURSE_CODE`) to your module plan on Semester (`WHEN`)
        - `view`: Shows your module plan so far
        - `bye`: Exits the FAP application

6. Head to [**Features**](#features) right below for more details on each command

7. Alternatively, Refer to [**Command Summary**](#command-summary) for the list of commands available

## Features

> [!IMPORTANT]  
> All arguments **must be provided** and **must follow the specified order** in Format
>
> Words in `UPPER_CASE` are the parameters to be **supplied by the user**
>
> - Eg. For the command `remove c/COURSE_CODE`, `COURSE_CODE` is a parameter which can be used as `remove c/CS2113`

## User Management

### Personalization: `set`

`set`: Personalises the bot for the user

- Sets the **name of the user and their current studying semester** for FAP
- **By default**, Name is set to `Anonymous`, Current Semester is set to `1`

Format: `set n/NAME curr/CURR_SEM`

`NAME` represents the name of the user

`CURR_SEM` represents the current semester (From 1-8) the user is currently in

* `NAME` must only have alphabetic characters (either uppercase or lowercase) and optionally spaces in between
* `CURR_SEM` must be a positive integer from 1-8, to represent the semester the user is currently in

Example usage:

`set n/James Gosling curr/1`

Expected output:

```
__________________________________________________
Greetings James Gosling! Your details are updated:
You are currently in Semester 1
__________________________________________________
```

## Course Management

### Add module: `add`

`add`: Adds modules to the user's course plan

Format: `add c/COURSE_CODE w/WHEN`

- `COURSE_CODE` must be a valid course code from NUS mods from AY23-24
- `WHEN` must be a valid semester from 1-8
- The module must not already be in the list
- The module must be a valid module from NUS mods from AY23-24
- The module must not be a duplicate of another module in the list
- The module must not be a core module that has already been completed
- The module must not be a core module that has already been added to the list

Example usage:

`add c/CS2113 w/4`

Expected output:

```
__________________________________________________
Added the new module: CS2113
Software Engineering & Object-Oriented Programming
__________________________________________________
```

### Remove module: `remove`

`remove`: Removes modules from the user's course plan

Format: `remove c/COURSE_CODE`

- `COURSE_CODE` must be a valid course code from NUS mods from AY23-24

Example usage:

`remove c/CS2113` (assuming CS2113 is in the list)

Expected output:

```
__________________________________________________
Module CS2113 removed!
__________________________________________________
```

### View modules: `view`

`view`: Allows users to view their academic plan.

Format: `view`

- This will show various details, including the user’s
    - `name` (`Anonymous` if not inputted by the user)
    - `current studying` semester (`1` if not inputted by the user)
    - `module course code` and `grade`, along with the semester associated with it (`empty` if not inputted by the
      user)
    - `total modular credits taken` (modular credits of all modules taken up till and including current semester)
    - `total modular credits listed` (modular credits of all modules listed in the schedule)

Example usage:

`view`

Example output:

```
CEG Study Plan for: James Gosling
_____________________________________________________________
| Y1S1 [Sem 1] | Y1S2 [Sem 2] | Y2S1 [Sem 3] | Y2S2 [Sem 4] |
|CS1010        |CG2111A       |CS2040C       |CS2113      A+|
|CG1111A       |PF1101      A-|EE2026        |CG2271        |
|              |DTK1234       |              |              |
_____________________________________________________________
| Y3S1 [Sem 5] | Y3S2 [Sem 6] | Y4S1 [Sem 7] | Y4S2 [Sem 8] |
|CP3880        |              |CG4002        |              |
_____________________________________________________________
- Current Study: Semester 4
- Total MCs taken: 36 / 160
- Total MCs listed: 56 / 160
_____________________________________________________________
```

`view c/COURSE_CODE`: Allows users to view specific course information

Format: `view c/COURSE_CODE`

- The command will show the course title, modular credits, and its description
- The command is only limited to courses offered in NUS AY23/24

Example usage:

`view c/GEX1007`

Example output:

```
======================================================================
| Title: Geopolitics: Geographies of War & Peace          Credits: 4 |
======================================================================
| Description: This is cross-Faculty course aimed at introducing     |
| Geopolitics to a broad range of students. The course is designed   |
| to be as engaging and interactive as possible: utilising various   |
| multi-media tools, including documentary clips, news media, filmic |
| resources, popular journals and magazines. Not surprisingly, the   |
| course has a very lively section on Popular Geopolitics. In        |
| addition, we introduce students to the interactions of geopolitics |
| and political space through detailed examination of different      |
| geopolitical eras and codes, specifically: Cold War Geopolitics    |
| and the Human Landscapes of Southeast Asia: and the so-called      |
| "Global War on Terror".                                            |
======================================================================
```

### View remaining modules to graduate: `graduate`

`graduate`: Allows users to view the remaining core CEG modules left to take along with their respective MCs

Format: `graduate`

- EG3611A (Industrial Attachment) and CP3880 (Advanced Technology Attachment Programme) will both show on the list if
  neither is completed. Both will be removed from the list when either one is completed.

Example usage:

`graduate` (assuming `add` was never called on any valid modules)

Expected output:

```
+---------------------------+------------+
| Course Code               | MCs        |
+---------------------------+------------+
| ES2631                    | 4          |
| CS1010                    | 4          |
| GEA1000                   | 4          |
| DTK1234                   | 4          |
| EG1311                    | 4          |
| IE2141                    | 4          |
| EE2211                    | 4          |
| CDE2501                   | 4          |
| CDE2000                   | 4          |
| PF1101                    | 4          |
| CG4002                    | 8          |
| MA1511                    | 2          |
| MA1512                    | 2          |
| MA1508E                   | 4          |
| EG2401A                   | 2          |
| EG3611A                   | 10         |
| CP3880                    | 12         |
| CG1111A                   | 4          |
| CG2111A                   | 4          |
| CS1231                    | 4          |
| CG2023                    | 4          |
| CG2027                    | 2          |
| CG2028                    | 2          |
| CG2271                    | 4          |
| CS2113                    | 4          |
| EE2026                    | 4          |
| EE4204                    | 4          |
+---------------------------+------------+
Be sure to also complete 40MCs of Unrest
ricted Electives, GESS, GEC, and GEN mod
ules.
```

## Grade Management

### Change grade: `grade`

`grade`: Input or adjust the grade obtained for a module

Format: `grade c/COURSE_CODE g/GRADE`

`COURSE_CODE` represents the module that you want to input a grade for

`GRADE` represents the grade you attained for that module

- The module should be added into the list first with the add module function
- CS/CU modules can only have their grades be set to CS
- Currently unable to account for modules that are under GRADED grading basis but cannot be S/U
- `GRADE` only accepts alphabetical grade. Following are the allowed inputs for `GRADE`
    - `A+, A, A-, B+, B, B-, C+, C, D+, D, F, CS, S`
    - For U, CU, W, IP grade, user can simply remove the module from the list 
    - EXE grade is not implemented in this version
- You can only change the grade of modules that are before or during your current semester
    - If current semester 5, you can only change grades of modules from semester 1 to 5

Example usage:

`grade c/cs1231 g/B`

Expected output:

```
__________________________________________________
Grade for CS1231 updated to B
__________________________________________________
```

### View GPA: `gpa`

`gpa`: Shows user their current GPA

Format: `gpa`

Example usage:

`gpa`

Expected output:

```
__________________________________________________
Your current GPA is: 4.25
__________________________________________________
```

### Feasibility of desired GPA: `desiredgpa`

`desiredgpa`: Checks if user's desired final GPA is possible with regard to current GPA, and show a combination of
required grades to obtain to achieve the desired GPA

Format: `desiredgpa DESIRED_GPA`

- `DESIRED_GPA` must be a number from 0 to 5
- Assumes remaining mods to be taken are all graded 4MC mods
- Assumes user take 160MC by graduation
- Modules that have not been graded will be treated as invisible

Example usage:

`desiredgpa 4.75` (assuming 2 mods already inputted, 1 A and 1 B)

Expected output:

```
__________________________________________________
To obtain desired GPA of: 4.75
You will need: 21 A and 17 A-
With the above grades, your end GPA will be: 4.75
__________________________________________________
```

## Miscellaneous commands

### Display help message in FAP: `help`

`help`: View list of command syntax available for FAP

Format: `help`

Example usage:

`help`

Expected output:
```
_____________________________________________________________
Available Commands:
NOTE: "<WORD>" represents a user-typed argument that is required for the command
1. set n/<NAME> curr/<CURR_SEM> - Set name & current semester
2. add c/<COURSE_CODE> w/<WHEN> - Add a module to your schedule
3. remove c/<COURSE_CODE> - Remove a module from your schedule
4. grade c/<COURSE_CODE> g/<GRADE> - Add or change a module grade
5. gpa - View your GPA
6. desiredgpa <GPA> - Calculates grades needed to achieve a desired GPA
7. view - View modules on your schedule
8. view c/<COURSE_CODE> - View selected module information
9. graduate - View remaining core modules and MCs left to graduate
10. help - View command syntax and list of commands available for FAP
11. bye - Exit the program

Argument format:
<NAME>: Alphabetic characters and optionally spaces between
<CURR_SEM>: Valid semester from 1-8
<COURSE_CODE>: Valid NUS course code from AY23-24
<WHEN>: Valid semester from 1-8
<GRADE>: Alphabetic grade (A+, A, A-, B+, B, B-, C+, C, D+, D, F, CS, S)
<GPA>: Number from 0 to 5
_____________________________________________________________
```

### Exit Program: `bye`

`bye`: Close and exits the program safely

Format: `bye`

Example usage:

`bye`

Expected output:
```
_____________________________________________________________
Bye. Enjoy your studies!
```

## FAQ

**Q**: How do I edit a module if I accidentally added it to a wrong semester?

**A**: Currently you will need to remove the module then add it again to the correct semester.

**Q**: How do I edit a module if I accidentally added it with a wrong gpa?

**A**: You can use the command `grade c/COURSE_CODE g/GRADE`.

**Q**: What if I S\U the mod, and the mod in the list originally has a grade?

**A**: You can use the command `grade c/COURSE_CODE g/GRADE, then in the GRADE section put S.

**Q**: How can I change the current semester I am currently in?

**A**: You can type the set command again to indicate the current semester you are in.

## Command Summary

#### Command format:
* Set user information `set n/NAME curr/CURR_SEM`
* Add modules `add c/COURSE_CODE w/WHEN`
* Remove modules `remove c/COURSE_CODE`
* Input grade `grade c/COURSE_CODE g/GRADE`
* View GPA `gpa`
* Check for desired GPA `desiredgpa DESIRED_GPA`
* View modules in schedule `view`
* View specific module information `view c/COURSE_CODE`
* View modules still required for graduation `graduation`
* Show help message `help`
* Exit the program `bye`


####  Argument format:
* `NAME`: Alphabetic characters and optionally spaces between
* `CURR_SEM` / `WHEN`: Valid semester from 1-8
* `COURSE_CODE`: Valid NUS course code from AY23-24
* `GRADE`: Alphabetic grade (A+, A, A-, B+, B, B-, C+, C, D+, D, F, CS, S)
* `GPA`/ `DESIRED_GPA`: Number from 0 to 5
