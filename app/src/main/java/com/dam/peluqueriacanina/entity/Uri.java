package com.dam.peluqueriacanina.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "URI")
public class Uri {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "key")
    private String key;

    @ColumnInfo(name = "uri")
    private String uri;

    public Uri(String key, String uri) {
        this.key = key;
        this.uri = uri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
