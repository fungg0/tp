package seedu.duke.modules;

import seedu.duke.exceptions.ModuleException;

public class Module {
    private String moduleCode;
    private String moduleGrade;
    private float moduleMC;
    private boolean moduleTaken;
    private int moduleDate;
    private boolean gradedGradingBasis;

    private String moduleDescription;

    public Module(String moduleCode, float moduleMC, int moduleDate, String moduleDescription,
                  boolean gradedGradingBasis) {
        this.moduleCode = moduleCode;
        this.moduleMC = moduleMC;
        this.moduleDate = moduleDate;
        this.moduleTaken = false;
        this.moduleGrade = null;
        this.moduleDescription = moduleDescription;
        this.gradedGradingBasis = gradedGradingBasis;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        if (moduleCode == null || moduleCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Module code cannot be null or empty.");
        }
        this.moduleCode = moduleCode;
    }

    /**
     * Gets the grade of the module.
     *
     * @return The grade of the module.
     */
    public String getModuleGrade() {
        return moduleGrade;
    }

    /**
     * Sets the grade of the module. Throws an exception if moduleGrade is an invalid string, if a Graded mod is
     * assigned with "CS", or if a CS/CU mod is assigned with a letter grade.
     *
     * @param moduleGrade The grade to set for the module.
     * @throws ModuleException If the module grade is invalid or does not fit the module.
     */
    public void setModuleGrade(String moduleGrade) throws ModuleException {

        if (moduleGrade == null || !moduleGrade.matches("[AB][+-]?|[CD][+]?|F|CS|S")) {
            throw new IllegalArgumentException("Invalid module grade.");
        }
        if (!moduleTaken) {
            throw new ModuleException("Module needs to be taken before its grade can be updated.");
        }
        if(!gradedGradingBasis && !moduleGrade.matches("CS")) {
            throw new ModuleException("This module is CS/CU. Please only input \"CS\" grade for this module");
        }
        if(gradedGradingBasis && moduleGrade.matches("CS")) {
            throw new ModuleException("This module is graded. You cannot input \"CS\" grade for this module.");
        }
        this.moduleGrade = moduleGrade;
    }

    public void setModuleGradeNull() {
        this.moduleGrade = null;
    }

    public float getModuleMC() {
        return moduleMC;
    }

    public void setModuleMC(int moduleMC) {
        if (moduleMC <= 0) {
            throw new IllegalArgumentException("Module MC (Modular Credits) must be positive.");
        }
        this.moduleMC = moduleMC;
    }

    public boolean getModuleStatus() {
        return moduleTaken;
    }

    public void setModuleTaken(boolean moduleTaken) {
        this.moduleTaken = moduleTaken;
    }

    public int getModuleDate() {
        return moduleDate;
    }

    public void setModuleDate(int moduleDate) {
        if (moduleDate <= 0) {
            throw new IllegalArgumentException("Module date must be a positive number.");
        }
        this.moduleDate = moduleDate;
    }

    /**
     * Returns the numerical value of the grade based on the module grade.
     *
     * @return The numerical value of the grade.
     * @throws IllegalStateException If the module grade is invalid or unassigned.
     */
    public double getGradeNumber () {
        switch (moduleGrade) {
        case "A+":
            //fall through
        case "A":
            return 5.0;
        case "A-":
            return 4.5;
        case "B+":
            return 4.0;
        case "B":
            return 3.5;
        case "B-":
            return 3.0;
        case "C+":
            return 2.5;
        case "C":
            return 2.0;
        case "D+":
            return 1.5;
        case "D":
            return 1.0;
        case "F":
            return 0;
        default:
            throw new IllegalStateException("Invalid or unassigned module grade.");
        }
    }

    /**
     * Checks if the module has been assigned a grade, which is no if module grade is null.
     *
     * @return true if the module grade is null, false otherwise.
     */
    public boolean gradeIsNull() {
        return moduleGrade == null;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleCode='" + moduleCode + '\'' +
                ", moduleGrade='" + moduleGrade + '\'' +
                ", moduleMC='" + moduleMC + '\'' +
                '}';
    }
}
