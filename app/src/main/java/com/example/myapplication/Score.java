package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Score{

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "player_name")
    public String playerName;

    @ColumnInfo(name = "player_score")
    public int playerScore;

    @ColumnInfo(name = "hard_score")
    public int hardScore;

    public Score(int id, String playerName, int playerScore, int hardScore) {
        this.id = id;
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.hardScore = hardScore;
    }
}