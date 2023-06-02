package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2_Lose extends AppCompatActivity {

    Button si, no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_lose);

        String player_name = getIntent().getStringExtra("jugador");
        String _player_score = getIntent().getStringExtra("score");
        int player_score = Integer.parseInt(_player_score);
        String _player_mode = getIntent().getStringExtra("mode");
        int player_mode = Integer.parseInt(_player_mode);

        DataBase db = Room.databaseBuilder(getApplicationContext(),
                DataBase.class, "dataBase").allowMainThreadQueries().build();

        Score score = (player_mode == 0)
                ? db.scoreDAO().getEasyScore()
                : db.scoreDAO().getHardScore();

        Score new_score = new Score();
        new_score.playerName = player_name;
        new_score.playerScore = player_score;
        new_score.hardScore = player_mode;
        if(score != null){
            if (score.playerScore < player_score){
                db.scoreDAO().insertAll(new_score);
            }
        }else{
            db.scoreDAO().insertAll(new_score);
        }

        si = findViewById(R.id.button_si);
        no = findViewById(R.id.button_no);

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciar();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void reiniciar(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}