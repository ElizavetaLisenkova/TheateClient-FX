package ApiService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.HallsModel;
import utils.HttpClass;

import java.util.ArrayList;
import java.util.List;

public class HallsJsonParser {

    private static final String ServerURL = "http://localhost:8080";
    private static final String apiURL = "/hall/";

    public HallsJsonParser() {
    }

    public void createHall(HallsModel hall) {
        HttpClass.PostRequest(ServerURL + apiURL, hall.toJson());
    }


    public List<HallsModel> getHalls() {
        List<HallsModel> result = new ArrayList<>();
        String buffer = HttpClass.GetRequest(ServerURL + apiURL);

        JsonArray jsonResult = JsonParser.parseString(buffer).getAsJsonArray();
        System.out.println(jsonResult.toString());
        for (int i = 0; i < jsonResult.size(); i++) {
            JsonObject currentHallsModel = jsonResult.get(i).getAsJsonObject();
            Long id = currentHallsModel.get("id").getAsLong();
            String name = currentHallsModel.get("name").getAsString();
            Integer seatsNumber = currentHallsModel.get("totalPlaces").getAsInt();

            HallsModel newHallsModel = new HallsModel(id, name, seatsNumber);
            result.add(newHallsModel);
        }
        return result;
    }




    public void updateHall(HallsModel hall) {
        Long id = hall.getId();
        String jsonString = hall.toJson();
        HttpClass.PutRequest(ServerURL + apiURL + id, jsonString);
    }


    public boolean deleteHall(HallsModel hall) {
        Long id = hall.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(ServerURL + apiURL + id);
    }
}
