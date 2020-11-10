package com.alejandro.android.femina.Pantallas_exteriores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.Toast;

import com.alejandro.android.femina.BD.Secuencias.SecuenciasBD;
import com.alejandro.android.femina.BD.Usuarios.UsuariosBD;
import com.alejandro.android.femina.Dialogos.DialogoRecuperar;
import com.alejandro.android.femina.Entidades.Secuencias;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Exit.ExitActivity;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

import java.util.prefs.Preferences;

public class Ingresar extends AppCompatActivity {

    private Button btn_registrarse, btn_ingresar, btn_olvidaste;
    private ImageButton arriba, abajo, izquierda, derecha, ok_accesibilidad;
    private EditText usuario, contrasena;
    private Usuarios user;
    private Switch setear_accesibilidad;
    private TableLayout accesibilidad;
    private String secuencia;
    private CheckBox recordar_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_ingresar);


        btn_ingresar = (Button) findViewById(R.id.btn_ingresar);
        btn_registrarse = (Button) findViewById(R.id.btn_registrarse);
        btn_olvidaste = (Button) findViewById(R.id.btn_olvidaste);
        usuario = (EditText) findViewById(R.id.txt_usuario_ingresar);
        contrasena = (EditText) findViewById(R.id.txt_contraseña_ingresar);
        accesibilidad = (TableLayout) findViewById(R.id.accesibilidad);
        setear_accesibilidad = (Switch) findViewById(R.id.switch_coordenadas);
        arriba = (ImageButton) findViewById(R.id.btn_arriba);
        abajo = (ImageButton) findViewById(R.id.btn_abajo);
        izquierda = (ImageButton) findViewById(R.id.btn_izquierda);
        derecha = (ImageButton) findViewById(R.id.btn_derecha);
        ok_accesibilidad = (ImageButton) findViewById(R.id.btn_ok);
        recordar_usuario = (CheckBox) findViewById(R.id.recordar_usuario);


        setear_accesibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (setear_accesibilidad.isChecked()) {
                    SharedPreferences preferences = Ingresar.this.getSharedPreferences("ACCESIBILIDAD", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ACCESIBILIDAD", "ACTIVADA");
                    editor.apply();
                    accesibilidad.setVisibility(View.VISIBLE);
                } else {
                    SharedPreferences preferences = Ingresar.this.getSharedPreferences("ACCESIBILIDAD", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ACCESIBILIDAD", "DESACTIVADA");
                    editor.apply();
                    accesibilidad.setVisibility(View.GONE);
                }


            }
        });


        recordar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    Log.d("CHEQUEADO","TRUE");
                    SharedPreferences preferences = Ingresar.this.getSharedPreferences("Recuerdo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("RECUERDO","SI");
                    editor.putString("USER", usuario.getText().toString());
                    editor.apply();
                } else {
                    SharedPreferences preferences = Ingresar.this.getSharedPreferences("Recuerdo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("RECUERDO","NO");
                    editor.putString("USER", "");
                    editor.apply();
                }
            }
        });

        usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (recordar_usuario.isChecked()) {
                    SharedPreferences preferences = Ingresar.this.getSharedPreferences("Recuerdo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("RECUERDO","SI");
                    editor.putString("USER", usuario.getText().toString());
                    editor.apply();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        SharedPreferences preferences_user = this.getSharedPreferences("Recuerdo", MODE_PRIVATE);

        if (preferences_user.getString("RECUERDO", "NORECUERDO").equals("SI")) {
            usuario.setText(preferences_user.getString("USER", ""));
            recordar_usuario.setChecked(true);
        }

        if (preferences_user.getString("RECUERDO", "NORECUERDO").equals("NO")) {
            usuario.setText("");
            recordar_usuario.setChecked(false);
        }


        SharedPreferences preferences = this.getSharedPreferences("ACCESIBILIDAD", MODE_PRIVATE);

        if (preferences.getString("ACCESIBILIDAD", "DESACTIVADA").equals("ACTIVADA")) {
            accesibilidad.setVisibility(View.VISIBLE);
            setear_accesibilidad.setChecked(true);
        }

        if (preferences.getString("ACCESIBILIDAD", "DESACTIVADA").equals("DESACTIVADA")) {
            accesibilidad.setVisibility(View.GONE);
            setear_accesibilidad.setChecked(false);
        }


        //usuario.setText("User1");
        //contrasena.setText("aleale");

        secuencia = "";

        arriba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secuencia += "0";
                Toast.makeText(Ingresar.this, "ARRIBA", Toast.LENGTH_SHORT).show();
            }
        });

        derecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secuencia += "1";
                Toast.makeText(Ingresar.this, "DERECHA", Toast.LENGTH_SHORT).show();
            }
        });

        abajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secuencia += "2";
                Toast.makeText(Ingresar.this, "ABAJO", Toast.LENGTH_SHORT).show();
            }
        });

        izquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secuencia += "3";
                Toast.makeText(Ingresar.this, "IZQUIERDA", Toast.LENGTH_SHORT).show();
            }
        });

        ok_accesibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!usuario.getText().toString().isEmpty()) {

                    if (secuencia.length() < 3 || secuencia.length() > 4)
                        Toast.makeText(Ingresar.this, "Secuencia incorrecta", Toast.LENGTH_SHORT).show();
                    else {
                        Usuarios usuarios = new Usuarios();
                        Secuencias secuencias = new Secuencias();
                        usuarios.setUsuario(usuario.getText().toString());
                        secuencias.setId_usuario(usuarios);
                        secuencias.setSecuencia(secuencia);
                        SecuenciasBD secuenciasBD = new SecuenciasBD(secuencias, Ingresar.this, "LeerSecuencia");
                        secuenciasBD.execute();
                        secuencia = "";
                    }

                } else
                    Toast.makeText(Ingresar.this, "Complete el usuario", Toast.LENGTH_SHORT).show();

            }
        });

        //iniciarSesion();


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

                if (!usuario.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty()){
                    iniciarSesion();

                if (recordar_usuario.isChecked()) {
                    SharedPreferences preferences = Ingresar.this.getSharedPreferences("Recuerdo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("RECUERDO","SI");
                    editor.putString("USER", usuario.getText().toString());
                    editor.apply();
                }


                } else
                    Toast.makeText(getApplicationContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitActivity.exitApplication(this);
    }

    public boolean enviar_datos(String numero, String contrasena, String usuario) {

        return recuperar_contrasena(numero, "- FEMINA RECUPERACION - \n Tus datos de acceso son: \n" +
                " Usuario: " + usuario + "\n" +
                " Contraseña: " + contrasena);
    }

    public void iniciarSesion() {
        user = new Usuarios();
        user.setUsuario(usuario.getText().toString());
        user.setContrasena(contrasena.getText().toString());
        UsuariosBD us = new UsuariosBD(user, this, "Loguin");
        us.execute();
    }

}