package seedu.duke.command;

import java.util.ArrayList;
import java.util.Map;

import static seedu.duke.FAP.jsonManager;
import static seedu.duke.FAP.user;

import seedu.duke.modules.Module;
import seedu.duke.ui.Ui;

public class ViewCommand extends Command {

    private String name = user.getName();
    private int currSem = user.getCurrentSemester();
    private float takenMCs = moduleList.calculateTakenMCs();
    private float totalMCs = moduleList.calculateTotalMCs();
    private Map<Integer, ArrayList<Module>> modulesBySemMap = moduleList.groupModulesBySemester();
    private final Map<String, String> args;

    public ViewCommand(Map<String, String> args) {
        this.args = args;
    }

    @Override
    public void execute(String userInput) {
        if (args.containsKey("courseCode")) {
            if (jsonManager.moduleExist(args.get("courseCode"))) {
                Map<String, String> moduleInfo = jsonManager.queryModuleInfo(args.get("courseCode"));
                String moduleTitle = moduleInfo.get("moduleTitle");
                String moduleMC = moduleInfo.get("moduleMC");
                String moduleDescription = moduleInfo.get("moduleDescription");
                Ui.printViewModule(moduleTitle, moduleMC, moduleDescription);
            } else {
                System.out.println("No such module found in NUS AY23-24!");
            }
        } else {
            Ui.printScheduleHeader(name);
            Ui.printModulePlan(modulesBySemMap);
            Ui.printScheduleDetails(currSem, takenMCs, totalMCs);
        }
    }
}
