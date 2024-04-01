# User Guide

## Introduction

Future Academic Planner (FAP) is a **robust academic management software** designed to help **streamline NUS Computer
Engineering (CEG) student’s academic journey at NUS**. If you can type fast, FAP can get your module planning done
faster than your traditional GUI app.

## Quick Start

{Give steps to get started quickly} {To be modified}

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features

> [!IMPORTANT]  
> All arguments **must be provided** and **must follow the specified order** in Format.
>
> Arguments required from the user are specified in **CAPITAL LETTERS**
> - Eg. The argument `n/NAME` would require the user to type `n/` followed by the user's required argument, their `NAME`

### Personalization : init

Personalises the bot for the user.

- Sets the **name of the user, current studying semester, and expected graduation semester** for FAP.

- **By default**, Name is set to `Anonymous`, Current Semester is set to `1`, Graduation Semester is set to `8`.

Format: `init n/NAME curr/CURR_SEM grad/GRAD_SEM`

**Arguments:**

`NAME` represents the name of the user

`CURR_SEM` represents the current semester the user is currently in

`GRAD_SEM` represents the expected graduation semester the user will graduate from NUS.

* `NAME` must only have alphabetic characters (either uppercase or lowercase) and optionally spaces in between.
* `CURR_SEM` must be a positive integer from 1-8, to represent the semester the user is currently in
* `GRAD_SEM` must be a positive integer from 1-8, to represent the semester the user will be expected to
  graduate. `GRAD_SEM` value cannot be lower than the value for `CURR_SEM`

Example usage:

`init n/James Gosling curr/1 grad/8`

`init n/Tommy curr/4 grad/8`

Expected output:

```
__________________________________________________
Greetings James Gosling! Your details are updated:
You are currently in Semester 1
You are expected to graduate in Semester 8
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

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
