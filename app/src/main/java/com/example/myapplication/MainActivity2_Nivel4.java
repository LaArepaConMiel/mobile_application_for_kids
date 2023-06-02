package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2_Nivel4 extends AppCompatActivity {

    TextView tv_name, tv_score;
    ImageView iv_one, iv_two, iv_lives, iv_sym;
    EditText et_response;
    MediaPlayer mp, mpGreat, mpBad;
    int score, num_aleatory_one, num_aleatory_two, result, lives=3;
    String player_name, string_score, string_lives, string_mode;

    String numbers[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","dies"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_nivel4);

        Toast.makeText(this,"Nivel 4 - Sumas y Restas", Toast.LENGTH_SHORT).show();

        tv_name = findViewById(R.id.tv_Nombre);
        tv_score = findViewById(R.id.tv_score);
        iv_lives = findViewById(R.id.iv_manzana);
        iv_one = findViewById(R.id.iv_numero1);
        iv_two = findViewById(R.id.iv_numero2);
        et_response = findViewById(R.id.et_respuesta);
        iv_sym = findViewById(R.id.iv_symbol_1);

        player_name = getIntent().getStringExtra("jugador");
        tv_name.setText("jugador: " + player_name);

        string_score = getIntent().getStringExtra("score");
        score = Integer.parseInt(string_score);
        tv_score.setText("Score: " + score);

        string_lives = getIntent().getStringExtra("vidas");
        lives = Integer.parseInt(string_lives);
        switch (lives){
            case 3:
                iv_lives.setImageResource(R.drawable.tresvidas);
                break;
            case 2:
                iv_lives.setImageResource(R.drawable.dosvidas);
                break;
            case 1:
                iv_lives.setImageResource(R.drawable.unavida);
                break;
        }

        mp = MediaPlayer.create(this,R.raw.goats);
        mp.start();
        mp.setLooping(true);

        setSupportActionBar(findViewById(R.id.myToolbar1));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mpGreat = MediaPlayer.create(this,R.raw.wonderful);
        mpBad = MediaPlayer.create(this,R.raw.bad);

        aleatoryNumber();
    }
    public void comparar(View view){
        String respuesta = et_response.getText().toString();
        if(!respuesta.equals("")){
            int respuestaJugador = Integer.parseInt(respuesta);
            if(result == respuestaJugador){
                mpGreat.start();
                score++;
                tv_score.setText("Score: " + score);
            }else{
                mpBad.start();
                lives--;
                switch (lives){
                    case 2:
                        iv_lives.setImageResource(R.drawable.dosvidas);
                        Toast.makeText(this, "Quedan 2 Manzanas", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        iv_lives.setImageResource(R.drawable.unavida);
                        Toast.makeText(this, "Queda 1 Manzana", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(this, "has perdido todas tus manzanas", Toast.LENGTH_SHORT).show();
                        mp.stop();
                        mp.release();
                        Intent intent = new Intent(this,MainActivity2_Lose.class);
                        string_score = String.valueOf(score);
                        string_mode = "0";
                        intent.putExtra("jugador", player_name);
                        intent.putExtra("score", string_score);
                        intent.putExtra("mode", string_mode);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
            //DataBase();
            et_response.setText("");
            aleatoryNumber();
        }else{
            Toast.makeText(this, "Debe dar una respuesta", Toast.LENGTH_SHORT).show();
        }
    }
    private void aleatoryNumber(){
        if(score <= 39){
            num_aleatory_one = (int) (Math.random() * 10);
            num_aleatory_two = (int) (Math.random() * 10);

            if(num_aleatory_one > 0 && num_aleatory_two <= 4){
                result = num_aleatory_one - num_aleatory_two;
                result = num_aleatory_one - num_aleatory_two;
                if(result < 0){
                    num_aleatory_one = num_aleatory_one + num_aleatory_two;
                    num_aleatory_two = num_aleatory_one - num_aleatory_two;
                    num_aleatory_one = num_aleatory_one - num_aleatory_two;
                    result = num_aleatory_one - num_aleatory_two;
                }
                int id = getResources().getIdentifier(numbers[num_aleatory_one],"drawable",getPackageName());
                iv_one.setImageResource(id);
                id = getResources().getIdentifier(numbers[num_aleatory_two],"drawable",getPackageName());
                iv_two.setImageResource(id);
                iv_sym.setImageResource(R.drawable.restarp);
            }else{
                result = num_aleatory_one + num_aleatory_two;
                int id = getResources().getIdentifier(numbers[num_aleatory_one],"drawable",getPackageName());
                iv_one.setImageResource(id);
                id = getResources().getIdentifier(numbers[num_aleatory_two],"drawable",getPackageName());
                iv_two.setImageResource(id);
                iv_sym.setImageResource(R.drawable.sumarp);
            }
        }else{
            Intent intent = new Intent(this,MainActivity2_Nivel5.class);
            string_score = String.valueOf(score);
            string_lives = String.valueOf(lives);
            intent.putExtra("jugador", player_name);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_lives);
            mp.stop();
            mp.release();
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){
    }
}