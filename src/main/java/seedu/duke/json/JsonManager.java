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

public class JsonManager {

    InputStream inputStream;
    Gson gson;
    InputStreamReader reader;
    List<JsonObject> jsonArray;

    String moduleDescription;

    int moduleMC;

    String moduleTitle;

    ArrayList<Integer> moduleSemester;

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

    public boolean correctSemester(int intendedSem) {
        return moduleSemester.contains(intendedSem);
    }

    public boolean moduleExist(String moduleCode) {
        for (JsonObject obj : jsonArray) {
            String name = obj.get("moduleCode").getAsString();
            if (name.equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    public void getModuleInfo(String moduleCode) {
        for (JsonObject obj : jsonArray) {
            String name = obj.get("moduleCode").getAsString();
            if (name.equals(moduleCode)) {
                moduleSemester = new ArrayList<>();
                this.moduleMC = obj.get("moduleCredit").getAsInt();
                this.moduleDescription = obj.get("description").getAsString();
                this.moduleTitle = obj.get("title").getAsString();
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

    public int getModuleMC() {
        return moduleMC;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }
}
