package models;

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
    private LongProperty troupId;
    private StringProperty troupName;
    private HallsModel hall;
    private LongProperty hallId;
    private StringProperty hallName;
    private StringProperty status;

    public PerformancesModel(Long id, String name, String date, String time, TroupsModel troup, HallsModel hall, String status) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.troup = troup;
        this.troupId = new SimpleLongProperty(troup.getId());
        this.troupName = new SimpleStringProperty(troup.getName());
        this.hall = hall;
        this.hallId = new SimpleLongProperty(hall.getId());
        this.hallName = new SimpleStringProperty(hall.getName());
        this.status = new SimpleStringProperty(status);
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
            System.out.println("метод toJson в Актерах: "+map.toString());
            return map.toString();
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

    public LongProperty idProperty() {
        return id;
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

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public TroupsModel getTroup() {
        return troup;
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

    public HallsModel getHall() {
        return hall;
    }

    public void setHall(HallsModel hall) {
        this.hall = hall;
    }

    public long getHallId() {
        return hallId.get();
    }

    public LongProperty hallIdProperty() {
        return hallId;
    }

    public void setHallId(long hallId) {
        this.hallId.set(hallId);
    }

    public String getHallName() {
        return hallName.get();
    }

    public StringProperty hallNameProperty() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName.set(hallName);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
