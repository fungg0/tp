package seedu.duke.storage;

import seedu.duke.exceptions.ModuleException;
import seedu.duke.modules.Module;
import static seedu.duke.FAP.jsonManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import static seedu.duke.FAP.moduleList;

public class Storage {

    public static void saveModulesToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
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

    public static void loadModulesFromFile() {
        try {
            String filePath = Paths.get(System.getProperty("user.dir"), "data", "moduleList.txt").toString();
            File file = new File(filePath);
            if (!file.exists()) {
                createFile(filePath);
            }
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                String line = input.nextLine();
                Module module = getModule(line);
                moduleList.add(module);
            }
            input.close();
        } catch (ModuleException | FileNotFoundException e) {
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
                module.setModuleStatus(true);
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

}
