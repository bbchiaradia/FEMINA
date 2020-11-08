package com.alejandro.android.femina.Fragments.test_violencia.Principal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Preguntas.PreguntasBD;
import com.alejandro.android.femina.BD.ResultadoTest.ResultadoDB;
import com.alejandro.android.femina.BD.Test.TestDB;
import com.alejandro.android.femina.Entidades.PreguntasTest;
import com.alejandro.android.femina.Entidades.ResultadosTest;
import com.alejandro.android.femina.Entidades.Test;
import com.alejandro.android.femina.Fragments.test_violencia.Preguntas.TestViolenciaPreguntasFragment;
import com.alejandro.android.femina.R;

import java.io.Serializable;
import java.util.ArrayList;

public class TestViolenciaFragment extends Fragment {

    private TestViolenciaViewModel testViolenciaViewModel;
    Button btnIniciarTest;
    private ListView lvlPreguntas;
    ArrayList<PreguntasTest> arrayPreguntas = new ArrayList<PreguntasTest>(); //declaro para ver las preguntas
    private Spinner spinner_test;
    private int seleccion;
    private TextView textResultadoTest;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testViolenciaViewModel =
                ViewModelProviders.of(this).get(TestViolenciaViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_test_violencia, container, false);
        //final TextView textView = root.findViewById(R.id.txt_test_violencia);
        testViolenciaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            //    textView.setText(s);
                lvlPreguntas = (ListView) root.findViewById(R.id.lvlPreguntas);
                spinner_test = (Spinner) root.findViewById(R.id.sp_test);
                textResultadoTest = (TextView) root.findViewById(R.id.txtResultadoTestUsuario);

                TestDB testDB = new TestDB(getContext(),spinner_test);
                testDB.execute();



               //PreguntasBD cont = new PreguntasBD(getContext(), arrayPreguntas , lvlPreguntas , 1 /*(Integer) spinner_test.getSelectedItem()*/);
                //cont.execute();

                seleccion = 0;
                btnIniciarTest = (Button) root.findViewById(R.id.btnEmpezarTest);
                btnIniciarTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println( "PREGUNTAS LENGHT despues" + lvlPreguntas.getCount());
                            if(lvlPreguntas.getCount() > 0) {
                                ArrayList<PreguntasTest> arrayPreguntas = new ArrayList<PreguntasTest>();
                                PreguntasTest preg = new PreguntasTest();
                                for (int i = 0; i < lvlPreguntas.getCount(); i++) {
                                    arrayPreguntas.add((PreguntasTest) lvlPreguntas.getItemAtPosition(i));
                                }
                                Fragment frgmnt = new TestViolenciaPreguntasFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("preguntas", (Serializable) arrayPreguntas);
                                frgmnt.setArguments(bundle);

                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                //TestViolenciaPreguntasFragment fragment = new TestViolenciaPreguntasFragment();
                                fragmentTransaction.add(R.id.content_main, frgmnt).commit();
                                ProgressDialog progressDialog = new ProgressDialog(getContext());
                                progressDialog.setTitle("Progreso");
                                progressDialog.setMessage("Estamos progresando....");
                                progressDialog.setMax(100);
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            }else{
                                Toast.makeText(root.getContext(),"El Test no presenta preguntas cargadas ",Toast.LENGTH_SHORT).show();
                            }


                    }
                });

                spinner_test.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {

                            @SuppressLint("WrongViewCast")
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                             //if( seleccion > 0){
                                Test test = (Test) spinner_test.getSelectedItem();

                                ResultadoDB resu = new ResultadoDB(test.getId_test() ,getContext(),"ConsultarTestUsuario",textResultadoTest );
                                  resu.execute();

                                  PreguntasBD cont = new PreguntasBD(getContext(), arrayPreguntas , lvlPreguntas , test.getId_test() );
                                  cont.execute();
                              //}
                               // seleccion= seleccion+1 ;

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                Toast.makeText(root.getContext(),"TESTIMONIOS",Toast.LENGTH_SHORT).show();
                            }
                            //add some code here
                        }
                );

            }
        });
        return root;
    }


}
