package models;

import com.google.gson.Gson;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

public class TroupsModel implements ApiModel{

    private final LongProperty id;
    private final StringProperty name;

    public TroupsModel() {
        this(null, null);
    }

    public TroupsModel(Long id, String name) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
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

}
