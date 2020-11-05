package com.alejandro.android.femina.Fragments.secuencia;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.Adaptadores.AdapterAyuda;
import com.alejandro.android.femina.BD.Ayuda.AyudaDB;
import com.alejandro.android.femina.BD.Secuencias.SecuenciasBD;
import com.alejandro.android.femina.Entidades.Secuencias;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Fragments.ayuda.AyudaViewModel;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

import java.util.ArrayList;

public class SecuenciaFragment extends Fragment {

    private SecuenciaViewModel secuenciaViewModel;

    private ImageButton arriba,derecha,abajo,izquierda;
    private Button guardar_secuencia,modificar;
    private EditText secuencia;
    private Switch activar_secuencia;
    private TextView id_secuencia;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        secuenciaViewModel =
                ViewModelProviders.of(this).get(SecuenciaViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_secuencia, container, false);

        arriba = (ImageButton) root.findViewById(R.id.btn_arriba);
        abajo = (ImageButton) root.findViewById(R.id.btn_abajo);
        derecha = (ImageButton) root.findViewById(R.id.btn_derecha);
        izquierda = (ImageButton) root.findViewById(R.id.btn_izquierda);

        id_secuencia = (TextView) root.findViewById(R.id.txt_id_secuencia);

        secuencia = (EditText)root.findViewById(R.id.txt_secuencia);

        guardar_secuencia = (Button) root.findViewById(R.id.btn_guardar_secuencia);
        modificar = (Button) root.findViewById(R.id.btn_habilitar_secuencia);

        activar_secuencia = (Switch) root.findViewById(R.id.activar_secuencia);

        Session session = new Session();
        session.setCt(getContext());
        session.cargar_session();


        Secuencias secuencias = new Secuencias();
        Usuarios usuarios = new Usuarios();
        usuarios.setId_usuario(session.getId_usuario());
        secuencias.setId_usuario(usuarios);

        SecuenciasBD secuenciasBD = new SecuenciasBD(secuencias,getContext(),secuencia,activar_secuencia,
                id_secuencia,"TraerSecuencia");
        secuenciasBD.execute();


        return root;
    }



}
