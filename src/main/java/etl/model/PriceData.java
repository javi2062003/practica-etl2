package etl.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceData {
    
    @JsonProperty("value")
    private double value;

    @JsonProperty("currency")
    private String currency;

    // --- GETTERS Y SETTERS ---
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}