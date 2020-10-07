package com.alejandro.android.femina.Fragments.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.BD.Usuarios.UsuariosBD;
import com.alejandro.android.femina.Entidades.Usuarios;
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

        UsuariosBD usu = new UsuariosBD(getContext(),txt_nombre,txt_apellido,"Listar");
        usu.execute();


        perfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {



            }
        });
        return root;
    }
}