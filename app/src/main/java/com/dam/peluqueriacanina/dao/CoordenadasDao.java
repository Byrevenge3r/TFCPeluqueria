package com.dam.peluqueriacanina.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dam.peluqueriacanina.entity.Animal;
import com.dam.peluqueriacanina.entity.Coordenadas;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CoordenadasDao {
    @Query("SELECT * FROM COORDENADAS")
    List<Coordenadas> sacarTodo();

    @Insert
    void insert(ArrayList<Coordenadas> lista);

}
