package seedu.duke.command;

import static seedu.duke.FAP.user;

import seedu.duke.ui.Ui;
import seedu.duke.exceptions.UserException;

public class SetCommand extends Command {
    private final String name;
    private final int currentSem;

    public SetCommand(String name, int currSem) {
        this.name = name;
        this.currentSem = currSem;
    }

    @Override
    public void execute(String userInput) {
        try {
            user.setUserInfo(name, currentSem);
            Ui.printUserInfo(name, currentSem);
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
    }
}
