package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2_Fin extends AppCompatActivity {

    Button restrat, exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_fin);

<<<<<<< Updated upstream
        restrat = findViewById(R.id.button_restrat);
        exit = findViewById(R.id.button_salir);

        restrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restratJ();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
=======
        DataBase db = Room.databaseBuilder(getApplicationContext(),
                DataBase.class, "dataBase").allowMainThreadQueries().build();

        Score easy = db.scoreDAO().getEasyScore();
        Score hard = db.scoreDAO().getHardScore();
>>>>>>> Stashed changes
    }
    private void restratJ(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}