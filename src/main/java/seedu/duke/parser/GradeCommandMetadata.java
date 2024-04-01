package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.GradeCommand;
import seedu.duke.exceptions.InvalidGpaException;

import java.util.Map;

public class GradeCommandMetadata extends CommandMetadata {
    private static final String GRADE_KEYWORD = "grade";
    private static final String[] GRADE_ARGUMENTS = {"courseCode", "grade"};

    public GradeCommandMetadata() {
        super(GRADE_KEYWORD, GRADE_ARGUMENTS);
    }

    public static void validateGPA(double gpa) throws InvalidGpaException {
        if (gpa > 5 || gpa <0) {
            throw new InvalidGpaException("GPA must be within 0 to 5.");
        }
    }

    // Grade Command Creator
    @Override
    protected Command createCommandInstance(Map<String, String> args) {
        String moduleCode = args.getOrDefault("courseCode", "COURSECODE_ERROR");
        String grade = args.getOrDefault("grade", "GRADE_ERROR");

        return new GradeCommand(moduleCode, grade);
    }
}
