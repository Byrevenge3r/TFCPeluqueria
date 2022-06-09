package com.dam.peluqueriacanina.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dam.peluqueriacanina.dao.UriDao;
import com.dam.peluqueriacanina.entity.Uri;

@Database(entities = {Uri.class}, version = 1, exportSchema = false)
public abstract class UriDB extends RoomDatabase {
    public abstract UriDao uriDao();

    private static UriDB URI_DB = null;

    public static UriDB getDatabase(Context context) {
        if (URI_DB == null) {
            URI_DB = Room.databaseBuilder(
                            context.getApplicationContext(), UriDB.class, "uri-db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return URI_DB;
    }
}
