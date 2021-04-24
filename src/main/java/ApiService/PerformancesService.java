package ApiService;

import ApiService.ApiService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PerformancesService {

    final static String apiUrl = "performance";

    public static String findAll(){
        return ApiService.getJsonRequest(apiUrl);
    }

    public static String findById(Integer id){

        try{
                JSONArray as = new JSONArray(findAll());
                return as.getJSONObject(id).toString();
        } catch (JSONException e){
                System.out.println("Ошибка JSON");
        } return "null";

    }
















}
