package com.dam.peluqueriacanina.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dam.peluqueriacanina.dao.AnimalesDao;
import com.dam.peluqueriacanina.entity.Animal;

@Database(entities = {Animal.class},version = 2)
public abstract class AnimalesDB extends RoomDatabase {
    public abstract AnimalesDao animalDao();

    private static AnimalesDB ANIMAL_DB = null;

    public static AnimalesDB getDatabase(Context context) {
        if (ANIMAL_DB == null) {
            ANIMAL_DB = Room.databaseBuilder(
                    context.getApplicationContext(), AnimalesDB.class, "animales-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return ANIMAL_DB;
    }
}
