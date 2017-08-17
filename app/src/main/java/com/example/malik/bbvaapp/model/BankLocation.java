package com.example.malik.bbvaapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BankLocation {

private Double latitude;
private Double longitude;
    private String formattedAddress;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();
public static String LATITUDE = "lat";
    public static String LONGITUDE = "lng";

    public BankLocation(JSONObject locationObject, String formatted_address) {
        try {
            latitude = // locationObject.getString(LATITUDE);
                    Double.parseDouble(locationObject.getString(LATITUDE));
            longitude =
                    Double.parseDouble(locationObject.getString(LONGITUDE));

            //locationObject.getString(LONGITUDE);
            formattedAddress = formatted_address;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getFormattedAddress(){return formattedAddress;}
    public Double getLatitude() {
return latitude;
}

public void setLatitude(Double latitude) {
this.latitude = latitude;
}

public Double getLongitude() {
return longitude;
}

public void setLongitude(Double longitude) {
this.longitude = longitude;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

    @Override
    public String toString() {
        return  "lat"+latitude+"\n"+"lon"+longitude+"\n"+"address"+formattedAddress;
    }
}