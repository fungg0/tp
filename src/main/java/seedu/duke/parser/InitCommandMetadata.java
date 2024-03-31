package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.InitCommand;

import java.util.Map;

public class InitCommandMetadata extends CommandMetadata {
    private static final String INIT_KEYWORD = "init";
    private static final String[] INIT_ARGUMENTS = {"name", "currentSem", "graduationSem"};

    public InitCommandMetadata() {
        super(INIT_KEYWORD, INIT_ARGUMENTS);
    }

    // Init Command Creator
    @Override
    protected Command createCommandInstance(Map<String, String> args) {
        String name = args.getOrDefault("name", "NAME_ERROR");
        int currSem = Integer.parseInt(args.get("currentSem"));
        int gradSem = Integer.parseInt(args.get("graduationSem"));

        return new InitCommand(name, currSem, gradSem);
    }
}
