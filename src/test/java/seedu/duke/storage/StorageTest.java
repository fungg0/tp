package seedu.duke.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.ModuleNotFoundException;
import seedu.duke.exceptions.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.duke.FAP.moduleList;

class StorageTest {

    private static final Path TEST_DATA_FILE = Paths.get(System.getProperty("user.dir"),
            "data", "CS2113_AY2324S2_FAP_Storage_Test.txt");

    @BeforeEach
    void setUp() throws Exception {
        // Ensure the directory exists
        if (!Files.exists(TEST_DATA_FILE.getParent())) {
            Files.createDirectories(TEST_DATA_FILE.getParent());
        }
        // Create or clear the file
        Files.write(TEST_DATA_FILE, new byte[0]);
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.delete(TEST_DATA_FILE);
        moduleList.clearModules();
    }

    @Test
    void storageExists() {
        assertTrue(Files.exists(TEST_DATA_FILE));
    }

    @Test
    void ensureDirectoryExists_validPath_success() {
        try {
            Storage.ensureDirectoryExists(TEST_DATA_FILE.toString());
            assertTrue(Files.exists(TEST_DATA_FILE.getParent()));
        } catch (StorageException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void createFile_validPath_success() {
        try {
            Storage.createFile(TEST_DATA_FILE.toString());
            assertTrue(Files.exists(TEST_DATA_FILE));
        } catch (StorageException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void loadDataFromFile_validFile_success() {
        try {
            List<String> lines = new ArrayList<>();
            lines.add("InitialisedUser 1 8 ");
            lines.add("CS2113 null 2 false");
            lines.add("CS1010 null 2 false");
            Files.write(TEST_DATA_FILE, lines);

            Storage.loadDataFromFile(TEST_DATA_FILE.toString());

            assertEquals(2, moduleList.getTakenModuleList().size());
            assertEquals(4, moduleList.getModule("CS2113").getModuleMC());
            assertEquals(4, moduleList.getModule("CS1010").getModuleMC());
        } catch (StorageException | IOException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        } catch (ModuleNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadDataFromFile_emptyFile_success() {
        try {
            List<String> lines = new ArrayList<>();
            Files.write(TEST_DATA_FILE, lines);

            Storage.loadDataFromFile(TEST_DATA_FILE.toString());

            assertEquals(0, moduleList.getTakenModuleList().size());
        } catch (StorageException | IOException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void loadDataFromFile_invalidModuleData_exceptionThrown() {
        try {
            List<String> lines = new ArrayList<>();
            lines.add("InitialisedUser 1 8 ");
            lines.add("Invalid data");
            Files.write(TEST_DATA_FILE, lines);

            Storage.loadDataFromFile(TEST_DATA_FILE.toString());
            fail("Expected exception to be thrown");
        } catch (StorageException | IOException e) {
            // Expected exception
        }
    }

}
