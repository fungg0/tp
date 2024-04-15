package seedu.duke.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Manages the JSON operations related to module data, such as checking module existence,
 * and retrieving module information.
 */
public class JsonManager {

    InputStream inputStream;
    Gson gson;
    InputStreamReader reader;
    List<JsonObject> jsonArray;

    String moduleDescription;

    float moduleMC;

    String moduleTitle;

    ArrayList<Integer> moduleSemester;
    boolean gradedGradingBasis = false;

    /**
     * Initializes JsonManager with module data loaded from a JSON file.
     */
    public JsonManager() {

        this.inputStream = this.getClass().getResourceAsStream("/moduleInfo.json");
        if (inputStream == null) {
            throw new RuntimeException("Cannot find resource file");
        }

        this.gson = new Gson();

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            Type type = new TypeToken<List<JsonObject>>() {
            }.getType();
            jsonArray = gson.fromJson(reader, type);
            this.reader = reader;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a module can be taken in a specified semester.
     *
     * @param intendedSem The semester to check.
     * @return true if the semester is valid for the module, false otherwise.
     */
    public boolean correctSemester(int intendedSem) {
        try {
            return moduleSemester.contains(intendedSem);
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Returns whether the module uses a graded grading basis.
     *
     * @return true if graded, false otherwise.
     */
    public boolean getGradedGradingBasis() {
        return gradedGradingBasis;
    }

    /**
     * Checks if the specified module exists in the JSON data.
     *
     * @param moduleCode The code of the module to check.
     * @return true if the module exists, false otherwise.
     */
    public boolean moduleExist(String moduleCode) {
        for (JsonObject obj : jsonArray) {
            String name = obj.get("moduleCode").getAsString();
            if (name.equals(moduleCode)) {
                JsonElement semesterData = obj.get("semesterData");
                JsonArray semesterArray = semesterData.getAsJsonArray();
                // JsonFile contains mods that do not have data on available semester to be taken in.
                // So they are considered as not available just like in NusMods
                if (semesterArray.isEmpty()) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves and stores detailed information about a module from JSON.
     *
     * @param moduleCode The code of the module for which information is retrieved.
     */
    public void getModuleInfo(String moduleCode) {
        for (JsonObject obj : jsonArray) {
            String name = obj.get("moduleCode").getAsString();
            if (name.equals(moduleCode)) {
                moduleSemester = new ArrayList<>();
                this.moduleMC = obj.get("moduleCredit").getAsFloat();
                this.moduleDescription = obj.get("description").getAsString();
                this.moduleTitle = obj.get("title").getAsString();
                this.gradedGradingBasis = obj.get("gradingBasisDescription").getAsString().equals("Graded");
                JsonElement semesterData = obj.get("semesterData");

                JsonArray semesterArray = semesterData.getAsJsonArray();
                for (JsonElement itemElement : semesterArray) {
                    JsonObject item = itemElement.getAsJsonObject();
                    int semester = item.get("semester").getAsInt();
                    moduleSemester.add(semester);
                }
                return;
            }
        }
    }

    /**
     * Queries and returns detailed information about a module.
     *
     * @param moduleCode The module code to query.
     * @return A map of module attributes like title and credits.
     */
    public Map<String, String> queryModuleInfo(String moduleCode) {
        Map<String, String> moduleInfo = new HashMap<>();
        for (JsonObject obj : jsonArray) {
            String name = obj.get("moduleCode").getAsString();
            if (name.equals(moduleCode)) {
                moduleInfo.put("moduleTitle", obj.get("title").getAsString());
                moduleInfo.put("moduleMC", obj.get("moduleCredit").getAsString());
                moduleInfo.put("moduleDescription", obj.get("description").getAsString());
            }
        }
        return moduleInfo;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public float getModuleMC() {
        return moduleMC;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }
}
