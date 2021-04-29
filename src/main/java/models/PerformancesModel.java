package models;

import com.google.gson.JsonObject;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.JSONException;
import org.json.JSONObject;

public class PerformancesModel implements ApiModel{

    private LongProperty id;
    private StringProperty name;
    private StringProperty date;
    private StringProperty time;
    private TroupsModel troup;
    private StringProperty troupName;
    private HallsModel hall;
    private StringProperty hallName;
    private StringProperty status;


    public PerformancesModel(Long id, String name, String date, String time, TroupsModel troup, HallsModel hall, String status) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.troup = troup;
        this.troupName = new SimpleStringProperty(troup.getName());
        this.hall = hall;
        this.hallName = new SimpleStringProperty(hall.getName());
        this.status = new SimpleStringProperty(status);
    }

    public PerformancesModel(JsonObject json) {
        this.id = new SimpleLongProperty(json.get("id").getAsLong());
        this.name = new SimpleStringProperty(json.get("name").getAsString());
        this.date = new SimpleStringProperty(json.get("date").getAsString());
        this.time = new SimpleStringProperty(json.get("time").getAsString());
        this.troup = new TroupsModel(json.get("troup").getAsJsonObject());
        this.troupName = new SimpleStringProperty(troup.getName());
        this.hall = new HallsModel(json.get("hall").getAsJsonObject());;
        this.hallName = new SimpleStringProperty(hall.getName());
        this.status = new SimpleStringProperty(json.get("status").getAsString());
    }

    public PerformancesModel(Long id) {
        this.id = new SimpleLongProperty(id);
    }


    @Override
    public String toJson() {
        JSONObject map = new JSONObject();
        try {
            map.put("id", id.get());
            map.put("name", name.get());
            map.put("date", date.get());
            map.put("time", time.get());
            map.put("troup", troup.toJsonObj());
            map.put("hall", hall.toJsonObj());
            map.put("status", status.get());
            return map.toString();
        } catch (JSONException exception) {
            exception.printStackTrace();
        } return null;
    }


    public JSONObject toJsonObj() {
        JSONObject map = new JSONObject();
        try {
            map.put("id", id.get());
            map.put("name", name.get());
            map.put("date", date.get());
            map.put("time", time.get());
            map.put("troup", troup.toJsonObj());
            map.put("hall", hall.toJsonObj());
            map.put("status", status.get());
            return map;
        } catch (JSONException exception) {
            exception.printStackTrace();
        } return null;
    }


    @Override
    public String toString() {
        return id.get() + ". " + name.get();
    }

    public String getHallName() {
        return hallName.get();
    }

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDate() {
        return date.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getTroupName() {
        return troupName.get();
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
