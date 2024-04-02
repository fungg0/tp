# User Guide for Future Academic Planner (FAP)

## Introduction

Future Academic Planner (FAP) is a **robust academic management software** designed to help **streamline NUS Computer
Engineering (CEG) student’s academic journey at NUS**. If you can type fast, FAP can get your module planning done
faster than your traditional GUI app.

##  Content Navigation:
- [Quick Start](#quick-start)
- [Features](#features)
    - [User Management](#user-management)
        - [`init`: Personalizes the bot for the user.](#personalization-init)
    - [Course Management](#course-management)
        - [`add`: Adds a module.](#add-module-add)
        - [`remove`: Removes a module.](#remove-module-remove)
        - `view`: View modules in schedule.
        - [`graduate`: View remaining modules required to graduate.](#view-remaining-modules-to-graduate-graduate)
    - [Grade Management](#grade-management)
        - `grade`: Add or edit grades to a module.
        - `gpa`: View current GPA.
        - `desiredgpa`: Calculate grades required for a gpa goal.
- [Saving Data](#saving-data)
- [FAQ](#faq)
- [Command Summary](#command-summary)

## Quick Start

1. Ensure you have `java 11` or above installed on your computer
   
2. Download the latest `FAP.jar` from [here](https://github.com/AY2324S2-CS2113-W14-3/tp/releases)
   
3. Copy the file `FAP.jar` to a new empty folder
 
4. Open a command terminal in the directory of the folder, and use `java -jar carrot.jar` to run the application
   
5. Type commands in the terminal and press enter to execute them. 
   - Some example commands are:
        - `add c/COURSE_CODE w/WHEN`: Add a module (`COURSE_CODE`) to your module plan on Semester (`WHEN`)
        - `view`: Shows your module plan so far
        - `bye`: Exits the FAP application
          
6. Head to [**Features**](#features) right below for more details on each command.
 
7. Alternatively, Refer to [**Command Summary**](#command-summary) for the list of commands available. 

## Features 

> [!IMPORTANT]  
> All arguments **must be provided** and **must follow the specified order** in Format.
>
> Words in `UPPER_CASE` are the parameters to be **supplied by the user**.
>
> - Eg. For the command `remove c/COURSE_CODE`, `COURSE_CODE` is a parameter which can be used as `remove c/CS2113`

## User Management

### Personalization: `init` 

`init`: Personalises the bot for the user.

- Sets the **name of the user, current studying semester, and expected graduation semester** for FAP.
- **By default**, Name is set to `Anonymous`, Current Semester is set to `1`, Graduation Semester is set to `8`.

Format: `init n/NAME curr/CURR_SEM grad/GRAD_SEM`

`NAME` represents the name of the user

`CURR_SEM` represents the current semester (From 1-8) the user is currently in.

`GRAD_SEM` represents the expected graduation semester (From 1-8) the user will graduate from NUS

* `NAME` must only have alphabetic characters (either uppercase or lowercase) and optionally spaces in between.
* `CURR_SEM` must be a positive integer from 1-8, to represent the semester the user is currently in
* `GRAD_SEM` must be a positive integer from 1-8, to represent the semester the user will be expected to
  graduate. `GRAD_SEM` value cannot be lower than the value for `CURR_SEM`

Example usage:

`init n/James Gosling curr/1 grad/8`

Expected output:

```
__________________________________________________
Greetings James Gosling! Your details are updated:
You are currently in Semester 1
You are expected to graduate in Semester 8
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
Module CS2113 Removed!
__________________________________________________
```

### View remaining modules to graduate: `graduate`

`graduate`: Allows users to view the remaining core CEG modules left to take along with their respective MCs

Format: `graduate`

- EG3611A (Industrial Attachment) and CP3880 (Advanced Technology Attachment Programme) will both show on the list if
  neither is completed. Both will be removed from the list when either one is completed.

Example usage:

`graduate` (assuming `add` was never called on any valid modules).

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

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
