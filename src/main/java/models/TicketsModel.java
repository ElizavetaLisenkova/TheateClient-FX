package models;

import javafx.beans.property.*;
import org.json.JSONException;
import org.json.JSONObject;

public class TicketsModel implements ApiModel {

    private LongProperty id;
    private IntegerProperty price;
    private PerformancesModel performance;
    private IntegerProperty place;
    private StringProperty availability;
    private LongProperty performanceId;
    private StringProperty performanceName;


    public TicketsModel(Long id, Integer price, PerformancesModel performance, Integer place, String availability) {
        this.id = new SimpleLongProperty(id);
        this.price = new SimpleIntegerProperty(price);
        this.performance = performance;
        this.performanceId = new SimpleLongProperty(performance.getId());
        this.performanceName = new SimpleStringProperty(performance.getName());
        this.place = new SimpleIntegerProperty(place);
        this.availability = new SimpleStringProperty(availability);
    }

    public TicketsModel(Long id) {
        this.id = new SimpleLongProperty(id);
    }


    @Override
    public String toJson() {
        JSONObject map = new JSONObject();
        try {
            map.put("id", id.get());
            map.put("price", price.get());
            map.put("performance", performance.toJsonObj());
            map.put("place", place.get());
            map.put("availability", availability.get());
            return map.toString();
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public int getPrice() {
        return price.get();
    }

    public int getPlace() {
        return place.get();
    }

    public String getAvailability() {
        return availability.get();
    }

    public String getPerformanceName() {
        return performanceName.get();
    }

}
