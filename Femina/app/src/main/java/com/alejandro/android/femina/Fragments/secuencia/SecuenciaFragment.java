package com.alejandro.android.femina.Fragments.secuencia;


import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

    private ImageButton arriba, derecha, abajo, izquierda,borrar;
    private Button guardar_secuencia, modificar;
    private EditText secuencia;
    private Switch activar_secuencia;
    private TextView id_secuencia,largo_,secuencia_string_;
    private int largo,id_sec;
    private String secuencia_string,secuencia_edit;
    private SecuenciaFragment secuenciaFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        secuenciaViewModel =
                ViewModelProviders.of(this).get(SecuenciaViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_secuencia, container, false);

        arriba = (ImageButton) root.findViewById(R.id.btn_arriba_secuencia);
        abajo = (ImageButton) root.findViewById(R.id.btn_abajo_secuencia);
        derecha = (ImageButton) root.findViewById(R.id.btn_derecha_secuencia);
        izquierda = (ImageButton) root.findViewById(R.id.btn_izquierda_secuencia);
        borrar = (ImageButton) root.findViewById(R.id.btn_borrar);

        id_secuencia = (TextView) root.findViewById(R.id.txt_id_secuencia);
        largo_ = (TextView) root.findViewById(R.id.largo_);
        secuencia_string_ = (TextView) root.findViewById(R.id.secuencia_);

        secuencia = (EditText) root.findViewById(R.id.txt_secuencia);

        guardar_secuencia = (Button) root.findViewById(R.id.btn_guardar_secuencia);
        modificar = (Button) root.findViewById(R.id.btn_habilitar_secuencia);

        activar_secuencia = (Switch) root.findViewById(R.id.activar_secuencia);

        secuencia.setTransformationMethod(PasswordTransformationMethod.getInstance());

        secuenciaFragment = this;

        setDisabled();

        secuencia_string = "";
        secuencia_edit = "";

        Session session = new Session();
        session.setCt(getContext());
        session.cargar_session();


        guardar_secuencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean insertar = true;

                if(largo<3 || secuencia_edit.isEmpty()){
                    Toast.makeText(getContext(),"La secuencia debe contar un " +
                            "minimo de 3 combinaciones", Toast.LENGTH_SHORT).show();
                    insertar = false;
                }

                if(insertar){
                    Secuencias secuencias = new Secuencias();
                    secuencias.setId_secuencia(id_sec);
                    secuencias.setSecuencia(secuencia_string);

                    SecuenciasBD secuenciasBD = new SecuenciasBD(secuencias,getContext(),secuenciaFragment,"Modificar");
                    secuenciasBD.execute();
                }

            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnabled();

                Log.d("SECUENCIAA:", "" + secuencia_string_.getText().toString());
                Log.d("LARGOO:", "" + largo_.getText().toString());

                largo = Integer.parseInt(largo_.getText().toString());
                secuencia_string = secuencia_string_.getText().toString();
                id_sec = Integer.parseInt(id_secuencia.getText().toString());
                secuencia_edit = secuencia.getText().toString();

            }
        });

        arriba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(largo >= 4){
                    Toast.makeText(getContext(),"La secuencia debe contar un " +
                            "maximo de 4 combinaciones", Toast.LENGTH_SHORT).show();
                }else{
                    secuencia_string += "0";
                    largo ++;
                    secuencia_edit +="ARRIBA;";
                    secuencia.setText(secuencia_edit);
                }

                Log.d("SECUENCIA:", "" + secuencia_string);
                Log.d("LARGO:", "" + largo);
                Log.d("SECUENCIA EDIT:", secuencia_edit);

            }
        });

        derecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(largo >= 4){
                    Toast.makeText(getContext(),"La secuencia debe contar un " +
                            "maximo de 4 combinaciones", Toast.LENGTH_SHORT).show();
                }else{
                    secuencia_string += "1";
                    largo ++;
                    secuencia_edit +="DERECHA;";
                    secuencia.setText(secuencia_edit);
                }

                Log.d("SECUENCIA:", "" + secuencia_string);
                Log.d("LARGO:", "" + largo);
                Log.d("SECUENCIA EDIT:", secuencia_edit);
            }
        });

        abajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(largo >= 4){
                    Toast.makeText(getContext(),"La secuencia debe contar un " +
                            "maximo de 4 combinaciones", Toast.LENGTH_SHORT).show();
                }else{
                    secuencia_string += "2";
                    largo ++;
                    secuencia_edit +="ABAJO;";
                    secuencia.setText(secuencia_edit);
                }

                Log.d("SECUENCIA:", "" + secuencia_string);
                Log.d("LARGO:", "" + largo);
                Log.d("SECUENCIA EDIT:", secuencia_edit);

            }
        });

        izquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(largo >= 4){
                    Toast.makeText(getContext(),"La secuencia debe contar un " +
                            "maximo de 4 combinaciones", Toast.LENGTH_SHORT).show();
                }else{
                    secuencia_string += "3";
                    largo ++;
                    secuencia_edit +="IZQUIERDA;";
                    secuencia.setText(secuencia_edit);
                }

                Log.d("SECUENCIA:", "" + secuencia_string);
                Log.d("LARGO:", "" + largo);
                Log.d("SECUENCIA EDIT:", secuencia_edit);

            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                largo = 0;
                secuencia_string = "";
                secuencia_edit = "";
                secuencia.setText("");
            }
        });


        activar_secuencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activar_secuencia.isChecked()){
                    Secuencias secuencias = new Secuencias();
                    secuencias.setId_secuencia(id_sec);
                    SecuenciasBD secuenciasBD = new SecuenciasBD(secuencias,getContext(),secuenciaFragment,"Activar");
                    secuenciasBD.execute();
                }else{
                    Secuencias secuencias = new Secuencias();
                    secuencias.setId_secuencia(id_sec);
                    SecuenciasBD secuenciasBD = new SecuenciasBD(secuencias,getContext(),secuenciaFragment,"Desactivar");
                    secuenciasBD.execute();
                }
            }
        });

        Secuencias secuencias = new Secuencias();
        Usuarios usuarios = new Usuarios();
        usuarios.setId_usuario(session.getId_usuario());
        secuencias.setId_usuario(usuarios);

        SecuenciasBD secuenciasBD = new SecuenciasBD(secuencias, getContext(), secuencia, activar_secuencia,
                id_secuencia, largo_, secuencia_string_, "TraerSecuencia");
        secuenciasBD.execute();


        return root;
    }

    public void setEnabled() {
        secuencia.setTransformationMethod(SingleLineTransformationMethod.getInstance());
        activar_secuencia.setEnabled(true);
        arriba.setEnabled(true);
        abajo.setEnabled(true);
        derecha.setEnabled(true);
        izquierda.setEnabled(true);
        arriba.setEnabled(true);
        borrar.setEnabled(true);
    }

    public void setDisabled() {

        secuencia.setTransformationMethod(PasswordTransformationMethod.getInstance());
        secuencia.setEnabled(false);
        activar_secuencia.setEnabled(false);
        arriba.setEnabled(false);
        abajo.setEnabled(false);
        derecha.setEnabled(false);
        izquierda.setEnabled(false);
        arriba.setEnabled(false);
        borrar.setEnabled(false);
    }




}
