package seedu.duke.command;

import java.util.ArrayList;
import java.util.Map;

import static seedu.duke.FAP.user;

import seedu.duke.modules.Module;
import seedu.duke.ui.Ui;

public class ViewCommand extends Command {

    private String name = user.getName();
    private int currSem = user.getCurrentSemester();
    private int gradSem = user.getGraduationSemester();
    private Map<Integer, ArrayList<Module>>  modulesBySemMap = moduleList.groupModulesBySemester();

    @Override
    public void execute(String userInput) {
        Ui.printUserInfo(name, currSem, gradSem);
        Ui.printModulePlan(modulesBySemMap);
    }
}
