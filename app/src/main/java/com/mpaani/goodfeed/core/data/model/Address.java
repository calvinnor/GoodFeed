package com.mpaani.goodfeed.core.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import static com.mpaani.goodfeed.core.db.Constants.USER_ADDRESS_CITY;
import static com.mpaani.goodfeed.core.db.Constants.USER_ADDRESS_STREET;
import static com.mpaani.goodfeed.core.db.Constants.USER_ADDRESS_SUITE;
import static com.mpaani.goodfeed.core.db.Constants.USER_ADDRESS_ZIPCODE;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "street",
        "suite",
        "city",
        "zipcode",
        "geo"
})
public class Address {

    @ColumnInfo(name = USER_ADDRESS_STREET)
    @JsonProperty("street")
    private String street;

    @ColumnInfo(name = USER_ADDRESS_SUITE)
    @JsonProperty("suite")
    private String suite;

    @ColumnInfo(name = USER_ADDRESS_CITY)
    @JsonProperty("city")
    private String city;

    @ColumnInfo(name = USER_ADDRESS_ZIPCODE)
    @JsonProperty("zipcode")
    private String zipcode;

    @Embedded
    @JsonProperty("geo")
    private Geo geo;

    @Ignore
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty("suite")
    public String getSuite() {
        return suite;
    }

    @JsonProperty("suite")
    public void setSuite(String suite) {
        this.suite = suite;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("zipcode")
    public String getZipcode() {
        return zipcode;
    }

    @JsonProperty("zipcode")
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @JsonProperty("geo")
    public Geo getGeo() {
        return geo;
    }

    @JsonProperty("geo")
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
