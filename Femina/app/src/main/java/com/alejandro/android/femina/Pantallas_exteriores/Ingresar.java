package com.alejandro.android.femina.Pantallas_exteriores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alejandro.android.femina.BD.Usuarios.UsuariosBD;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

public class Ingresar extends AppCompatActivity {

    private Button btn_registrarse,btn_ingresar;
    private EditText usuario,contrasena;
    private Usuarios user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_ingresar);

       btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
       btn_registrarse = (Button) findViewById(R.id.btn_registrarse);
       usuario = (EditText) findViewById(R.id.txt_usuario_ingresar);
       contrasena = (EditText) findViewById(R.id.txt_contraseña_ingresar);

        usuario.setText("User1");
        contrasena.setText("aleale");

        iniciarSesion();

       btn_ingresar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if(!usuario.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty())
                   iniciarSesion();
               else
                   Toast.makeText(getApplicationContext(),"Complete todos los campos!",Toast.LENGTH_SHORT).show();

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

    public void iniciarSesion(){
        user = new Usuarios();
        user.setUsuario(usuario.getText().toString());
        user.setContrasena(contrasena.getText().toString());
        UsuariosBD us = new UsuariosBD(user,this,"Loguin");
        us.execute();
    }

}