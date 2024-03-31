# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Design & implementation

### Main Class: `FAP`

The `FAP` class serves as the central hub of the application, orchestrating the flow of execution and managing critical
resources. It embodies key software design principles and showcases thoughtful architectural decisions.

#### Design Overview

- **Singleton Components:** `FAP` manages singleton components such as `moduleList` and `LOGGER`, ensuring they are
  initialized once and accessible throughout the application's lifecycle.
    - `ModuleList`: A collection that holds modules, initialized with a capacity of 10.
    - `LOGGER`: Utilized for logging various levels of application events and errors.

- **Separation of Concerns:** The class delegates specific responsibilities to specialized classes, adhering to the
  principle of separation of concerns.
    - Interaction with users is managed by the `Ui` class.
    - Command parsing is delegated to the `Parser` class.
    - Command execution is handled by implementations of the `Command` interface.

- **Error Handling:** Demonstrates robust error handling strategies by catching and logging different exceptions, which
  ensures graceful handling of unexpected situations.

#### Implementation Details

1. **Application Initialization and Entry Point:**

   The `main` method, as the application's entry point, performs initial setups such as greeting the user and ensuring
   critical components are initialized properly. It encapsulates high-level flow control and implements error handling
   to manage assertion errors and unexpected exceptions.

   ```java
   public static void main(String[] args) {
       try {
           printGreeting();
           assert moduleList != null : "moduleList should not be null";
           runApplication();
       } catch (AssertionError e) {
           LOGGER.log(Level.SEVERE, "Assertion failed: " + e.getMessage(), e);
           System.err.println("Critical assertion failure: " + e.getMessage());
       } catch (Exception e) {
           LOGGER.log(Level.SEVERE, "An unexpected error occurred: " + e.getMessage(), e);
           System.err.println("An unexpected error occurred: " + e.getMessage());
       }
   }
   ```
   #### UML Diagram

   ![FAP class diagram](diagrams/FAP.png)

   Below is a brief description of the UML diagram that outlines the structure and relationships of the `FAP` class:

    - **Classes:** `FAP`, `ModuleList`, `Ui`, `Parser`, and `Command`.
    - **Associations:** `FAP` has associations with `ModuleList` for managing modules and `LOGGER` for logging. It
      uses `Ui`
      for user interactions, `Parser` for parsing commands, and `Command` for executing actions.
    - **Flow:** The diagram would show `FAP` at the center, indicating its role in orchestrating the application flow
      and
      its interactions with other components.

   This section highlights the central role of the `FAP` class in coordinating the application's functionality,
   emphasizing
   its design as a modular, maintainable, and extensible entry point.


2. **Running the Application Loop:**

   The `runApplication` method maintains a loop that processes user input until an error occurs or an exit condition is
   met. This method highlights interactions with other components of the application and demonstrates the use of
   polymorphism and encapsulation.

   ```java
   private static void runApplication() {
       Ui ui = new Ui();
       boolean continueRunning = true;

       while (continueRunning) {
           try {
               String userInput = ui.getUserCommand();
               LOGGER.log(Level.INFO, "User input: " + userInput);
               Command command = Parser.getCommand(userInput);
               command.execute(userInput);
           } catch (Exception e) {
               LOGGER.log(Level.SEVERE, "An error occurred: " + e.getMessage(), e);
               System.out.println("An error occurred: " + e.getMessage());
               continueRunning = false;
           }
       }
   }
   ```
3. **Module Class:**
  
   #### Purpose
  
   Represents an academic module, holding information such as the module code, credits (MCs), grade, and description.
  
   #### Key Methods
  
   - **`setModuleGrade(String moduleGrade)`**  
    Sets the grade for the module. Validates the grade format and throws `ModuleException` if the module hasn't been taken yet.
  
   - **`getGradeNumber()`**  
     Returns a numerical value associated with the module's grade, used for GPA calculation.

4. **ModuleList Class:**
    
   #### Purpose
    
   Manages a collection of `Module` objects. It facilitates operations such as adding, removing, retrieving modules, calculating GPA, and grouping modules by semester.
    
   #### Key Methods
    
   - **`addModule(Module module)`**  
     Adds a new module to the list.  
     Throws `IllegalArgumentException` if the module is `null`.
   
   - **`getModule(String courseCode)`**  
     Retrieves a module by its course code.  
     Throws `ModuleNotFoundException` if the module is not found.
    
   - **`removeModule(Module module)`**  
     Removes a specified module from the list.
    
   - **`changeModuleGrade(String moduleCode, String grade)`**  
     Changes the grade of a module identified by its course code.
    
   - **`tallyGPA()`**  
     Calculates and returns the GPA based on the modules in the list.  
     Throws `GpaNullException` if there are no modules with countable grades.
    
   - **`groupModulesBySemester()`**  
     Groups modules by their semester and returns a map where each key is a semester, and each value is a list of modules in that semester.
    
   #### Error Handling
    
   Uses exceptions to handle errors, such as when trying to access or modify modules that don't exist.
    
5. **Getting module details from Json File (JsonManager Class):**
    
   #### Overview
    
   The `JsonManager` class is designed to manage and interact with module information stored in a JSON format. It provides functionalities for checking the existence of modules, retrieving module information such as Modular Credits (MCs), description, and title from a JSON file.
    
   #### Constructor
    
   - `JsonManager()`: Initializes a new instance of the `JsonManager` by loading the module information from a JSON file located at `/moduleInfo.json`.
   
   #### Methods
    
   ##### Module Existence
    
   - **`moduleExist(String moduleCode)`**: Checks if a module with the specified code exists in the JSON data.
     - **Parameters**: `String moduleCode` - The code of the module to check for existence.
     - **Returns**: `boolean` - `true` if the module exists, `false` otherwise.
    
   ##### Module Information Retrieval
    
   - **`getModuleInfo(String moduleCode)`**: Retrieves detailed information about a module, including its Modular Credits, description, and title, based on the module code.
     - **Parameters**: `String moduleCode` - The code of the module for which information is to be retrieved.
     - **Note**: This method updates the internal state of the `JsonManager` object with the retrieved module information.
    
   ##### Information Accessors
    
   - **`getModuleDescription()`**: Returns the description of the last module queried.
     - **Returns**: `String` - The description of the module.
   
   - **`getModuleMC()`**: Returns the Modular Credits of the last module queried.
     - **Returns**: `int` - The Modular Credits of the module.
    
   - **`getModuleTitle()`**: Returns the title of the last module queried.
     - **Returns**: `String` - The title of the module.
    
   #### Error Handling
    
   - The constructor throws a `RuntimeException` if the JSON file containing module information cannot be found or accessed, ensuring that the application is aware of missing or inaccessible module data.
    
   #### Usage
    
   ```java
   JsonManager jsonManager = new JsonManager();
   if (jsonManager.moduleExist("CS1010")) {
       jsonManager.getModuleInfo("CS1010");
       System.out.println("Module Title: " + jsonManager.getModuleTitle());
       System.out.println("Module Description: " + jsonManager.getModuleDescription());
       System.out.println("Module MC: " + jsonManager.getModuleMC());
   }
   ```
6. **Viewing modules left to graduate**

   The `ViewGraduateCommand` class is responsible for displaying the list of modules that a student needs to
   complete
   for graduation with respect to the modules the user has previously completed. The diagram below provides an overview
   of how this class interacts with other components in the
   system.

   ![View Graduate Module Class](diagrams/ViewGraduateModuleClass.png)

   It utilizes the `Ui` class to print out the list of modules in a
   formatted manner. Additionally, it interacts with the `CEGModules` enum to retrieve module information such as
   module codes and Module Credits (MCs).
7. **Viewing GPA**

   The `ViewGpaCommand` class is responsible for displaying the current GPA attained by the student. It
   accesses `ModuleList`, which looks through all `Module` object contained in the list. If the `Module` is marked as
   taken and has been assigned a valid grade with `GradeCommand` by the user, its grades will be included into the
   calculation.

   This is the formula used for tabulation of GPA.
   `GPA = SUM(Course Grade Point * Course Units) / SUM(Course Units Counted Towards GPA)`

   Below is the sequence diagram for `ViewGpaCommand`.
   ![View Gpa Command Sequence Diagram](diagrams/ViewGpaCommand.png)

8. **Parsing UserInput**

   The `Parser` class, together with the `CommandMetadata` class parses user input to
   **return appropriate command objects** for the corresponding `Command` classes. If input validation fails or no
   matching command is found, it returns an `Invalid` command instance.
   <br />
   <br />
   **High Level Overview:**

   The`CommandMetadata` class is an abstract class that manages regular expressions (regex) and validation
   for command arguments, allowing subclasses to generate specific **`Command` instances** based on **command keywords
   and
   parsed arguments.**
   <br />
   <br />
   The `Parser` class maintains a list of these `CommandMetadata` subclasses instances and iterates through them to
   identify a given user command.
   <br />
   <br />
   Below is a diagram that shows this relationship

   ![ParserClassDiagram.png](diagrams/ParserClassDiagram.png)
   <br />
   <br />
   **Use of regular expressions (Regex) in FAP:**

   The add command class requires a user input that best
   matches this string
    ```
    add c/COURSECODE w/SEMESTERTAKEN
    ```
   where `COURSECODE` and `SEMESTERTAKEN` have their defined restrictions: `COURSECODE` should best match an actual
   course code at NUS, `SEMESTERTAKEN` should be a number value in some range. The `COURSECODE` and `SEMESTERTAKEN`
   will thus have their own argument regex pattern.
   <br />
   <br />
   A simple example would be that `SEMESTERTAKEN` would be a number ranging from 1-8 to represent a normal honours
   pathway for a CEG student (FAP's target user). A regex pattern for that would look like `w/(?<semester>[1-8])`.
   An **argument name capturing group** `semester` is enclosed within the brackets so that the argument group will be
   **named** and thus the argument value (anywhere between `1-8`) can be referenced/called by using the
   `matcher.group()` method.
   Meanwhile, the `Pattern` and `Matcher` methods used for regex would handle the checks that the argument value given
   is indeed between `1-8`
   <br />
   <br />
   A userInput regex would thus follow this convention:
    ```
    keyword argument_1 argument_2 ...
    ```
   This full regex pattern for a command itself can be generated by having a `keyword`, as well as all the
   `argument group names` (a name to use so as to _reference_ the argument) and the `argument regex pattern`
   corresponding to that name reference. Typically, these arguments would be spaced out and thus a `\s+` (representing
   at least one whitespace character) is placed between the gaps of the regex pattern for
   `keyword, argument_1, argument_2...`
   <br />
   <br />
   While regex allows the `userInput` checks to be prudent, as well as potentially offering the flexibility for string
   inputs to allow a different order of arguments, there are limitations where it becomes hard to determine the exact
   error of the user's input solely based on the regular expressions, because it solely returns a true/false value
   if the string value itself fits the criteria given). Regardless, we think the use of regex in FAP can help provide
   us **safety in the arguments** that passes through to the commands via the userInput.
   <br />
   <br />
   **Developer usage FAP: Parser & CommandMetadata class as of v2.0**: **How to create a new command**

- First, we need a `Command` type class to return as an object. In the future, this may be expanded to any `T` type.
- Second, we need a string that would be used to create this `Command` instance. This string should follow the
  format `keyword argument_1 argument_2` where arguments are **optional**.
- Third, for every argument available, make a **regex pattern with name capturing** that encloses the value within the
  brackets. (e.g., `n/(?<name>[A-Za-z0-9 ]+)`, `g/(?<grade>[ab][+-]?|[cd][+]?|f|cs|cu)`)

- **Using example `add c/COURSECODE w/SEMESTERTAKEN`**
    - Create a subclass that extends `CommandMetadata`.
    - Put in the `keyword` (e.g., `add`) and `groupArgumentNames` (e.g., `{"courseCode", "semester"}`) in the superclass
      constructor.
    - Define the argument regex pattern in the static variable `argsRegexMap` Note: Currently, `argsRegexMap` is in the
      superclass `CommandMetadata`. (e.g., `argRegexMap.put("semester", "w/(?<semester>[1-8])")`).
    - Override the method `createCommandInstance(Map<String, String> args)` to implement the method on how to create
      the `Command` object you want. Return the `Command` instance.
        - `Map<String, String> args` contains the `groupArgumentName : argumentValue` pairing.
    - In the `Parser` class, add the created `CommandMetadata` subclass to `metadataList`.
    - The `Parser` method `getCommand(String userInput)` will help validate the `userInput`. If the `userInput` matches
      the string you wanted, then `getCommand(String userInput)` will return the Command instance you require.

Sample example code:

  ```java
  public class AddCommandMetadata extends CommandMetadata {
    private static final String ADD_KEYWORD = "add";
    private static final String[] ADD_ARGUMENTS = {"courseCode", "semester"};

    public AddCommandMetadata() {
        super(ADD_KEYWORD, ADD_ARGUMENTS);
    }

    // Add Command Creator
    @Override
    protected Command createCommandInstance(Map<String, String> args) {
        try {
            String moduleCode = args.getOrDefault("courseCode", "COURSECODE_ERROR");
            String semester = args.getOrDefault("semester", "SEMESTER_ERROR");
            int semesterInt = Integer.parseInt(semester);

            return new AddCommand(moduleCode, semesterInt);
        } catch (ModuleNotFoundException e) {
            LOGGER.log(Level.SEVERE, "An error occurred: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        }
        return new InvalidCommand();
    }
}
  ```
Design & Implementation
Storage Class

The Storage class is crucial for persisting user data between sessions, ensuring that module information and user preferences are not lost even after the application is closed. It interacts with the file system to load and save data, employing error handling to manage potential IO exceptions gracefully.
Key Responsibilities

- **File Management:** Ensures necessary directories and files exist at application startup or creates them if they don't.
- **Data Persistence:** Serializes and deserializes user data, including modules and user information, to and from a designated file.
- **Error Handling:** Captures and throws custom exceptions to signal file access or data integrity issues, facilitating robust error management in higher-level components.

**Implementation Details**

- **Initialization:** Verifies the presence of required directories/files or creates them. This is crucial for first-time application runs on a user's machine.

- **Saving Data:**
    The `saveModulesToFile` method serializes the current state of moduleList and user into a human-readable format (or a structured format like JSON/XML, depending on implementation) and writes it to a file. This method is invoked at critical points, such as application shutdown or after any operation that alters user data.

- **Loading Data:**
    The `loadDataFromFile` method reads the file contents, deserializes them back into the application's data structures (moduleList and user), and ensures that the application state reflects the persisted data. This method is typically called at application startup.

- **Data Integrity and Error Management:**
    Implements `try-catch` blocks to manage `IOExceptions` and custom exceptions, ensuring the application can handle and recover from unexpected issues during file operations.

   ### UML Diagram

   A simplified UML diagram for the Storage class and its interaction with the Module, User, and exception classes is shown below:
-    ![View Storage Class](diagrams/Storage.png)

   ### Integration with FAP

   The FAP class incorporates the Storage class to manage application data persistence. At startup, FAP calls Storage.loadDataFromFile to restore the previous session's state. Before termination or at regular intervals, FAP invokes Storage.saveModulesToFile to save the current state.

   This integration ensures that the application's data lifecycle is managed efficiently, providing a seamless user experience across sessions.

---

## Product scope

### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
|---------|----------|---------------------------|-------------------------------------------------------------|
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0    | user     | find a to-do item by name | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}




