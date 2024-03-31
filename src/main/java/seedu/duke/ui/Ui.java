package seedu.duke.ui;

import seedu.duke.enums.CEGModules;
import seedu.duke.modules.Module;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Ui {
    private static final String COMMENT_LINE_FORMAT_REGEX = "#.*";
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
        printHyphens();
        System.out.println("Hello! This is your CEG Future Academic Planner!");
        System.out.println("What can I do for you?");
    }

    // Prints if name is present
    public static void printUserGreeting(String name, int startSem, int gradSem) {
        printHyphens();
        System.out.println("Hello " + name + "! This is your CEG Future Academic Planner!");
        System.out.println("What would you like to do today?");
    }

    public static void printUserInfo(String name, int startSem, int gradSem) {
        String greeting = name.equals("") ? "Hello!" : "Hello " + name + "!";
        String currentSemesterInfo = String.format("You are currently in Semester %d", startSem);
        String graduationSemesterInfo = String.format("and expected to graduate in Semester %d", gradSem);

        System.out.println(greeting);
        System.out.println(currentSemesterInfo);
        System.out.println(graduationSemesterInfo);
    }

    // Helper function for printModulePlan()
    private static void printSemesterTableHeader(String... semesters) {
        System.out.print("|");
        for (String semester : semesters) {
            System.out.printf("%-11s|", semester);
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

                System.out.printf(" %-8s %-2s", moduleCode, moduleGrade);
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
        System.out.println("Be sure to also complete your GESS, GEC, and GEN modules.");
    }

    public static void printHyphens() {
        System.out.println("__________________________________________________");
    }

    public static void printExit() {
        System.out.println("Bye. Enjoy your studies!");
    }

}
