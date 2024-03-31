package seedu.duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FAPTest {

    public static Path userInfoPath = Paths.get(System.getProperty("user.dir"),
            "data", "CS2113_AY2324S2_FAP_Storage.txt");
    private String originalUserInfo;
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpStreams() throws IOException {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        if (Files.exists(userInfoPath)) {
            originalUserInfo = Files.readString(userInfoPath);
        }
    }

    @AfterEach
    public void restoreStreams() throws IOException {
        System.setIn(systemIn);
        System.setOut(systemOut);
        if (originalUserInfo != null) {
            Files.writeString(userInfoPath, originalUserInfo, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String buildExpectedOutput(String... lines) {
        return String.join(System.lineSeparator(), lines) + System.lineSeparator();
    }

    @Test
    public void testInit() {
        String simulatedUserInput = "init n/bob curr/4 grad/8" + System.lineSeparator() +
                "bye" + System.lineSeparator();
        provideInput(simulatedUserInput);

        FAP.main(new String[]{});

        String output = testOut.toString().replace(System.lineSeparator(), "\n");

        String expectedOutput = buildExpectedOutput(
                "__________________________________________________",
                "Hello bob!",
                "You are currently in Semester 4",
                "and expected to graduate in Semester 8",
                "__________________________________________________",
                "__________________________________________________",
                "Bye. Enjoy your studies!"
        ).replace(System.lineSeparator(), "\n");

        assertTrue(output.contains(expectedOutput));
    }

    @Test
    public void invalidInitTest() {
        String simulatedUserInput = "init n/" + System.lineSeparator() +
                "bye" + System.lineSeparator();
        provideInput(simulatedUserInput);
        FAP.main(new String[]{});
        String output = testOut.toString().replace(System.lineSeparator(), "\n");

        String expectedOutput = buildExpectedOutput(
                "__________________________________________________",
                "init command: Invalid argument format/delimiters used",
                "__________________________________________________",
                "__________________________________________________",
                "Bye. Enjoy your studies!"
        ).replace(System.lineSeparator(), "\n");

        assertTrue(output.contains(expectedOutput));
    }
}
