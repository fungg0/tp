package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.DesiredGpaCommand;

import java.util.Map;

public class DesiredGpaCommandMetadata extends CommandMetadata{
    private static final String DESIREDGPA_KEYWORD = "desiredgpa";
    private static final String[] DESIREDGPA_ARGUMENTS = {"dgpa"};

    public DesiredGpaCommandMetadata() {
        super(DESIREDGPA_KEYWORD, DESIREDGPA_ARGUMENTS);
    }

    @Override
    protected Command createCommandInstance(Map<String, String> args) {
        float dgpa = Float.parseFloat(args.get("dgpa"));

        return new DesiredGpaCommand(dgpa);
    }
}
