package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.ViewCommand;

import java.util.Map;

public class ViewCommandMetadata extends CommandMetadata {
    private static final String VIEW_KEYWORD = "view";
    private static final String[] VIEW_ARGUMENTS = {"courseCode"};
    private static final String[] VIEW_ARG_FLAGS = {"optional"};

    public ViewCommandMetadata() {
        super(VIEW_KEYWORD, VIEW_ARGUMENTS, VIEW_ARG_FLAGS);
    }

    // View Command Creator
    @Override
    protected Command createCommandInstance(Map<String, String> args) {
        return new ViewCommand(args);
    }
}
