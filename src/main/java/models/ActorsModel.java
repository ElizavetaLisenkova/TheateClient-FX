package models;

import com.google.gson.Gson;
//import com.google.gson.JsonObject;
import javafx.beans.property.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActorsModel implements ApiModel{

    private LongProperty id;
    private StringProperty fullName;
    private TroupsModel troup;
    private LongProperty troupId;
    private StringProperty troupName;


    public ActorsModel(Long id, String name, TroupsModel troup) {
        this.id = new SimpleLongProperty(id);
        this.fullName = new SimpleStringProperty(name);
        this.troup = troup;
        this.troupId = new SimpleLongProperty(troup.getId());
        this.troupName = new SimpleStringProperty(troup.getName());
    }


    public void setTroup(TroupsModel troup) {
        this.troup = troup;
    }

    public long getTroupId() {
        return troupId.get();
    }

    public LongProperty troupIdProperty() {
        return troupId;
    }

    public void setTroupId(long troupId) {
        this.troupId.set(troupId);
    }

    public String getTroupName() {
        return troupName.get();
    }

    public StringProperty troupNameProperty() {
        return troupName;
    }

    public void setTroupName(String troupName) {
        this.troupName.set(troupName);
    }


    @Override
    public String toJson() {

        JSONObject map = new JSONObject();
        try {
            map.put("id", id.get());
            map.put("troup", troup.toJsonObj());
            map.put("fullName", fullName.get());
            System.out.println("метод toJson в Актерах: "+map.toString());
            return map.toString();
        } catch (JSONException exception) {
            exception.printStackTrace();
        } return null;


    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public TroupsModel getTroup() {
        return troup;
    }
}
