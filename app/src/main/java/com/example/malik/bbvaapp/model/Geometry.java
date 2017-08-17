package com.example.malik.bbvaapp.model;

import java.util.HashMap;
import java.util.Map;

public class Geometry {

private BankLocation BankLocation;
private Viewport viewport;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public BankLocation getBankLocation() {
return BankLocation;
}

public void setBankLocation(BankLocation bankLocation) {
this.BankLocation = bankLocation;
}

public Viewport getViewport() {
return viewport;
}

public void setViewport(Viewport viewport) {
this.viewport = viewport;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
