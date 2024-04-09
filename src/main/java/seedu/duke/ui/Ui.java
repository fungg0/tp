package seedu.duke.ui;

import seedu.duke.enums.CEGModules;
import seedu.duke.modules.Module;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Ui {
    private static final String COMMENT_LINE_FORMAT_REGEX = "#.*";
    private static final String COMMANDS_HELP_MESSAGE =
            "Available Commands:\n" +
                    "NOTE: \"<WORD>\" represents a user-typed argument that is required for the command\n" +
                    "1. set n/<NAME> curr/<CURR_SEM> - Set name & current semester\n" +
                    "2. add c/<COURSE_CODE> w/<WHEN> - Add a module to your schedule\n" +
                    "3. remove c/<COURSE_CODE> - Remove a module from your schedule\n" +
                    "4. grade c/<COURSE_CODE> g/<GRADE> - Add or change a module grade\n" +
                    "5. gpa - View your GPA\n" +
                    "6. desiredgpa <GPA> - Calculates grades needed to achieve a desired GPA\n" +
                    "7. view - View modules on your schedule\n" +
                    "8. view c/<COURSE_CODE> - View selected module information\n" +
                    "9. graduate - View remaining core modules and MCs left to graduate\n" +
                    "10. help - View command syntax and list of commands available for FAP\n" +
                    "11. bye - Exit the program";
    private final Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public void close() {
        in.close();
    }

    public String getUserCommand() {
        printHyphens();
        String currentLine = in.nextLine();

        while (shouldIgnore(currentLine)) {
            currentLine = in.nextLine();
        }
        printHyphens();
        return currentLine;
    }

    public static void printMessage(String str) {
        System.out.println(str);
    }

    private boolean shouldIgnore(String currentLine) {
        return currentLine.isBlank() || currentLine.trim().matches(COMMENT_LINE_FORMAT_REGEX);
    }

    public static void printGreeting() {
        printGreeting("");
    }

    public static void printGreeting(String name) {
        String greeting = name.equals("") ? "Hello!" : "Hello " + name + "!";

        printHyphens();
        System.out.println(greeting + " This is your CEG Future Academic Planner!");
        System.out.println("What would you like to do today?");
        printHyphens();
    }

    public static void printHelpInfoCommand() {
        System.out.println("Type \"help\" to view the list & syntax of available commands");
    }

    public static void printCommandGuide() {
        System.out.println(COMMANDS_HELP_MESSAGE);
    }

    public static void printUserInfo(String name, int startSem) {
        String greeting = String.format("Greetings %s! Your details are updated:", name);
        String updatedCurrentSemesterInfo = String.format("You are currently in Semester %d", startSem);

        System.out.println(greeting);
        System.out.println(updatedCurrentSemesterInfo);
    }

    public static void printScheduleHeader(String name) {
        String user = (name.isEmpty() || name == null) ? "Anonymous" : name;
        String title = String.format("CEG Study Plan for: %s", user);

        System.out.println(title);
    }

    public static void printScheduleDetails(int startSem, int mcsTaken, int mcslisted) {
        String currentSemesterInfo = String.format("- Current Study: Semester %d", startSem);
        String mcsTakenInfo = String.format("- Total MCs taken: %d / 160", mcsTaken);
        String mcsListedInfo = String.format("- Total MCs listed: %d / 160", mcslisted);

        System.out.println(currentSemesterInfo);
        System.out.println(mcsTakenInfo);
        System.out.println(mcsListedInfo);
    }

    // Helper function for printModulePlan()
    private static void printSemesterTableHeader(String... semesters) {
        System.out.print("|");
        for (String semester : semesters) {
            System.out.printf("%-14s|", semester);
        }
        System.out.println();
    }

    // Helper function for printModulePlan()
    private static void printModulesForSemesters(
            Map<Integer, ArrayList<Module>> moduleBySemMap,
            int startSem,
            int endSem
    ) {
        // Get the max number of modules per sem between the start sem and end sem.
        int maxModules = 0;
        for (int i = startSem; i <= endSem; i++) {
            ArrayList<Module> moduleForSemester = moduleBySemMap.getOrDefault(i, new ArrayList<>());
            maxModules = Math.max(maxModules, moduleForSemester.size());
        }

        // Print the module codes and grades under each semester. Prints horizontally.
        for (int j = 0; j < maxModules; j++) {
            for (int i = startSem; i <= endSem; i++) {
                ArrayList<Module> modulesForSemester = moduleBySemMap.getOrDefault(i, new ArrayList<>());
                int moduleListSize = modulesForSemester.size();

                Module module = (moduleListSize > j) ? modulesForSemester.get(j) : null;
                String moduleCode = (module != null) ? module.getModuleCode() : "";
                String moduleGrade = (module != null && module.getModuleGrade() != null) ? module.getModuleGrade() : "";

                System.out.printf(" %-11s %-2s", moduleCode, moduleGrade);
            }
            System.out.println(" ");
        }
    }

    public static void printModulePlan(Map<Integer, ArrayList<Module>> moduleBySemMap) {
        printHyphens();
        printSemesterTableHeader("Y1S1", "Y1S2", "Y2S1", "Y2S2");
        printModulesForSemesters(moduleBySemMap, 1, 4);
        printHyphens();
        printSemesterTableHeader("Y3S1", "Y3S2", "Y4S1", "Y4S2");
        printModulesForSemesters(moduleBySemMap, 5, 8);
        printHyphens();
    }

    public static void printModulesToComplete(ArrayList<String> modulesToComplete) {
        int courseCodeTableWidth = 25;
        int mcTableWidth = 10;
        int borderWidth = 5;

        System.out.println("+---------------------------+------------+");
        System.out.println("| Course Code               | MCs        |");
        System.out.println("+---------------------------+------------+");
        for (String moduleCode : modulesToComplete) {
            String paddedModuleCode = String.format("| %-" + courseCodeTableWidth + "s |", moduleCode);
            String paddedModuleMC = String.format(" %-" + mcTableWidth + "s |",
                    CEGModules.mapStringToEnum(moduleCode).getModuleMC());
            System.out.println(paddedModuleCode + paddedModuleMC);
        }
        System.out.println("+---------------------------+------------+");
        printWrappedText("Be sure to also complete 40MCs of Unrestricted Electives, GESS, GEC, and GEN modules.",
                courseCodeTableWidth + mcTableWidth + borderWidth, 0);
    }

    public static void printViewModule(String courseTitle, String courseMC, String courseDescription) {
        int minimumTableWidth = 50;
        int spaceBetween = 10;
        int titleLength = courseTitle.length() + " Title: ".length();
        int creditsLength = courseMC.length() + " Credits: ".length();
        int padding = 2;
        int border = 2;

        int contentWidth = titleLength + spaceBetween + creditsLength + padding;
        int separatorWidth = Math.max(minimumTableWidth, contentWidth);
        int calculatedSpaceBetween = Math.max(minimumTableWidth - contentWidth, 0);

        System.out.println("=".repeat(separatorWidth));
        System.out.println(String.format("| Title: %s%s          Credits: %s |",
                courseTitle,
                " ".repeat(calculatedSpaceBetween),
                courseMC));
        System.out.println("=".repeat(separatorWidth));
        printWrappedText("Description: " + courseDescription,
                separatorWidth - padding - border,
                separatorWidth - padding - border);
        System.out.println("=".repeat(separatorWidth));
    }

    public static void printWrappedText(String text, int width, int tableWidth) {
        int start = 0;
        int end = 0;

        while (end < text.length()) {
            end = start + width;
            if (end >= text.length()) {
                end = text.length();
            } else {
                while (end > start && !Character.isWhitespace(text.charAt(end))) {
                    end--;
                }
                if (end == start) {
                    end = start + width;
                }
            }
            if (tableWidth > 0) { // print with border
                System.out.println(String.format("| %-" + tableWidth + "s |", text.substring(start, end)));
            } else {
                System.out.println(text.substring(start, end));
            }

            start = end;
            while (start < text.length() && Character.isWhitespace(text.charAt(start))) {
                start++;
            }
        }
    }

    //@@author dextboy
    public static void printHyphens() {
        System.out.println("_____________________________________________________________");
    }

    public static void printExit() {
        System.out.println("Bye. Enjoy your studies!");
    }

}
