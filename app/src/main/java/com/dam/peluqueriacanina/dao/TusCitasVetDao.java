package com.dam.peluqueriacanina.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dam.peluqueriacanina.entity.TusCitas;

import java.util.List;
@Dao
public interface TusCitasVetDao {
    @Query("SELECT * FROM TUSCITAS")
    List<TusCitas> sacarTodo();

    @Query("SELECT * FROM TUSCITAS WHERE `key` = :key")
    List<TusCitas> sacarCitasKey(String key);

    @Query("SELECT * FROM TUSCITAS WHERE ruta = :ruta")
    TusCitas sacarCitaRuta(String ruta);

    @Insert
    void insert(TusCitas animal);

    @Delete
    void delete(TusCitas animal);
}