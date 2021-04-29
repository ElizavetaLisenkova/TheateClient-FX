package models;

import com.google.gson.JsonObject;
import javafx.beans.property.*;
import org.json.JSONException;
import org.json.JSONObject;

public class HallsModel implements ApiModel{

    private final LongProperty id;
    private final StringProperty name;
    private final IntegerProperty totalPlaces;

    public HallsModel(Long id, String name, Integer totalPlaces) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.totalPlaces = new SimpleIntegerProperty(totalPlaces);
    }


    public HallsModel(JsonObject json) {
        this.id = new SimpleLongProperty(json.get("id").getAsLong());
        this.name = new SimpleStringProperty(json.get("name").getAsString());
        this.totalPlaces = new SimpleIntegerProperty(json.get("totalPlaces").getAsInt());
    }

    @Override
    public String toJson() {
        JSONObject map = new JSONObject();
        try {
            map.put("id", String.valueOf(id.get()));
            map.put("name", name.get());
            map.put("totalPlaces", String.valueOf(totalPlaces.get()));
            return map.toString();
        } catch (JSONException exception) {
            exception.printStackTrace();
        } return null;
    }

    public JSONObject toJsonObj() {
        JSONObject map = new JSONObject();
        try {
            map.put("id", String.valueOf(id.get()));
            map.put("name", name.get());
            map.put("totalPlaces", String.valueOf(totalPlaces.get()));
            return map;
        } catch (JSONException exception) {
            exception.printStackTrace();
        } return null;
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

    public int getSeatsNumber() {
        return totalPlaces.get();
    }

    @Override
    public String toString() {
        
        return id.get()+". "+ name.get();
    }
}
