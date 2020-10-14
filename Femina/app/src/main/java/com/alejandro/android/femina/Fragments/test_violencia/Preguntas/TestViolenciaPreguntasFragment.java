package com.alejandro.android.femina.Fragments.test_violencia.Preguntas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.BD.Preguntas.PreguntasBD;
import com.alejandro.android.femina.Entidades.PreguntasTest;
import com.alejandro.android.femina.Fragments.test_violencia.Resultados.TestViolenciaResultadoFragment;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

import java.util.ArrayList;
import java.util.Iterator;

public class TestViolenciaPreguntasFragment extends Fragment {


    private TestViolenciaPreguntasViewModel testViolenciaPreguntasViewModel;
    private Button btn_proxima_pregunta, btn_pregunta_anterior;
    private int numeroPregunta = 1;
    private TextView txtPregunta1 ;
    private RadioGroup radioGroup;
    private RadioButton radioButton2,rbNo;
    private int contadorSi;
    private ListView lvlPreguntas;
    Boolean respuesta1 ,respuesta2 , respuesta3 ,respuesta4 ,respuesta5;
    private ArrayList<PreguntasTest> arrayPreguntas = new ArrayList<PreguntasTest>();
    private int i ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testViolenciaPreguntasViewModel =
                ViewModelProviders.of(this).get(TestViolenciaPreguntasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_test_violencia_pregunta, container, false);

        contadorSi= 0;
        btn_proxima_pregunta = (Button) root.findViewById(R.id.btn_proxima_pregunta);
        txtPregunta1 = (TextView) root.findViewById(R.id.txtPregunta1);
        btn_pregunta_anterior = (Button) root.findViewById(R.id.btn_pregunta_1_anterior);
        radioGroup = (RadioGroup) root.findViewById(R.id.radioGroupSiNoTest);
        radioButton2 = (RadioButton) root.findViewById(R.id.radioButton2);



        arrayPreguntas = (ArrayList<PreguntasTest>) getArguments().getSerializable("preguntas");

        for(int i=0;i<arrayPreguntas.size();i++){
            PreguntasTest preg = new PreguntasTest();
            preg = (PreguntasTest) arrayPreguntas.get(i);
        }

        radioButton2.setChecked(true);
        rbNo =(RadioButton) root.findViewById(R.id.rbNo);
        numeroPregunta = 1;
        txtPregunta1.setText(arrayPreguntas.get(0).getTexto_pregunta());
        final int cantidadPreguntas= arrayPreguntas.size();

        System.out.println("");
            btn_proxima_pregunta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                 for ( i= 0 ; i<cantidadPreguntas  ;i++ ) {
                     System.out.println("estoy en proxima pregunta" + i);
                     if (numeroPregunta == i) {
                         txtPregunta1.setText(arrayPreguntas.get(i).getTexto_pregunta());
                         numeroPregunta = i + 1;
                         if (radioButton2.isChecked()) {
                             contadorSi = contadorSi + 1;
                             respuesta1 = true;
                         } else {
                             respuesta1 = false;
                         }
                         break;
                     }
                     if(numeroPregunta == (cantidadPreguntas) ){
                         if (radioButton2.isChecked()) {
                             contadorSi = contadorSi + 1;
                             //ultimaRtaSi=true;
                         }
                         numeroPregunta = 8;
                         FragmentManager fm = getActivity().getSupportFragmentManager();
                         FragmentTransaction fragmentTransaction = fm.beginTransaction();
                         TestViolenciaResultadoFragment fragment = new TestViolenciaResultadoFragment();
                         fragmentTransaction.add(R.id.content_main, fragment).commit();
                         Bundle bundle = new Bundle();
                         if (contadorSi >= 1) {
                             bundle.putInt("SCORE", 1);//Envio 1 si es una relacion peligrosa
                             fragment.setArguments(bundle);
                         } else {
                             bundle.putInt("SCORE", 2);//Envio 2 si es una relacion peligrosa
                             fragment.setArguments(bundle);
                         }
                     }


                 }
                }
            });

        btn_pregunta_anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for ( i= 1 ; i<=cantidadPreguntas  ;i++ ) {
                    System.out.println("ENTRO FOREACH  pregunta anterior" + i + " Numero pregunta " + numeroPregunta);
                    if (numeroPregunta == i && numeroPregunta >1) {
                        System.out.println("ingreso NICO" + i + " Numero pregunta " + numeroPregunta);
                        txtPregunta1.setText(arrayPreguntas.get(i-2).getTexto_pregunta());
                        numeroPregunta = i-1;
                        if(respuesta1== true){
                            contadorSi = contadorSi-1;
                            radioButton2.setChecked(true);
                        }else{
                            rbNo.setChecked(true);
                        }
                        break;
                    }
                }
            }
        });








        testViolenciaPreguntasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //    textView.setText(s);
            }
        });
        return root;
    }

}
