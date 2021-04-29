package ApiService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.HallsModel;
import models.PerformancesModel;
import models.TicketsModel;
import models.TroupsModel;
import utils.HttpClass;

import java.util.ArrayList;
import java.util.List;

public class TicketsJsonParser {

    private static final String ServerURL = "http://localhost:8080";
    private static final String apiURL = "/ticket/";


    public TicketsJsonParser() {
    }


    public void createTicket(TicketsModel ticket) {
        HttpClass.PostRequest(ServerURL + apiURL, ticket.toJson());
    }


    public List<TicketsModel> getTickets() {
        List<TicketsModel> result = new ArrayList<>();
        String buffer = HttpClass.GetRequest(ServerURL + apiURL);

        JsonArray jsonResult = JsonParser.parseString(buffer).getAsJsonArray();
        for (int i = 0; i < jsonResult.size(); i++) {
            JsonObject currentTicketsModel = jsonResult.get(i).getAsJsonObject();

            Long id = currentTicketsModel.get("id").getAsLong();
            Integer price = currentTicketsModel.get("price").getAsInt();
            PerformancesModel performance = new PerformancesModel(currentTicketsModel.get("performance").getAsJsonObject());
            Integer place = currentTicketsModel.get("place").getAsInt();
            String availability = currentTicketsModel.get("availability").getAsString();

            TicketsModel newTicketsModel = new TicketsModel(id, price, performance, place, availability);
            result.add(newTicketsModel);
        }
        return result;
    }


    public void updateTicket(TicketsModel ticket) {
        Long id = ticket.getId();
        String jsonString = ticket.toJson();
        HttpClass.PutRequest(ServerURL + apiURL + id, jsonString);
    }


    public boolean deleteTicket(TicketsModel ticket) {
        Long id = ticket.getId();
        if (id == null){
            return false;
        }
        return HttpClass.DeleteRequest(ServerURL + apiURL + id);
    }

}
