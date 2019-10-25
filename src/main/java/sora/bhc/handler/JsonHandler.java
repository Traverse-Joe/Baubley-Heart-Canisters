package sora.bhc.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.loading.FMLPaths;
import sora.bhc.BaubleyHeartCanisters;
import sora.bhc.Reference;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonHandler {

    public static List<String> genericValues = Arrays.asList(new String[]{"hostile", "boss", "dragon"});

    private Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private JsonObject object = new JsonObject();
    private String name;

    public JsonHandler(String name) {
        this.name = name;
    }

    public JsonObject getObject() {
        return object;
    }

    public void addObject(String category, String name, double value) {
        object.getAsJsonObject(category).addProperty(name, value);
    }

    public void addCategory(String... categories) {
        for (String string : categories) {
            object.add(string, new JsonObject());
        }
    }



    public void saveJson() {
        File file = new File(FMLPaths.CONFIGDIR.get().toFile() + "/bhc/" + BaubleyHeartCanisters.MODID);
        JsonParser parser = new JsonParser();
        try {
            if (file.exists()){
                object = parser.parse(new FileReader(file)).getAsJsonObject();
                return;
            }
            FileWriter writer = new FileWriter(file);
            gson.toJson(object, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
