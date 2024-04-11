package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.enums.CEGModules;
import seedu.duke.modules.Module;
import seedu.duke.modules.ModuleList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewGraduateCommandTest {
    private final ModuleList moduleList = new ModuleList();
    private final ArrayList<String> graduateModuleList = new ArrayList<>();

    @BeforeEach
    public void populate() {
        for (CEGModules cegModules : CEGModules.values()) {
            graduateModuleList.add(cegModules.name());
        }
    }

    @Test
    public void viewGraduateCommandCEGModulesTest() {
        for (CEGModules cegModules : CEGModules.values()) {
            boolean isInternship = cegModules.name().equals("EG3611A") || cegModules.name().equals("CP3880");
            if (!isInternship) {
                moduleList.addModule(new Module(cegModules.name(), 4, 1, "", true));
                graduateModuleList.remove(cegModules.name());
                assertEquals(graduateModuleList, moduleList.getModulesToComplete());
            }
        }
    }

    @Test
    public void viewGraduateCommandNonCEGModulesTest() {
        moduleList.addModule(new Module("AC5001", 4, 1, "", true));
        moduleList.addModule(new Module("GEC1005", 4, 1, "", true));
        moduleList.addModule(new Module("MA2101", 4, 1, "", true));
        moduleList.addModule(new Module("LC6009GRSII", 4, 1, "", false));
        assertEquals(graduateModuleList, moduleList.getModulesToComplete());
    }

    @Test
    public void viewGraduateCommandEquivalentModulesTest1() {
        moduleList.addModule(new Module("EG3611A", 10, 1, "", true));
        graduateModuleList.remove("EG3611A");
        graduateModuleList.remove("CP3880");
        assertEquals(graduateModuleList, moduleList.getModulesToComplete());
    }

    @Test
    public void viewGraduateCommandEquivalentModulesTest2() {
        moduleList.addModule(new Module("CP3880", 12, 1, "", true));
        graduateModuleList.remove("EG3611A");
        graduateModuleList.remove("CP3880");
        assertEquals(graduateModuleList, moduleList.getModulesToComplete());
    }
}
