package com.example.malik.bbvaapp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {

private String formattedAddress;
private Geometry geometry;
private String icon;
private String id;
private String name;
private String placeId;
private String reference;
private List<String> types = null;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public String getFormattedAddress() {
return formattedAddress;
}

public void setFormattedAddress(String formattedAddress) {
this.formattedAddress = formattedAddress;
}

public Geometry getGeometry() {
return geometry;
}

public void setGeometry(Geometry geometry) {
this.geometry = geometry;
}

public String getIcon() {
return icon;
}

public void setIcon(String icon) {
this.icon = icon;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getPlaceId() {
return placeId;
}

public void setPlaceId(String placeId) {
this.placeId = placeId;
}

public String getReference() {
return reference;
}

public void setReference(String reference) {
this.reference = reference;
}

public List<String> getTypes() {
return types;
}

public void setTypes(List<String> types) {
this.types = types;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}