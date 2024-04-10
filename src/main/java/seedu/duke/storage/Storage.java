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

/**
 * Handles the storage of user and module data to and from files.
 * This includes saving the current user's information and their module list
 * to a file and loading this information back from the file.
 */
public class Storage {

    public static final String INITIALISED_USER = "InitialisedUser";
    public static final String EMPTY_STR = "";
    private static final int MINIMUM_SEMESTER = 1;
    private static final int MAXIMUM_SEMESTER = 8;

    /**
     * Saves the current user and their taken modules to the specified file path.
     *
     * @param filePath The path of the file to save the data to.
     * @throws StorageException If an error occurs while writing to the file.
     */
    public static void saveModulesToFile(String filePath) throws StorageException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(toString(user) + System.lineSeparator());
            for (Module module : moduleList.getTakenModuleList()) {
                writer.write(toString(module) + System.lineSeparator());
            }
        } catch (IOException | SecurityException e) {
            throw new StorageException("An error occurred while saving modules to file: " + filePath);
        }
    }

    /**
     * Ensures that the directory for the specified file path exists. If it does not,
     * attempts to create the directory.
     *
     * @param filePath The file path for which to ensure the directory exists.
     * @throws StorageException If the directory does not exist and cannot be created.
     */
    public static void ensureDirectoryExists(String filePath) throws StorageException {

        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new StorageException("Failed to create directories for path: " + filePath +
                    ". Check permissions and disk space.");
        }
    }

    /**
     * Creates a new file at the specified file path. If the file already exists,
     * it will not be overwritten.
     *
     * @param filePath The path of the file to create.
     * @throws StorageException If an error occurs while creating the file.
     */
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

    /**
     * Loads user and module data from the specified file path.
     *
     * @param filePath The path of the file from which to load the data.
     * @throws StorageException If an error occurs during file loading.
     */
    public static void loadDataFromFile(String filePath) throws StorageException {

        File file = new File(filePath);
        if (!file.exists()) {
            createFile(filePath);
            return; // Early return if file does not exist
        }
        handleLoadFileException(filePath, file);
        try (Scanner input = new Scanner(file)) {
            if (!input.hasNextLine()) {
                return; // Early return if file is empty
            }
            processFile(input);
        } catch (IOException | StorageException e) {
            wipeFileClean(filePath);
            throw new StorageException("Error loading data from file: " + filePath +
                    e.getMessage() + " File has been wiped clean.");
        }
    }

    private static void handleLoadFileException(String filePath, File file) throws StorageException {

        if (file.isDirectory()) {
            throw new StorageException("Specified path points to a directory, not a file: " + filePath);
        }
        if (!file.canRead()) {
            throw new StorageException("File cannot be read, check permissions: " + filePath);
        }
    }

    private static void wipeFileClean(String filePath) throws StorageException {

        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write(EMPTY_STR); // Writing an empty string to overwrite the file content.
        } catch (IOException e) {
            throw new StorageException("Failed to wipe file clean: " + filePath);
        }
    }

    private static void processFile(Scanner input) throws StorageException {

        boolean isUserInitialised = false;
        try {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (!isUserInitialised) {
                    isUserInitialised = processInitialUserLine(line);
                    continue;
                }
                processModuleLine(line);
            }
        } catch (StorageException e) {
            throw new StorageException("Error processing file: " + e.getMessage());
        }
    }

    private static boolean processInitialUserLine(String line) throws StorageException {

        if (!line.startsWith(INITIALISED_USER)) {
            return false;
        }
        String[] parts = line.split(" ", 3);
        if (parts.length < 3) {
            throw new StorageException("User data is corrupted.");
        }
        int currentSemester = Integer.parseInt(parts[1]);
        String name = parts[2];
        try {
            if (!name.isEmpty()) {
                user.setUserInfo(name, currentSemester);
            }
        } catch (NumberFormatException e) {
            throw new StorageException("Failed to parse user semester information: " + e.getMessage());
        } catch (UserException e) {
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

            handleModuleException(moduleCode, moduleDate, moduleStatus, moduleGrade);
            jsonManager.getModuleInfo(moduleCode);
            int moduleMC = jsonManager.getModuleMC();
            String moduleDescription = jsonManager.getModuleDescription();

            Module module = new Module(moduleCode, moduleMC, moduleDate, moduleDescription);
            module.setModuleTaken("true".equals(moduleStatus));
            if (!moduleGrade.equals("null")) {
                module.setModuleGrade(moduleGrade);
            }
            return module;
        } catch (RuntimeException | ModuleException | StorageException e) {
            throw new StorageException("Error processing module line: " + line + System.lineSeparator() +
                    "This is due to: " + e.getMessage());
        }
    }

    private static void handleModuleException(String moduleCode, int moduleDate,
                                              String moduleStatus, String moduleGrade) throws StorageException {

        if (!jsonManager.moduleExist(moduleCode)) {
            throw new StorageException("Module " + moduleCode + " does not exist in NUS.");
        }
        if (moduleDate < MINIMUM_SEMESTER || moduleDate > MAXIMUM_SEMESTER) {
            throw new StorageException("Invalid semester date for module " + moduleCode + ": " + moduleDate);
        }
        if (!moduleStatus.equals("true") && !moduleStatus.equals("false")) {
            throw new StorageException("Invalid module status for module " + moduleCode + ": " + moduleStatus);
        }
        if (!moduleGrade.equals("null") && !moduleGrade.matches("[AB][+-]?|[CD][+]?|F|CS")) {
            throw new StorageException("Invalid module grade for module " + moduleCode + ": " + moduleGrade);
        }
    }

    /**
     * Converts a module to its string representation for storage.
     *
     * @param module The module to convert.
     * @return The string representation of the module.
     */
    public static String toString(Module module) {

        return module.getModuleCode() + ' ' +
                module.getModuleGrade() + ' ' +
                module.getModuleDate() + ' ' +
                module.getModuleStatus();
    }

    /**
     * Converts a user to its string representation for storage.
     *
     * @param user The user to convert.
     * @return The string representation of the user.
     */
    public static String toString(User user) {

        return INITIALISED_USER + ' ' +
                user.getCurrentSemester() + ' ' +
                user.getName();
    }
}
