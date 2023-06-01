package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
    }
    private void restratJ(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}