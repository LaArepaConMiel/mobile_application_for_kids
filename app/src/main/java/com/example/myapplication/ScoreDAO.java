package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDAO {
    @Insert
    void insertAll(Score... scores);

    @Query("SELECT * FROM Score")
    List<Score> getAllScores();

    @Query("SELECT * FROM Score WHERE hard_score = 0 ORDER BY player_score DESC")
    Score getEasyScore();

    @Query("SELECT * FROM Score WHERE hard_score = 1 ORDER BY player_score DESC")
    Score getHardScore();
}