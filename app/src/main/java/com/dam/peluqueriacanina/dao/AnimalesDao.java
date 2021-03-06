package com.dam.peluqueriacanina.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dam.peluqueriacanina.entity.Animal;

import java.util.List;

@Dao
public interface AnimalesDao {
    @Query("SELECT * FROM ANIMALES")
    List<Animal> sacarTodo();

    @Query("SELECT * FROM ANIMALES WHERE `key` = :key")
    Animal sacarAnimal(String key);

    @Query("SELECT * FROM ANIMALES WHERE `keyU` = :keyU")
    List<Animal> sacarAnimalKey(String keyU);

    @Insert
    void insert(Animal animal);

    @Delete
    void delete(Animal animal);
}
