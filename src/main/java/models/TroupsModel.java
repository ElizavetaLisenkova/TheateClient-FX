package models;

import com.google.gson.JsonObject;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.JSONException;
import org.json.JSONObject;

public class TroupsModel implements ApiModel{

    private final LongProperty id;
    private final StringProperty name;
    final char kav = (char) 34;

    public TroupsModel(Long id, String name) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public TroupsModel(JsonObject json) {
        this.id = new SimpleLongProperty(json.get("id").getAsLong());
        this.name = new SimpleStringProperty(json.get("name").getAsString());
    }

    @Override
    public String toJson() {
        JSONObject map = new JSONObject();
        try {
            map.put("id", String.valueOf(id.get()));
            map.put("name", name.get());
            return map.toString();
        } catch (JSONException exception) {
            exception.printStackTrace();
        }return null;
    }

    public JSONObject toJsonObj() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", String.valueOf(id.get()));
            obj.put("name", name.get());
            return obj;
        } catch (JSONException exception) {
            exception.printStackTrace();
        }return null;

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

    @Override
    public String toString() {
        return id.get() + ". " + name.get();
    }

}
