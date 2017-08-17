package com.example.malik.bbvaapp.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResult {
    public static String TAG = JsonResult.class.getName();
    public  static String RESULTS = "results";
    public  static String GEOMETRY = "geometry";
    public  static String LOCATION = "location";
    public static String FORMATTED_ADDRESS = "formatted_address";

    public ArrayList<BankLocation> bankLocations = new ArrayList<>();

private List<Object> htmlAttributions = null;
private List<Result> results = null;
private String status;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public List<Object> getHtmlAttributions() {
return htmlAttributions;
}

public void setHtmlAttributions(List<Object> htmlAttributions) {
this.htmlAttributions = htmlAttributions;
}

public List<Result> getResults() {
return results;
}

public void setResults(List<Result> results) {
this.results = results;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}
   public  void getJson(String url){

    }
    public   void parseJson(String jsonString){

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject resultObject = jsonArray.getJSONObject(i);
               // Log.i(TAG,resultObject.toString());
                String formattedAddres = resultObject.getString(FORMATTED_ADDRESS);
               // Log.i(TAG,formattedAddres);

                JSONObject geometryObject = resultObject.getJSONObject(GEOMETRY);
               // Log.i(TAG,geometryObject.toString());

                JSONObject locationObject = geometryObject.getJSONObject(LOCATION);
               /// Log.i(TAG,locationObject.toString());

                BankLocation bankLocation = new BankLocation(locationObject,formattedAddres);
                Log.i(TAG, bankLocation.toString());

                addToList(bankLocation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void addToList(BankLocation bankLocation){
        bankLocations.add(bankLocation);
    }
    public ArrayList<BankLocation> getListBBVALocation(){
        return bankLocations;
    }

}
