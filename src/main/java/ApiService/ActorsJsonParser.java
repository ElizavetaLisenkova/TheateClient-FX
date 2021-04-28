package ApiService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.ActorsModel;
import models.TroupsModel;
import utils.HttpClass;

import java.util.ArrayList;
import java.util.List;

public class ActorsJsonParser {

    private static final String ServerURL = "http://localhost:8080";
    private static final String apiURL = "/actor/";


    public ActorsJsonParser() {
    }


    public void createActor(ActorsModel actor) {
        HttpClass.PostRequest(ServerURL + apiURL, actor.toJson());
    }


    public List<ActorsModel> getActors() {
        List<ActorsModel> result = new ArrayList<>();
        String buffer = HttpClass.GetRequest(ServerURL + apiURL);

        JsonArray jsonResult = JsonParser.parseString(buffer).getAsJsonArray();

        for (int i = 0; i < jsonResult.size(); i++) {
            JsonObject currentActorsModel = jsonResult.get(i).getAsJsonObject();
            Long id = currentActorsModel.get("id").getAsLong();
            String fullName = currentActorsModel.get("fullName").getAsString();
            TroupsModel troup = new TroupsModel(currentActorsModel.get("troup").getAsJsonObject());

            ActorsModel newActorsModel = new ActorsModel(id, fullName, troup);
            result.add(newActorsModel);
        }
        return result;
    }


    public void updateActor(ActorsModel actor) {
        Long id = actor.getId();
        String jsonString = actor.toJson();
        HttpClass.PutRequest(ServerURL + apiURL + id, jsonString);
    }


    public boolean deleteActor(ActorsModel actor) {
        Long id = actor.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(ServerURL + apiURL + id);
    }

}
