# User Guide

## Introduction

Future Academic Planner (FAP) is a **robust academic management software** designed to help **streamline NUS Computer Engineering (CEG) student’s academic journey at NUS**. If you can type fast, FAP can get your module planning done faster than your traditional GUI app.

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
* `GRAD_SEM` must be a positive integer from 1-8, to represent the semester the user will be expected to graduate. `GRAD_SEM` value cannot be lower than the value for `CURR_SEM`
  
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

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
