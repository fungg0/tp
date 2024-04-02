package seedu.duke.command;

import static seedu.duke.ui.Ui.printExit;

public class ByeCommand extends Command {
    @Override
    public void execute(String userInput) {
        try {
            printExit();
        } catch (Exception e) {
            System.err.println("An error occurred while attempting to exit: " + e.getMessage());
        }
    }
}
