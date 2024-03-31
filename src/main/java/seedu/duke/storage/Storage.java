package seedu.duke.storage;

import seedu.duke.exceptions.ModuleException;
import seedu.duke.exceptions.StorageException;
import seedu.duke.exceptions.UserException;
import seedu.duke.modules.Module;
import seedu.duke.user.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static seedu.duke.FAP.jsonManager;
import static seedu.duke.FAP.moduleList;
import static seedu.duke.FAP.user;

public class Storage {

    public static final String INITIALISED_USER = "InitialisedUser";

    public static void saveModulesToFile(String filePath) throws StorageException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(toString(user) + System.lineSeparator());
            for (Module module : moduleList.getTakenModuleList()) {
                writer.write(toString(module) + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while saving modules to file: " + filePath);
        }
    }

    public static void ensureDirectoryExists(String filePath) throws StorageException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new StorageException("Failed to create directories for path: " + filePath);
        }
    }

    public static void createFile(String filePath) throws StorageException {
        ensureDirectoryExists(filePath);
        File file = new File(filePath);
        try {
            if (!file.createNewFile()) {
                System.out.println("Note: File already exists and will not be overwritten: " + filePath);
            }
        } catch (IOException e) {
            throw new StorageException("An error occurred while creating file: " + filePath);
        }
    }

    public static void loadDataFromFile(String filePath) throws StorageException {
        File file = new File(filePath);
        if (!file.exists()) {
            createFile(filePath);
            return; // Early return if file does not exist
        }
        if (file.isDirectory()) {
            throw new StorageException("Specified path points to a directory, not a file: " + filePath);
        }
        if (!file.canRead()) {
            throw new StorageException("File cannot be read, check permissions: " + filePath);
        }
        try (Scanner input = new Scanner(file)) {
            if (!input.hasNext()) {
                return; // Early return if file is empty
            }
            processFile(input);
        } catch (IOException e) {
            throw new StorageException("Error loading data from file: " + filePath);
        }
    }

    private static void processFile(Scanner input) throws StorageException {
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

    private static boolean processInitialUserLine(String line) throws StorageException {
        if (!line.startsWith(INITIALISED_USER)) {
            return false;
        }
        String[] parts = line.split(" ", 4);
        if (parts.length < 4) {
            throw new StorageException("User data is corrupted.");
        }
        int currentSemester = Integer.parseInt(parts[1]);
        int graduationSemester = Integer.parseInt(parts[2]);
        String name = parts[3];
        try {
            if (!name.isEmpty()) {
                user.setUserInfo(name, currentSemester, graduationSemester);
            }
        }
        catch (NumberFormatException e) {
            throw new StorageException("Failed to parse user semester information: " + e.getMessage());
        }
        catch (UserException e) {
            throw new StorageException("Failed to set user info due to: " + e.getMessage());
        }
        return true;
    }


    private static void processModuleLine(String line) throws StorageException {
        Module module = getModule(line);
        moduleList.add(module);
    }

    private static Module getModule(String line) throws StorageException {
        try {
            String[] parts = line.split(" ", 4);
            String moduleCode = parts[0];
            String moduleGrade = parts[1];
            int moduleDate = Integer.parseInt(parts[2]);
            String moduleStatus = parts[3];
            if (!jsonManager.moduleExist(moduleCode)) {
                throw new StorageException("Module " + moduleCode + " does not exist in NUS.");
            }
            int moduleMC = jsonManager.getModuleMC();
            String moduleDescription = jsonManager.getModuleDescription();
            Module module = new Module(moduleCode, moduleMC, moduleDate, moduleDescription);
            module.setModuleTaken("true".equals(moduleStatus));
            if (!"null".equals(moduleGrade)) {
                module.setModuleGrade(moduleGrade);
            }
            return module;
        } catch (RuntimeException | ModuleException e) {
            throw new StorageException("Error processing module line: " + line + " due to: " + e.getMessage());
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
