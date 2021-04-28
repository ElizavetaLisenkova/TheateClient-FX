package models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

public class TroupsModel implements ApiModel{

    private final LongProperty id;
    private final StringProperty name;
    final char kav = (char) 34;

    public TroupsModel() {
        this(null, null);
    }

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

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id.get()));
        map.put("name", name.get());

        Gson gson = new Gson();
        return gson.toJson(map);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        String s = "{" + kav + "id" + kav + ":" + id.get() + ", " + kav + "name" + kav + ":" + kav + name.get() + kav + "}";
        System.out.println("---------------------TroupsModel ToString = "+s);
        return s;
    }
}
