package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.SetCommand;

import java.util.Map;

public class SetCommandMetadata extends CommandMetadata {
    private static final String INIT_KEYWORD = "set";
    private static final String[] INIT_ARGUMENTS = {"name", "currentSem"};

    public SetCommandMetadata() {
        super(INIT_KEYWORD, INIT_ARGUMENTS);
    }

    // Init Command Creator
    @Override
    protected Command createCommandInstance(Map<String, String> args) {
        String name = args.getOrDefault("name", "NAME_ERROR");
        int currSem = Integer.parseInt(args.get("currentSem"));

        return new SetCommand(name, currSem);
    }
}
