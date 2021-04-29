package models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.JSONException;
import org.json.JSONObject;

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


    @Override
    public String toJson() {
        JSONObject map = new JSONObject();
        try {
            map.put("id", id.get());
            map.put("troup", troup.toJsonObj());
            map.put("fullName", fullName.get());
            return map.toString();
        } catch (JSONException exception) {
            exception.printStackTrace();
        } return null;
    }

    public ActorsModel(Long id) {
        this.id = new SimpleLongProperty(id);
    }

    public JSONObject toJsonObj() {
        JSONObject map = new JSONObject();
        try {
            map.put("id", id.get());
            map.put("troup", troup.toJsonObj());
            map.put("fullName", fullName.get());
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

    public String getFullName() {
        return fullName.get();
    }

    public String getTroupName() {
        return troupName.get();
    }


}
