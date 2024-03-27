package seedu.duke.storage;

import seedu.duke.exceptions.ModuleException;
import seedu.duke.modules.Module;

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
                moduleList.addModule(module);
            }
            input.close();
        } catch (ModuleException | FileNotFoundException e) {
            System.out.println("An error occurred while loading modules from file: " + e.getMessage());
        }
    }

    private static Module getModule(String line) throws ModuleException {
        try {
            String[] parts = line.split(" ", 5);
            String moduleCode = parts[0];
            int moduleMC = Integer.parseInt(parts[1]);
            String moduleGrade = parts[2];
            int moduleDate = Integer.parseInt(parts[3]);
            String moduleDescription = parts[4];
            Module module = new Module(moduleCode, moduleMC, moduleDate, moduleDescription);
            module.setModuleGrade(moduleGrade);
            return module;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ModuleException("Module data is corrupted.");
        }
    }

    public static String toString(Module module) {
        return module.getModuleCode() + ' ' +
                module.getModuleGrade() + ' ' +
                module.getModuleMC() + ' ' +
                module.getModuleDate() + ' ' +
                module.getModuleDescription();
    }

}
