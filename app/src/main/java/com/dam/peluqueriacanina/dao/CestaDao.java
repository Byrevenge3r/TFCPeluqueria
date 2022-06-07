package com.dam.peluqueriacanina.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dam.peluqueriacanina.entity.Cesta;

import java.util.List;

@Dao
public interface CestaDao {
    @Query("SELECT * FROM CESTA")
    List<Cesta> sacarTodo();

    @Insert
    void insert(Cesta cesta);

    @Update
    void update(Cesta cesta);

    @Insert
    void insert(List<Cesta> cesta);

    @Delete
    void delete(List<Cesta> cesta);

    @Delete
    void delete(Cesta cesta);
}
