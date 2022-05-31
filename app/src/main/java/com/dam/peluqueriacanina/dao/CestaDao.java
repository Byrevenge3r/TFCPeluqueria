package com.dam.peluqueriacanina.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.Cesta;
import com.dam.peluqueriacanina.entity.TusCitas;

import java.util.List;

@Dao
public interface CestaDao {
    @Query("SELECT * FROM CESTA")
    List<Cesta> sacarTodo();

    @Insert
    void insert(Cesta cesta);

    @Delete
    void delete(List<Cesta> cesta);

    @Delete
    void delete(Cesta cesta);


}
