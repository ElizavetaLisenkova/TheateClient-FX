package ApiService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.TroupsModel;
import utils.HttpClass;

import java.util.ArrayList;
import java.util.List;

public class TroupsJsonParser {

    private static final String ServerURL = "http://localhost:8080";
    private static final String apiURL = "/troup/";


    public TroupsJsonParser() {
    }


    public void createTroup(TroupsModel troup) {
        HttpClass.PostRequest(ServerURL + apiURL, troup.toJson());
    }


    public List<TroupsModel> getTroups() {
        List<TroupsModel> result = new ArrayList<>();
        String buffer = HttpClass.GetRequest(ServerURL + apiURL);

        JsonArray jsonResult = JsonParser.parseString(buffer).getAsJsonArray();
        System.out.println(jsonResult.toString());
        for (int i = 0; i < jsonResult.size(); i++) {
            JsonObject currentTroupsModel = jsonResult.get(i).getAsJsonObject();
            Long id = currentTroupsModel.get("id").getAsLong();
            String name = currentTroupsModel.get("name").getAsString();

            TroupsModel newTroupsModel = new TroupsModel(id, name);
            result.add(newTroupsModel);
        }
        return result;
    }


    public void updateTroup(TroupsModel troup) {
        Long id = troup.getId();
        String jsonString = troup.toJson();
        HttpClass.PutRequest(ServerURL + apiURL + id, jsonString);
    }


    public boolean deleteTroup(TroupsModel troup) {
        Long id = troup.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(ServerURL + apiURL + id);
    }

}
