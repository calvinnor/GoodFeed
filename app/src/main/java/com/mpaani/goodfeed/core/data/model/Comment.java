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

import static com.mpaani.goodfeed.core.db.Constants.COMMENT_BODY;
import static com.mpaani.goodfeed.core.db.Constants.COMMENT_EMAIL;
import static com.mpaani.goodfeed.core.db.Constants.COMMENT_ID;
import static com.mpaani.goodfeed.core.db.Constants.COMMENT_NAME;
import static com.mpaani.goodfeed.core.db.Constants.COMMENT_POST_ID;
import static com.mpaani.goodfeed.core.db.Constants.COMMENT_TABLE_NAME;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "postId",
        "id",
        "name",
        "email",
        "body"
})
@Entity(tableName = COMMENT_TABLE_NAME)
public class Comment {

    @ColumnInfo(name = COMMENT_POST_ID)
    @JsonProperty("postId")
    private Integer postId;

    @PrimaryKey
    @ColumnInfo(name = COMMENT_ID)
    @JsonProperty("id")
    private Integer id;

    @ColumnInfo(name = COMMENT_NAME)
    @JsonProperty("name")
    private String name;

    @ColumnInfo(name = COMMENT_EMAIL)
    @JsonProperty("email")
    private String email;

    @ColumnInfo(name = COMMENT_BODY)
    @JsonProperty("body")
    private String body;

    @Ignore
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("postId")
    public Integer getPostId() {
        return postId;
    }

    @JsonProperty("postId")
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

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

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
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
