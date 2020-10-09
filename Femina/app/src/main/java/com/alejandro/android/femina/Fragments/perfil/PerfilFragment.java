package com.alejandro.android.femina.Fragments.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.BD.Usuarios.UsuariosBD;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Pantallas_exteriores.Registrarse;
import com.alejandro.android.femina.R;

public class PerfilFragment extends Fragment {

    private Spinner sexo;
    private Button btn_modificarPerfil;
    private EditText txt_nombre, txt_apellido, txt_usuario, txt_contrasena, txt_telefono;
    private Usuarios user;
    private char sex;
    private String mensaje;
    private UsuariosBD usuariosBD;


    private PerfilViewModel perfilViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                ViewModelProviders.of(this).get(PerfilViewModel.class);
         View root = inflater.inflate(R.layout.fragment_perfil, container, false);


        sexo = (Spinner) root.findViewById(R.id.spn_sexo_modificar);
        btn_modificarPerfil = (Button) root.findViewById(R.id.btn_guardar_usuario);
        txt_nombre = (EditText) root.findViewById(R.id.txt_nombre);
        txt_apellido = (EditText) root.findViewById(R.id.txt_apellido);
        txt_usuario = (EditText) root.findViewById(R.id.txt_usuario);
        txt_contrasena = (EditText) root.findViewById(R.id.txt_contrasenia);
        txt_telefono = (EditText) root.findViewById(R.id.txt_telefono);
        txt_usuario = (EditText) root.findViewById(R.id.txt_usuario);



        UsuariosBD usu = new UsuariosBD(getContext(),txt_nombre,txt_apellido,txt_contrasena,txt_telefono,txt_usuario,sexo,"Listar");
        usu.execute();

        btn_modificarPerfil.setOnClickListener(new View.OnClickListener() {
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

                        usuariosBD = new UsuariosBD(user, getContext(),"Modificar");
                        usuariosBD.execute();

                    }
                    else
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();


            }
        });

        return root;
    }
}