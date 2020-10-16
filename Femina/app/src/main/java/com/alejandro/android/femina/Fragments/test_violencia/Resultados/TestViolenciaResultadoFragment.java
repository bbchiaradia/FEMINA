package com.alejandro.android.femina.Fragments.test_violencia.Resultados;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alejandro.android.femina.R;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Test.TestGrabarResultado;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.Pantallas_exteriores.Registrarse;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

import java.io.Serializable;


public class TestViolenciaResultadoFragment extends Fragment {

    private TestViolenciaResultadoViewModel violenciaResultadoViewModel;
    private int score;
    private Session ses;
    private TextView txt_consejos_resultado_test;
    private TextView txt_titulo_resultado_test;
    private Button finalizarTest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        violenciaResultadoViewModel =
                ViewModelProviders.of(this).get(TestViolenciaResultadoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_test_violencia_resultado, container, false);
        //final TextView textView = root.findViewById(R.id.txt_test_violencia);
        ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();
        finalizarTest = (Button) root.findViewById(R.id.btnFinalizarTest);
        txt_titulo_resultado_test = (TextView) root.findViewById(R.id.txt_titulo_resultado_test);
        txt_consejos_resultado_test = (TextView) root.findViewById(R.id.txt_consejos_resultado_test);

        if(getArguments()!=null){
            score= getArguments().getInt("SCORE", 0);
            if(score ==2) {

                txt_consejos_resultado_test.setText("Si bien en apariencia tu relación no presenta señales de alerta, puedes usar estas preguntas como guía no solo para vos sino también para prestar atención a las relaciones de parejas que te rodean (tus amigas , tus papas , etc.");
                txt_titulo_resultado_test.setText("Tu relacion no presenta señales de violencia.¡Disfruta tu noviazgo!");
                TestGrabarResultado test = new TestGrabarResultado(ses.getId_usuario(),1,0);
                test.execute();
            }
            if(score == 1){
                txt_consejos_resultado_test.setText("Es muy probable que te encuentres en una relación de noviazgo violento.Los actos de violencia se dan en cualquier contexto y son cada vez mas frecuentes e intensos. Muy probablemente despues de cada agresión te pida perdón y te promete que no volverá a pasar.Esta etapa es difícil porque podes sentir miedo y vergüenza pero mas peligroso es continuar con esa relación");
                txt_titulo_resultado_test.setText("¡Pedi ayuda! Estas viviendo una relacion violenta");
                TestGrabarResultado test = new TestGrabarResultado(ses.getId_usuario(),1,1);
                test.execute();

            }
        }

        finalizarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        violenciaResultadoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
