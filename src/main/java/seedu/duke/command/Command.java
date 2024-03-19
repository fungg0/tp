package seedu.duke.command;

import seedu.duke.modules.ModuleList;

public abstract class Command {
    protected ModuleList moduleList;
    public void setData (ModuleList moduleList) {
        this.moduleList = moduleList;
    }
    public abstract void execute(String userInput);
}