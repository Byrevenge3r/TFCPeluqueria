package com.dam.peluqueriacanina.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.Uri;

@Dao
public interface UriDao {
    @Query("SELECT * FROM URI WHERE `key` = :key")
    Uri sacarUri(String key);

    @Insert
    void insert (Uri uri);

    @Update
    void update (Uri uri);
}
