package seedu.duke.command;

import static seedu.duke.FAP.user;

import seedu.duke.ui.Ui;
import seedu.duke.exceptions.UserException;

public class InitCommand extends Command {
    private final String name;
    private final int currentSem;
    private final int graduationSem;

    public InitCommand(String name, int currSem, int gradSem) {
        this.name = name;
        this.currentSem = currSem;
        this.graduationSem = gradSem;
    }

    @Override
    public void execute(String userInput) {
        try {
            user.setUserInfo(name, currentSem, graduationSem);
            Ui.printUserInfo(name, currentSem, graduationSem);
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
    }
}
