package com.dam.peluqueriacanina.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dam.peluqueriacanina.entity.Cita;

import java.util.List;

@Dao
public interface CitasDao {
    @Query("SELECT * FROM CITAS")
    List<Cita> sacarTodo();

    @Insert
    void insert(Cita cita);

    @Delete
    void delete(Cita cita);
}
