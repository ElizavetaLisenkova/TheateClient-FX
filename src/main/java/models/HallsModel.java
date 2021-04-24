package models;

import com.google.gson.Gson;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

public class HallsModel implements ApiModel{

    private final LongProperty id;
    private final StringProperty name;
    private final IntegerProperty totalSeats;

    public HallsModel() {
        this(null, null, null);
    }

    public HallsModel(Long id, String name, Integer totalSeats) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.totalSeats = new SimpleIntegerProperty(totalSeats);
    }

    @Override
    public String toJson() {

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id.get()));
        map.put("name", name.get());
        map.put("totalPlaces", String.valueOf(totalSeats.get()));

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

    public int getTotalSeats() {
        return totalSeats.get();
    }

    public IntegerProperty totalSeatsProperty() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats.set(totalSeats);
    }
}
