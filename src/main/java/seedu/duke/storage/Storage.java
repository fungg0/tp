package seedu.duke.storage;

import seedu.duke.exceptions.ModuleException;
import seedu.duke.exceptions.UserException;
import seedu.duke.modules.Module;
import seedu.duke.user.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import static seedu.duke.FAP.jsonManager;
import static seedu.duke.FAP.moduleList;
import static seedu.duke.FAP.user;

public class Storage {

    public static final String INITIALISED_USER = "InitialisedUser";

    public static void saveModulesToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(toString(user) + System.lineSeparator());
            for (Module module : moduleList.getTakenModuleList()) {
                writer.write(toString(module) + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving modules to file: " + e.getMessage());
        }
    }

    public static void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    public static void createFile(String filePath) {
        ensureDirectoryExists(filePath);
        try {
            FileWriter file = new FileWriter(filePath);
            file.close();
        } catch (IOException e) {
            System.out.println("An error occurred while creating file: " + e.getMessage());
        }
    }

    public static void loadDataFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            createFile(filePath);
            return; // Early return if file does not exist
        }

        try (Scanner input = new Scanner(file)) {
            if (!input.hasNext()) {
                return; // Early return if file is empty
            }
            processFile(input);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (ModuleException | UserException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private static void processFile(Scanner input) throws ModuleException, UserException {
        boolean isUserInitialised = false;
        while (input.hasNext()) {
            String line = input.nextLine();
            if (!isUserInitialised) {
                isUserInitialised = processInitialUserLine(line);
                continue;
            }
            processModuleLine(line);
        }
    }

    private static boolean processInitialUserLine(String line) throws UserException, ModuleException {
        if (line.startsWith(INITIALISED_USER)) {
            String[] parts = line.split(" ", 4);
            if (parts.length < 4) {
                throw new UserException("User data is corrupted.");
            }
            int currentSemester = Integer.parseInt(parts[1]);
            int graduationSemester = Integer.parseInt(parts[2]);
            String name = parts[3];
            if (!name.isEmpty()) {
                user.setUserInfo(name, currentSemester, graduationSemester);
            }
            return true;
        }
        processModuleLine(line); // Process this line as module data if it does not start with INITIALISED_USER
        return false;
    }

    private static void processModuleLine(String line) throws ModuleException {
        Module module = getModule(line);
        moduleList.add(module);
    }


    private static Module getModule(String line) throws ModuleException {
        try {
            String[] parts = line.split(" ", 4);
            String moduleCode = parts[0];
            String moduleGrade = parts[1];
            int moduleDate = Integer.parseInt(parts[2]);
            String moduleStatus = parts[3];
            if (jsonManager.moduleExist(moduleCode)) {
                jsonManager.getModuleInfo(moduleCode);
            } else {
                throw new ModuleException("Module" + moduleCode + " does not exist in NUS.");
            }
            int moduleMC = jsonManager.getModuleMC();
            String moduleDescription = jsonManager.getModuleDescription();
            Module module = new Module(moduleCode, moduleMC, moduleDate, moduleDescription);
            if (moduleStatus.equals("true")) {
                module.setModuleTaken(true);
            }
            if (!moduleGrade.equals("null")) {
                module.setModuleGrade(moduleGrade);
            }
            return module;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ModuleException("Module data is corrupted.");
        }
    }

    public static String toString(Module module) {
        return module.getModuleCode() + ' ' +
                module.getModuleGrade() + ' ' +
                module.getModuleDate() + ' ' +
                module.getModuleStatus();
    }

    public static String toString(User user) {
        return INITIALISED_USER + ' ' +
                user.getCurrentSemester() + ' ' +
                user.getGraduationSemester() + ' ' +
                user.getName();
    }

}
