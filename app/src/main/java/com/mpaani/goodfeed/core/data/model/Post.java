package com.mpaani.goodfeed.core.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import static com.mpaani.goodfeed.core.db.Constants.POST_BODY;
import static com.mpaani.goodfeed.core.db.Constants.POST_ID;
import static com.mpaani.goodfeed.core.db.Constants.POST_TABLE_NAME;
import static com.mpaani.goodfeed.core.db.Constants.POST_TITLE;
import static com.mpaani.goodfeed.core.db.Constants.POST_USER_ID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "id",
        "title",
        "body"
})
@Entity(tableName = POST_TABLE_NAME)
public class Post {

    @ColumnInfo(name = POST_USER_ID)
    @JsonProperty("userId")
    private Integer userId;

    @PrimaryKey
    @ColumnInfo(name = POST_ID)
    @JsonProperty("id")
    private Integer id;

    @ColumnInfo(name = POST_TITLE)
    @JsonProperty("title")
    private String title;

    @ColumnInfo(name = POST_BODY)
    @JsonProperty("body")
    private String body;

    @Ignore
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
