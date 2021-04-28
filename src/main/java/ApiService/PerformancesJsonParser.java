package ApiService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.HallsModel;
import models.PerformancesModel;
import models.TroupsModel;
import utils.HttpClass;

import java.util.ArrayList;
import java.util.List;

public class PerformancesJsonParser {

    private static final String ServerURL = "http://localhost:8080";
    private static final String apiURL = "/performance/";


    public PerformancesJsonParser() {
    }


    public void createPerformance(PerformancesModel performance) {
        System.out.println("im PerformancesParser createPerformance");
        System.out.println(performance.toJson());
        HttpClass.PostRequest(ServerURL + apiURL, performance.toJson());
    }


    public List<PerformancesModel> getPerformances() {
        List<PerformancesModel> result = new ArrayList<>();
        String buffer = HttpClass.GetRequest(ServerURL + apiURL);

        JsonArray jsonResult = JsonParser.parseString(buffer).getAsJsonArray();

        for (int i = 0; i < jsonResult.size(); i++) {
            JsonObject currentPerformancesModel = jsonResult.get(i).getAsJsonObject();
            Long id = currentPerformancesModel.get("id").getAsLong();
            String name = currentPerformancesModel.get("name").getAsString();
            String date = currentPerformancesModel.get("date").getAsString();
            String time = currentPerformancesModel.get("time").getAsString();
            TroupsModel troup = new TroupsModel(currentPerformancesModel.get("troup").getAsJsonObject());
            HallsModel hall = new HallsModel(currentPerformancesModel.get("hall").getAsJsonObject());
            String status = currentPerformancesModel.get("status").getAsString();
            PerformancesModel newPerformancesModel = new PerformancesModel(id, name, date, time, troup, hall, status);
            result.add(newPerformancesModel);
        }
        return result;
    }


    public void updatePerformance(PerformancesModel performance) {
        Long id = performance.getId();
        String jsonString = performance.toJson();
        HttpClass.PutRequest(ServerURL + apiURL + id, jsonString);
    }


    public boolean deletePerformance(PerformancesModel performance) {
        Long id = performance.getId();
        if (id == null)
            return false;
        return HttpClass.DeleteRequest(ServerURL + apiURL + id);
    }

}
