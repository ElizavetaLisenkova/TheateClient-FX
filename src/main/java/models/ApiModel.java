package models;

import org.json.JSONException;

public interface ApiModel {
    String toJson() throws JSONException;
}
