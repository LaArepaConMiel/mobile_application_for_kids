package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText et_name;
    ImageView iv_person;
    TextView tv_bestScore;
    Button btn_play;

    Switch swt_hard;

    MediaPlayer mp;

    int numeroAleatorio = (int)((Math.random() * 10) % 5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play = findViewById(R.id.btn_jugar);
        et_name = findViewById(R.id.et_nombre);
        iv_person = findViewById(R.id.iv_Persona);
        tv_bestScore = findViewById(R.id.tv_best_score);
        swt_hard = findViewById(R.id.switch_btn);

        setSupportActionBar(findViewById(R.id.myToolbar));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(
                "select * from puntaje where score = (select max(score) from puntaje)", null
        );

        if(consulta.moveToFirst()){
            String temp_name = consulta.getString(0);
            String temp_score = consulta.getString(1);
            tv_bestScore.setText("Record: " + temp_score + " " + temp_name);
        }
        BD.close();

        mp = MediaPlayer.create(this,R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);

        String frutas[] = {"mango","fresa","manzana","sandia","uva"};
        int id;
        id = getResources().getIdentifier(frutas[numeroAleatorio],"drawable",getPackageName());
        iv_person.setImageResource(id);
    }

    public void play(View view){
        String name = et_name.getText().toString();

        if(!name.equals("")){
            mp.stop();
            mp.release();
            Intent intent = (swt_hard.isChecked()) ? new Intent(this,MainActivity2_NivelHard.class)  : new Intent(this,MainActivity2_Nivel1.class);
            intent.putExtra("jugador",name);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Debe escribir un nombre",Toast.LENGTH_SHORT).show();
            et_name.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_name, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}