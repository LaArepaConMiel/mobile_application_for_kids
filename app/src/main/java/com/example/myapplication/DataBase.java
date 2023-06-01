package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Score.class, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract ScoreDAO scoreDAO();
}
