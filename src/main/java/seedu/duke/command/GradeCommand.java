package seedu.duke.command;

/**
 * Represents the command to change grade of a module
 */
public class GradeCommand extends Command {
    private final String moduleCode;
    private final String moduleGrade;

    public GradeCommand(String moduleCode, String moduleGrade) {
        this.moduleCode = moduleCode;
        this.moduleGrade = moduleGrade;
    }

    @Override
    public void execute(String userInput) {
        moduleList.changeModuleGrade(moduleCode, moduleGrade);
    }
}
