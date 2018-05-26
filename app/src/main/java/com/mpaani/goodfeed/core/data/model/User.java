package com.mpaani.goodfeed.core.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import static com.mpaani.goodfeed.core.db.Constants.USER_EMAIL;
import static com.mpaani.goodfeed.core.db.Constants.USER_ID;
import static com.mpaani.goodfeed.core.db.Constants.USER_PHONE;
import static com.mpaani.goodfeed.core.db.Constants.USER_SIMPLE_NAME;
import static com.mpaani.goodfeed.core.db.Constants.USER_TABLE_NAME;
import static com.mpaani.goodfeed.core.db.Constants.USER_USERNAME;
import static com.mpaani.goodfeed.core.db.Constants.USER_WEBSITE;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "username",
        "email",
        "address",
        "phone",
        "website",
        "company"
})
@Entity(tableName = USER_TABLE_NAME)
public class User {

    @ColumnInfo(name = USER_ID)
    @JsonProperty("id")
    private Integer id;

    @ColumnInfo(name = USER_SIMPLE_NAME)
    @JsonProperty("name")
    private String name;

    @ColumnInfo(name = USER_USERNAME)
    @JsonProperty("username")
    private String username;

    @PrimaryKey
    @ColumnInfo(name = USER_EMAIL)
    @JsonProperty("email")
    @NonNull
    private String email = "";

    @Embedded
    @JsonProperty("address")
    private Address address;

    @ColumnInfo(name = USER_PHONE)
    @JsonProperty("phone")
    private String phone;

    @ColumnInfo(name = USER_WEBSITE)
    @JsonProperty("website")
    private String website;

    @Embedded
    @JsonProperty("company")
    private Company company;

    @Ignore
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    @JsonProperty("company")
    public Company getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(Company company) {
        this.company = company;
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
