package seedu.duke.command;

import seedu.duke.exceptions.ModuleAlreadyExistException;
import seedu.duke.exceptions.ModuleException;
import seedu.duke.exceptions.ModuleNotFoundException;
import seedu.duke.exceptions.WrongSemesterException;
import seedu.duke.modules.Module;

import java.util.logging.Level;

import static seedu.duke.FAP.LOGGER;
import static seedu.duke.FAP.jsonManager;

public class AddCommand extends Command {

    private String moduleCode;
    private int moduleDate;

    private float moduleMC;
    private boolean gradedGradingBasis = false;

    public AddCommand(String moduleCode, int moduleDate) {
        assert moduleCode != null && !moduleCode.trim().isEmpty() : "Module code cannot be null or empty";
        assert moduleDate > 0 : "Module date must be a positive number";

        try {
            tryAddingModule(moduleCode, moduleDate);
        } catch (ModuleNotFoundException e) {
            LOGGER.log(Level.WARNING, "An error occurred: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        } catch (WrongSemesterException e) {
            LOGGER.log(Level.WARNING, "An error occured: " + e.getMessage());
            System.out.println("An error occured: " + e.getMessage());
        }
    }

    private void tryAddingModule(String moduleCode, int moduleDate) throws ModuleNotFoundException,
            WrongSemesterException {
        // try getting a module to see if it already exists, if it does then throw the exception
        // will go to the catch block if there are no duplicate modules
        try {
            Module moduleToAdd = moduleList.getModule(moduleCode);      // intended unused variable
            throw new ModuleAlreadyExistException("You have already added the module!");
        } catch (ModuleNotFoundException e) {
            boolean moduleInNUS = jsonManager.moduleExist(moduleCode);
            int plannedSem = moduleDate % 2;

            if (plannedSem == 0) {
                plannedSem = 2;
            }

            jsonManager.getModuleInfo(moduleCode);
            boolean correctSemester = jsonManager.correctSemester(plannedSem);

            if (moduleInNUS && correctSemester) {
                this.moduleMC = jsonManager.getModuleMC();
                this.moduleCode = moduleCode;
                this.moduleDate = moduleDate;
                this.gradedGradingBasis = jsonManager.getGradedGradingBasis();
            } else if (!moduleInNUS) {
                throw new ModuleNotFoundException("Module does not exist in NUS!");
            } else if (!correctSemester){
                throw new WrongSemesterException("You can't take this module in this semester! " + "\n" +
                        "Try another one instead!");
            }
        } catch (ModuleAlreadyExistException e) {

            LOGGER.log(Level.WARNING, "An error occurred: " + e.getMessage());
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public void execute(String userInput) {
        if (this.moduleCode == null) {
            return;
        }
        try {
            // Assuming moduleList is a class attribute of Command
            if (moduleList == null) {
                throw new ModuleException("Module list is not initialized.");
            }

            Module newModule = new Module(moduleCode, moduleMC, moduleDate, jsonManager.getModuleTitle(),
                    gradedGradingBasis);
            moduleList.addModule(newModule);
        } catch (ModuleException e) {
            System.err.println("Failed to add module: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
