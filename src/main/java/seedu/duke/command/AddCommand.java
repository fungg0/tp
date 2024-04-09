package seedu.duke.command;

import seedu.duke.exceptions.ModuleAlreadyExistException;
import seedu.duke.exceptions.ModuleException;
import seedu.duke.exceptions.ModuleNotFoundException;
import seedu.duke.modules.Module;

import java.util.logging.Level;

import static seedu.duke.FAP.LOGGER;
import static seedu.duke.FAP.jsonManager;

public class AddCommand extends Command {
    private String moduleCode;
    private int moduleDate;

    private int moduleMC;

    public AddCommand(String moduleCode, int moduleDate) {
        assert moduleCode != null && !moduleCode.trim().isEmpty() : "Module code cannot be null or empty";
        assert moduleDate > 0 : "Module date must be a positive number";

        try {
            TryAddingModule(moduleCode, moduleDate);
        } catch (ModuleNotFoundException e) {
            LOGGER.log(Level.WARNING, "An error occurred: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void TryAddingModule(String moduleCode, int moduleDate) throws ModuleNotFoundException {
        // try getting a module to see if it already exists, if i   t does then throw the exception
        // will go to the catch block if there are no duplicate modules
        try {
            Module moduleToAdd = moduleList.getModule(moduleCode);
            throw new ModuleAlreadyExistException("You have already added the module!");
        } catch (ModuleNotFoundException e) {
            if (jsonManager.moduleExist(moduleCode)) {
                jsonManager.getModuleInfo(moduleCode);
                this.moduleMC = jsonManager.getModuleMC();
                this.moduleCode = moduleCode;
                this.moduleDate = moduleDate;
            } else {
                throw new ModuleNotFoundException("Module does not exist in NUS!");
            }
        } catch (ModuleAlreadyExistException e) {
            LOGGER.log(Level.WARNING, "An error occurred: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //to not throw error
    public AddCommand() {

    }

    @Override
    public void execute(String userInput) {
        try {
            // Assuming moduleList is a class attribute of Command
            if (moduleList == null) {
                throw new ModuleException("Module list is not initialized.");
            }

            Module newModule = new Module(moduleCode, moduleMC, moduleDate, jsonManager.getModuleTitle());
            moduleList.addModule(newModule);
        } catch (ModuleException e) {
            System.err.println("Failed to add module: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
