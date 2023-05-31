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

import java.io.Console;

public class MainActivity2_NivelHard extends AppCompatActivity {
    TextView tv_name, tv_score;
    ImageView iv_one, iv_two, iv_three, iv_lives, iv_operation_one, iv_operation_two;
    EditText et_response;
    MediaPlayer mp, mpGreat, mpBad;
    int score, num_aleatory_one, num_aleatory_two, num_aleatory_three, result;
    String player_name;

    String numbers[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","dies"};

    //números divisibles para de 0 a 10
    int cantidades[] = {10,1,2,2,3,2,3,2,4,3,4};
    int combinaciones[][] = {
            {1,2,3,4,5,6,7,8,9,10}, //0
            {1},                    //1
            {1,2},                  //2
            {1,3},                  //3
            {1,2,4},                //4
            {1,5},                  //5
            {1,3,6},                //6
            {1,7},                  //7
            {1,2,4,8},              //8
            {1,3,9},                //9
            {1,2,5,10}              //10
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_nivel_hard);

        Toast.makeText(this,"Nivel Difícil - Operaciones combinadas", Toast.LENGTH_SHORT).show();

        tv_name = findViewById(R.id.tv_Nombre);
        tv_score = findViewById(R.id.tv_score);
        iv_lives = findViewById(R.id.iv_manzana);
        iv_one = findViewById(R.id.iv_numero1);
        iv_two = findViewById(R.id.iv_numero2);
        iv_three = findViewById(R.id.iv_numero3);
        iv_operation_one = findViewById(R.id.iv_symbol_1);
        iv_operation_two = findViewById(R.id.iv_symbol_2);
        et_response = findViewById(R.id.et_respuesta);

        player_name = getIntent().getStringExtra("jugador");
        tv_name.setText("jugador: " + player_name);

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
                Toast.makeText(this, "has perdido", Toast.LENGTH_SHORT).show();
                mp.stop();
                mp.release();
                Intent intent = new Intent(this,MainActivity2_Lose.class);
                intent.putExtra("jugador", player_name);
                intent.putExtra("score", String.valueOf(score));
                startActivity(intent);
                finish();
            }
            DataBase();
            et_response.setText("");
            aleatoryNumber();
        }else{
            Toast.makeText(this, "Debe dar una respuesta", Toast.LENGTH_SHORT).show();
        }
    }

    private void aleatoryNumber(){
        try{
            int result_1 = 0;

            //define la primera operación
            int first_operation = (int)(Math.random() * 10) % 4; //suma -> resta ->multiplicacion -> division
            num_aleatory_one = (int) (Math.random() * 10);
            if(first_operation == 3){ // si es division
                num_aleatory_two = combinaciones[num_aleatory_one][(int)(Math.random() * 10) % cantidades[num_aleatory_one]];
            }else{
                num_aleatory_two = (int) (Math.random() * 10);
            }

            //define la segunda operación
            int second_operation = (int)(Math.random() * 10) % 4;
            if(first_operation == 2 || first_operation == 3){ //la primera operacion tiene prioridad
                result_1 = (first_operation == 2) ? num_aleatory_one * num_aleatory_two : num_aleatory_one / num_aleatory_two;
                num_aleatory_three = (second_operation == 3)
                        ? combinaciones[result_1][(int)(Math.random() * 10) % cantidades[result_1]]
                        : (int)(Math.random() * 10);
                switch (second_operation){
                    case 0:
                        result = result_1 + num_aleatory_three;
                        break;
                    case 1:
                        result = result_1 - num_aleatory_three;
                        break;
                    case 2:
                        result = result_1 * num_aleatory_three;
                        break;
                    case 3:
                        result = result_1 / num_aleatory_three;
                        break;
                }
            }else{
                num_aleatory_three = (second_operation == 3)
                        ? combinaciones[num_aleatory_two][(int)(Math.random() * 10) % cantidades[num_aleatory_two]]
                        : (int)(Math.random() * 10);
                if(second_operation == 2 || second_operation == 3){
                    switch (second_operation){
                        case 2:
                            result_1 = num_aleatory_two * num_aleatory_three;
                            break;
                        case 3:
                            result_1 = num_aleatory_two / num_aleatory_three;
                            break;
                    }
                    switch (first_operation){
                        case 0:
                            result = num_aleatory_one + result_1;
                            break;
                        case 1:
                            result = num_aleatory_one - result_1;
                            break;
                    }
                }else{
                    switch (first_operation){
                        case 0:
                            result_1 = num_aleatory_one + num_aleatory_two;
                            break;
                        case 1:
                            result_1 = num_aleatory_one - num_aleatory_two;
                            break;
                    }
                    switch (second_operation){
                        case 0:
                            result = result_1 + num_aleatory_three;
                            break;
                        case 1:
                            result = result_1 - num_aleatory_three;
                            break;
                    }
                }
            }

            //verifica negativos
            if(result < 0){
                aleatoryNumber();
            }else{
                System.out.println(result);
                int id = getResources().getIdentifier(numbers[num_aleatory_one],"drawable",getPackageName());
                iv_one.setImageResource(id);
                id = getResources().getIdentifier(numbers[num_aleatory_two],"drawable",getPackageName());
                iv_two.setImageResource(id);
                id = getResources().getIdentifier(numbers[num_aleatory_three],"drawable",getPackageName());
                iv_three.setImageResource(id);

                switch (first_operation){
                    case 0:
                        iv_operation_one.setImageResource(R.drawable.sumarp);
                        break;
                    case 1:
                        iv_operation_one.setImageResource(R.drawable.restarp);
                        break;
                    case 2:
                        iv_operation_one.setImageResource(R.drawable.multiplicarrp);
                        break;
                    case 3:
                        iv_operation_one.setImageResource(R.drawable.divisionrp);
                        break;
                }
                switch (second_operation){
                    case 0:
                        iv_operation_two.setImageResource(R.drawable.sumarp);
                        break;
                    case 1:
                        iv_operation_two.setImageResource(R.drawable.restarp);
                        break;
                    case 2:
                        iv_operation_two.setImageResource(R.drawable.multiplicarrp);
                        break;
                    case 3:
                        iv_operation_two.setImageResource(R.drawable.divisionrp);
                        break;
                }
            }
        }catch(Exception e){
            aleatoryNumber();
        }
    }

    public void DataBase(){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();
        Cursor query = BD.rawQuery(
                "select * from puntaje where score = (select max(score) from puntaje)", null
        );
        if(query.moveToFirst()) {
            String temp_name = query.getString(0);
            String temp_score = query.getString(1);

            int bestScore = Integer.parseInt(temp_score);

            if(score > bestScore){
                ContentValues modify = new ContentValues();
                modify.put("nombre",player_name);
                modify.put("score",score);
                BD.update("puntaje",modify,"score=" + bestScore,null);
            }
        }else{
            ContentValues insert = new ContentValues();
            insert.put("nombre",player_name);
            insert.put("score",score);
            BD.insert("puntaje",null,insert);
        }
    }

    @Override
    public void onBackPressed(){
    }
}