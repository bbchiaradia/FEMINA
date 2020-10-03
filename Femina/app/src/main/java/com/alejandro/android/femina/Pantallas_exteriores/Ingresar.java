package com.alejandro.android.femina.Pantallas_exteriores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

public class Ingresar extends AppCompatActivity {

    private Button btn_registrarse,btn_ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_ingresar);

       btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
       btn_registrarse = (Button) findViewById(R.id.btn_registrarse);

       btn_ingresar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(intent);
           }
       });

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registrarse.class);
                startActivity(intent);
            }
        });



    }
}