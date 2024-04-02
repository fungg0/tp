package seedu.duke.modules;

import seedu.duke.enums.CEGModules;
import seedu.duke.exceptions.GpaNullException;
import seedu.duke.exceptions.InvalidGpaException;
import seedu.duke.exceptions.ModuleException;
import seedu.duke.exceptions.ModuleNotFoundException;
import seedu.duke.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;

import static seedu.duke.FAP.LOGGER;

public class ModuleList {
    private static final int DEFAULT_MC_REQUIRED = 160;

    protected ArrayList<Module> moduleList;
    private int moduleCreditsByGraduation = DEFAULT_MC_REQUIRED;
    private double currentGPA;
    private int moduleCreditsCountedToGPA;

    public ModuleList() {
        this.moduleList = new ArrayList<Module>();
    }

    public Module getModule(String courseCode) throws ModuleNotFoundException {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty.");
        }
        courseCode = courseCode.toUpperCase(); // Convert once and reuse, improving efficiency

        for (Module module : moduleList) {
            if (module.getModuleCode().equals(courseCode)) {
                return module;
            }
        }
        throw new ModuleNotFoundException("Module " + courseCode + " not found!");
    }

    public ArrayList<Module> getTakenModuleList() {
        return moduleList;
    }

    public void addModule(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null.");
        }
        moduleList.add(module);
        System.out.println("Added the new module: " + module.getModuleCode() + "\n" + module.getModuleDescription());
    }

    public void add(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null.");
        }
        moduleList.add(module);
    }

    public void printModules() {
        for (Module module : moduleList) {
            System.out.println(module.getModuleCode());
        }
    }

    public void removeModule(Module module) {
        assert module != null : "Module cannot be null";
        // The remove operation returns false if the item was not found
        boolean removed = moduleList.remove(module);
        if (!removed) {
            System.out.println("Module not found in either list.");
        }
    }

    public void changeModuleGrade(String moduleCode, String grade) {
        if (moduleCode == null || moduleCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Module code cannot be null or empty.");
        }
        try {
            Module toChange = getModule(moduleCode);
            toChange.setModuleGrade(grade);
            System.out.println("Grade for " + moduleCode + " updated to " + grade);
            assert toChange.getModuleGrade().equals(grade) : "Grade is not updated successfully";

        } catch (ModuleNotFoundException e) {
            System.out.println("Module not found in list");
        } catch (ModuleException e) {
            System.out.println(e.getMessage());
        }
    }

    public void tallyGPA() throws GpaNullException {
        int totalMC = 0;
        double sumOfGPA = 0;

        for (Module module : moduleList) {
            if (module.getModuleGrade() == null || module.getModuleGrade().equals("CS")) {
                continue;
            }
            totalMC += module.getModuleMC();
            sumOfGPA += module.getGradeNumber() * module.getModuleMC();
        }
        this.moduleCreditsCountedToGPA = totalMC;
        if (totalMC == 0) {
            LOGGER.log(Level.INFO, "No modules with grades available to tabulate GPA.");
            this.currentGPA = 0;
            throw new GpaNullException("No countable grades present to tally.");
        }
        this.currentGPA = sumOfGPA / (double) totalMC;
    }

    public double getCurrentGPA() {
        return this.currentGPA;
    }

    public Map<Integer, ArrayList<Module>> groupModulesBySemester() {
        Map<Integer, ArrayList<Module>> moduleBySemMap = new HashMap<>();
        for (int i = 1; i <= User.MAX_NUM_SEMESTERS; i++) {
            moduleBySemMap.put(i, new ArrayList<>());
        }

        for (Module module : moduleList) {
            moduleBySemMap.get(module.getModuleDate()).add(module);
        }

        return moduleBySemMap;
    }

    public boolean containsModule(String moduleCode) {
        for (Module takenModule : moduleList) {
            ArrayList<String> equivalentList = CEGModules
                    .mapStringToEnum(takenModule.getModuleCode())
                    .getEquivalent();
            boolean hasEquivalent = equivalentList != null && equivalentList.contains(moduleCode);
            if (hasEquivalent || moduleCode.equals(takenModule.getModuleCode())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getModulesToComplete() {
        ArrayList<String> modulesToComplete = new ArrayList<>();
        for (CEGModules cegModule : CEGModules.values()) {
            if (!containsModule(cegModule.name())) {
                modulesToComplete.add(cegModule.name());
            }
        }
        return modulesToComplete;
    }

    public int calculateTotalMCs() {
        return calculateMCs(module -> true);
    }

    public int calculateTakenMCs() {
        return calculateMCs(Module::getModuleStatus);
    }

    private int calculateMCs(Predicate<Module> predicate) {
        int totalMCs = 0;
        for (Module module : moduleList) {
            if (predicate.test(module)) {
                totalMCs += module.getModuleMC();
            }
        }
        return totalMCs;
    }

    public void clearModules() {
        moduleList.clear();
    }

    public void calcGradesExpectations(double desiredGPA) throws InvalidGpaException {
        tallyGPAForCalcGradesExpectations();
        int moduleCreditsTaken = getModuleCreditsTaken();
        int moduleCreditsNotTaken = moduleCreditsByGraduation - moduleCreditsTaken;
        int totalModuleCreditsCountedToGPA = moduleCreditsNotTaken + moduleCreditsCountedToGPA;
        double requiredFutureAverageGrade = (desiredGPA * totalModuleCreditsCountedToGPA -
                currentGPA * moduleCreditsCountedToGPA) /
                moduleCreditsNotTaken;
        validateFutureAverageGrade(requiredFutureAverageGrade);
        double upperBound = getFutureAverageGradeUpperBound(requiredFutureAverageGrade);
        double lowerBound = getFutureAverageGradeLowerBound(requiredFutureAverageGrade);
        int upperBoundGradeNeeded = 0;
        int lowerBoundGradeNeeded = 0;
        double mockGPA = lowerBound;
        while (moduleCreditsNotTaken > 0) {
            if (mockGPA < requiredFutureAverageGrade) {
                upperBoundGradeNeeded += 1;
            } else {
                lowerBoundGradeNeeded += 1;
            }
            mockGPA = calculateMockGPA(upperBound, upperBoundGradeNeeded, lowerBound, lowerBoundGradeNeeded);
            moduleCreditsNotTaken -= 4;
        }
        if (mockGPA < requiredFutureAverageGrade) {
            lowerBoundGradeNeeded -= 1;
            upperBoundGradeNeeded += 1;
            mockGPA = calculateMockGPA(upperBound, upperBoundGradeNeeded, lowerBound, lowerBoundGradeNeeded);
        }
        double acquiredGPA = (currentGPA * moduleCreditsCountedToGPA + mockGPA *
                (4 * (upperBoundGradeNeeded + lowerBoundGradeNeeded))) / totalModuleCreditsCountedToGPA;
        printGradeExpectations(desiredGPA, acquiredGPA, upperBoundGradeNeeded,
                upperBound, lowerBoundGradeNeeded, lowerBound);
    }

    private void tallyGPAForCalcGradesExpectations() {
        try {
            tallyGPA();
        } catch (GpaNullException e) {
            this.currentGPA = 0;
            this.moduleCreditsCountedToGPA = 0;
        }
    }

    private static double calculateMockGPA(double upperBound, int upperBoundGradeNeeded,
                                           double lowerBound, int lowerBoundGradeNeeded) {
        return (upperBound * upperBoundGradeNeeded + lowerBound * lowerBoundGradeNeeded) /
                (upperBoundGradeNeeded + lowerBoundGradeNeeded);
    }

    private static void validateFutureAverageGrade(double requiredFutureAverageGrade) throws InvalidGpaException {
        if (requiredFutureAverageGrade > 5) {
            throw new InvalidGpaException("Your current GPA is too low to achieve desired GPA :(");
        }
        if (requiredFutureAverageGrade < 0) {
            throw new InvalidGpaException("Your current GPA is too high to achieve desired GPA");
        }
    }

    private void printGradeExpectations(double desiredGPA, double acquiredGPA, int upperBoundGradeNeeded,
                                        double upperBound, int lowerBoundGradeNeeded, double lowerBound) {
        String formattedDesiredGPA = String.format("%.02f", desiredGPA);
        String formattedAcquiredGPA = String.format("%.02f", acquiredGPA);
        System.out.println("To obtain desired GPA of: " + formattedDesiredGPA);
        System.out.println("You will need: " + upperBoundGradeNeeded + " " + numberToGrade(upperBound) +
                " and " + lowerBoundGradeNeeded + " " + numberToGrade(lowerBound));
        System.out.println("With the above grades, your end GPA will be: " + formattedAcquiredGPA);
    }

    public double getFutureAverageGradeUpperBound(double requiredFutureAverageGrade) {
        if (requiredFutureAverageGrade > 4.5) {
            return 5.0;
        } else if (requiredFutureAverageGrade > 4.0) {
            return 4.5;
        } else if (requiredFutureAverageGrade > 3.5) {
            return 4.0;
        } else if (requiredFutureAverageGrade > 3.0) {
            return 3.5;
        } else if (requiredFutureAverageGrade > 2.5) {
            return 3.0;
        } else if (requiredFutureAverageGrade > 2.0) {
            return 2.5;
        } else if (requiredFutureAverageGrade > 1.5) {
            return 2.0;
        } else if (requiredFutureAverageGrade > 1.0) {
            return 1.5;
        } else if (requiredFutureAverageGrade > 0) {
            return 1.0;
        }
        return 0;
    }

    public double getFutureAverageGradeLowerBound(double requiredFutureAverageGrade) {
        if (requiredFutureAverageGrade < 1.0) {
            return 0;
        } else if (requiredFutureAverageGrade < 1.5) {
            return 1.0;
        } else if (requiredFutureAverageGrade < 2.0) {
            return 1.5;
        } else if (requiredFutureAverageGrade < 2.5) {
            return 2.0;
        } else if (requiredFutureAverageGrade < 3.0) {
            return 2.5;
        } else if (requiredFutureAverageGrade < 3.5) {
            return 3.0;
        } else if (requiredFutureAverageGrade < 4.0) {
            return 3.5;
        } else if (requiredFutureAverageGrade < 4.5) {
            return 4.0;
        } else if (requiredFutureAverageGrade < 5) {
            return 4.5;
        }
        return 5;
    }

    public String numberToGrade(double grade) {
        if (grade == 5.0) {
            return "A";
        } else if (grade == 4.5) {
            return "A-";
        } else if (grade == 4.0) {
            return "B+";
        } else if (grade == 3.5) {
            return "B";
        } else if (grade == 3.0) {
            return "B-";
        } else if (grade == 2.5) {
            return "C+";
        } else if (grade == 2.0) {
            return "C";
        } else if (grade == 1.5) {
            return "D+";
        } else if (grade == 1.0) {
            return "D";
        } else if (grade == 0) {
            return "F";
        }
        return "non-vaLid number";
    }


    private int getModuleCreditsTaken() {
        int moduleCreditsTaken = 0;
        for (Module module : moduleList) {
            if (module.getModuleStatus() && !module.gradeIsNull()) {
                moduleCreditsTaken += module.getModuleMC();
            }
        }
        return moduleCreditsTaken;
    }
}
