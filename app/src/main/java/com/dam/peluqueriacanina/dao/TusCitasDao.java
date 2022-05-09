package com.dam.peluqueriacanina.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.Cita;
import com.dam.peluqueriacanina.entity.TusCitas;

import java.util.List;

@Dao
public interface TusCitasDao {
    @Query("SELECT * FROM TUSCITAS")
    List<TusCitas> sacarTodo();

    @Query("SELECT * FROM TUSCITAS WHERE ruta = :ruta")
    TusCitas sacarCitaRuta(String ruta);

    @Insert
    void insert(TusCitas animal);

    @Delete
    void delete(TusCitas animal);

}
