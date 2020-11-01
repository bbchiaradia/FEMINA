package com.alejandro.android.femina.Pantallas_exteriores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alejandro.android.femina.BD.Usuarios.UsuariosBD;
import com.alejandro.android.femina.Dialogos.DialogoRecuperar;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

public class Ingresar extends AppCompatActivity {

    private Button btn_registrarse,btn_ingresar,btn_olvidaste;
    private EditText usuario,contrasena;
    private Usuarios user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_ingresar);



       btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
       btn_registrarse = (Button) findViewById(R.id.btn_registrarse);
       btn_olvidaste = (Button) findViewById(R.id.btn_olvidaste);
       usuario = (EditText) findViewById(R.id.txt_usuario_ingresar);
       contrasena = (EditText) findViewById(R.id.txt_contraseña_ingresar);

        usuario.setText("User1");
        contrasena.setText("aleale");

        iniciarSesion();


        btn_olvidaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoRecuperar dialog = new DialogoRecuperar();
                dialog.show(getSupportFragmentManager(), "");
            }
        });


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


    private boolean recuperar_contrasena(String numero, String mensaje) {


        if (ActivityCompat.checkSelfPermission(Ingresar.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Ingresar.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(Ingresar.this, new String[]{
                    Manifest.permission.SEND_SMS,
            }, 1000);
        else {
            try {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(numero, null, mensaje, null, null);
                Toast.makeText(this, "Mensaje enviado!", Toast.LENGTH_SHORT).show();
                return true;
            } catch (Exception e) {
                Toast.makeText(this, "Error al enviar!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }
        }

        return false;

    }

    public boolean enviar_datos(String numero, String contrasena, String usuario) {

        return recuperar_contrasena(numero, "- FEMINA RECUPERACION - \n Tus datos de acceso son: \n" +
                " Usuario: " + usuario + "\n" +
                " Contraseña: " + contrasena);
    }

    public void iniciarSesion(){
        user = new Usuarios();
        user.setUsuario(usuario.getText().toString());
        user.setContrasena(contrasena.getText().toString());
        UsuariosBD us = new UsuariosBD(user,this,"Loguin");
        us.execute();
    }

}