package com.alejandro.android.femina.Pantallas_exteriores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alejandro.android.femina.BD.Usuarios.UsuariosBD;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

public class Registrarse extends AppCompatActivity {

    private Spinner sexo;
    private Button btn_registro;
    private EditText txt_nombre, txt_apellido, txt_usuario, txt_contrasena, txt_telefono;
    private Usuarios user;
    private char sex;
    private String mensaje;
    private UsuariosBD usuariosBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_registrarse);

        sexo = (Spinner) findViewById(R.id.spn_sexo_registrarse);
        btn_registro = (Button) findViewById(R.id.btn_confirmar_registro);
        txt_nombre = (EditText) findViewById(R.id.txt_nombre_registrarse);
        txt_apellido = (EditText) findViewById(R.id.txt_apellido_registrarse);
        txt_usuario = (EditText) findViewById(R.id.txt_usuario_registrarse);
        txt_contrasena = (EditText) findViewById(R.id.txt_contraseña_registrarse);
        txt_telefono = (EditText) findViewById(R.id.txt_telefono_registrarse);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sexo.setAdapter(adapter);



        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txt_nombre.getText().toString().isEmpty() && !txt_apellido.getText().toString().isEmpty()
                        && !txt_usuario.getText().toString().isEmpty() && !txt_contrasena.getText().toString().isEmpty()
                && !txt_telefono.getText().toString().isEmpty()) {


                    if(sexo.getSelectedItem().toString().equals("Masculino"))
                        sex = 'M';

                    if(sexo.getSelectedItem().toString().equals("Femenino"))
                        sex = 'F';

                    if(sexo.getSelectedItem().toString().equals("Otro"))
                        sex = 'O';

                    user = new Usuarios();
                    user.setUsuario(txt_usuario.getText().toString());
                    user.setContrasena(txt_contrasena.getText().toString());
                    user.setNombre(txt_nombre.getText().toString());
                    user.setApellido(txt_apellido.getText().toString());
                    user.setTelefono(txt_telefono.getText().toString());
                    user.setSexo(sex);
                    mensaje = user.validarDatosPerfil();

                    if(mensaje.equals("si")){

                        usuariosBD = new UsuariosBD(user,Registrarse.this,"Insertar");
                        usuariosBD.execute();

                    }
                    else
                        Toast.makeText(Registrarse.this, mensaje, Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(Registrarse.this, "Complete todos los campos!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}