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

import static seedu.duke.FAP.*;

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

    public static void loadDataFromFile() {
        try {
            String filePath = Paths.get(System.getProperty("user.dir"), "data", "moduleList.txt").toString();
            File file = new File(filePath);
            if (!file.exists()) {
                createFile(filePath);
            }
            Scanner input = new Scanner(file);
            if (!input.hasNext()) {
                input.close();
                return;
            }
            String line = input.nextLine();
            if (line.startsWith(INITIALISED_USER)) {
                String[] parts = line.split(" ", 4);
                int currentSemester = Integer.parseInt(parts[1]);
                int graduationSemester = Integer.parseInt(parts[2]);
                String name = parts[3];
                if (!name.isEmpty()) {
                    user.setUserInfo(name, currentSemester, graduationSemester);
                }
            } else {
                Module module = getModule(line);
                moduleList.add(module);
            }
            while (input.hasNext()) {
                line = input.nextLine();
                Module module = getModule(line);
                moduleList.add(module);
            }
            input.close();
        } catch (ModuleException | FileNotFoundException | UserException e) {
            System.out.println("An error occurred while loading modules from file: " + e.getMessage());
        }
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
                throw new ModuleException("Module does not exist in NUS.");
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
