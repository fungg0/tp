package seedu.duke.command;

import seedu.duke.ui.Ui;

public class HelpCommand extends Command {
    @Override
    public void execute(String userInput) {
        Ui.printCommandGuide();
    }
}
