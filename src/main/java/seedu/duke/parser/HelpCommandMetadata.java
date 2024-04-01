package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.HelpCommand;

import java.util.Map;

public class HelpCommandMetadata extends CommandMetadata {
    private static final String HELP_KEYWORD = "help";
    private static final String[] HELP_ARGUMENTS = {};

    public HelpCommandMetadata() {
        super(HELP_KEYWORD, HELP_ARGUMENTS);
    }

    @Override
    protected Command createCommandInstance(Map<String, String> args) {
        return new HelpCommand();
    }
}
