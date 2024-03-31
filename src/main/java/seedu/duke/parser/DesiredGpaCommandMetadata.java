package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.DesiredGpaCommand;

import java.util.Map;

public class DesiredGpaCommandMetadata extends CommandMetadata{
    private static final String DESIREDGPA_KEYWORD = "desiredgpa";
    private static final String[] DESIREDGPA_ARGUMENTS = {"gpa"};

    public DesiredGpaCommandMetadata() {
        super(DESIREDGPA_KEYWORD, DESIREDGPA_ARGUMENTS);
    }

    @Override
    protected Command createCommandInstance(Map<String, String> args) {
        float gpa = Float.parseFloat(args.get("gpa"));

        return new DesiredGpaCommand(gpa);
    }
}
