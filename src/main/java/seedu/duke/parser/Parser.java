package seedu.duke.parser;

import seedu.duke.exceptions.InvalidGpaException;
import seedu.duke.exceptions.ParserException;
import seedu.duke.command.Command;
import seedu.duke.command.InvalidCommand;

import java.util.Map;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Parser {

    private static ArrayList<CommandMetadata> metadataList = new ArrayList<>();
    static {
        metadataList.add(new InitCommandMetadata());
        metadataList.add(new GpaCommandMetadata());
        metadataList.add(new ViewCommandMetadata());
        metadataList.add(new ViewGraduateCommandMetadata());
        metadataList.add(new AddCommandMetadata());
        metadataList.add(new RemoveCommandMetadata());
        metadataList.add(new GradeCommandMetadata());
        metadataList.add(new ByeCommandMetadata());
        metadataList.add(new DesiredGpaCommandMetadata());
    }

    public static Command getCommand(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return new InvalidCommand();
        }

        userInput = userInput.trim();

        for (CommandMetadata commandMetadata : metadataList) {
            if (!commandMetadata.matchesKeyword(userInput)) {
                continue; // Skip metadata if keyword doesn't match
            }
            try {
                Matcher matcher = commandMetadata.getPattern().matcher(userInput);
                if (matcher.matches()) {
                    Map<String, String> commandArguments = commandMetadata.getCommandArguments(matcher);
                    Command commandInstance = commandMetadata.createCommandInstance(commandArguments);
                    return commandInstance;
                }
                commandMetadata.validateUserInput(userInput);
            } catch (ParserException e) {
                return new InvalidCommand(e.getMessage());
            }
        }
        return new InvalidCommand();
    }

    public static void validateGPA(double GPA) throws InvalidGpaException {
        if (GPA > 5 || GPA <0) {
            throw new InvalidGpaException("GPA must be within 0 to 5.");
        }
    }
}
