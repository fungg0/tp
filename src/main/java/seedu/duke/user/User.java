package seedu.duke.user;

import seedu.duke.FAP;
import seedu.duke.modules.Module;
import seedu.duke.exceptions.UserException;

import java.util.ArrayList;

public class User {
    public static final int MIN_NUM_SEMESTERS = 1;
    public static final int MAX_NUM_SEMESTERS = 8;

    // Default parameters: name is empty string, currentSemester = 1, graduationSemester = 8
    private String name;
    private int currentSemester;
    private int graduationSemester;

    public User() {
        name = "";
        currentSemester = 1;
        graduationSemester = 8;
    }

    public String getName() {
        return name;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public int getGraduationSemester() {
        return graduationSemester;
    }

    public void setUserInfo(String name, int currSem, int gradSem) throws UserException {
        validateName(name);
        validateSemesters(currSem, gradSem);

        resetModuleStatuses(currSem);

        this.name = name;
        this.currentSemester = currSem;
        this.graduationSemester = gradSem;
    }

    private void validateName(String name) throws UserException {
        if (name == null) {
            throw new UserException("Name cannot be null.");
        }
        if (!name.matches("[A-Za-z0-9 ]+")) {
            throw new UserException("Invalid name format. Name should only contain letters, digits, or spaces.");
        }
    }

    private void validateSemesters(int currSem, int gradSem) throws UserException {
        if (currSem < MIN_NUM_SEMESTERS || currSem > MAX_NUM_SEMESTERS ||
                gradSem < MIN_NUM_SEMESTERS || gradSem > MAX_NUM_SEMESTERS) {
            throw new UserException("Semester values should be between 1 and 8.");
        }
        if (gradSem < currSem) {
            throw new UserException("Invalid semesters: graduation semester should not precede the current semester.");
        }
    }

    public void resetModuleStatuses(int currSem) {
        assert FAP.moduleList != null : "moduleList in FAP should be initialised";
        ArrayList<Module> moduleList = FAP.moduleList.getTakenModuleList();

        for (Module module : moduleList) {
            if (module.getModuleDate() > currSem) {
                module.setModuleTaken(false);
                module.setModuleGradeNull();
            } else {
                module.setModuleTaken(true);
            }
        }
    }

    public void resetModuleStatuses() {
        int currentSemester = getCurrentSemester();
        resetModuleStatuses(currentSemester);
    }
}
