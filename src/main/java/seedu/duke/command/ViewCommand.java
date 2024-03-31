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
    private int takenMCs = moduleList.calculateTakenMCs();
    private int totalMCs = moduleList.calculateTotalMCs();
    private Map<Integer, ArrayList<Module>> modulesBySemMap = moduleList.groupModulesBySemester();

    @Override
    public void execute(String userInput) {
        Ui.printScheduleHeader(name);
        Ui.printModulePlan(modulesBySemMap);
        Ui.printScheduleDetails(currSem, gradSem, takenMCs, totalMCs);
    }
}
