package com.alejandro.android.femina.Fragments.test_violencia.Principal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Preguntas.PreguntasBD;
import com.alejandro.android.femina.Dialogos.DialogoAEContactos;
import com.alejandro.android.femina.Entidades.PreguntasTest;
import com.alejandro.android.femina.Fragments.perfil.PerfilFragment;
import com.alejandro.android.femina.Fragments.test_violencia.Preguntas.TestViolenciaPreguntasFragment;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

import java.io.Serializable;
import java.util.ArrayList;

public class TestViolenciaFragment extends Fragment {

    private TestViolenciaViewModel testViolenciaViewModel;
    Button btnIniciarTest;
    private ListView lvlPreguntas;
    ArrayList<PreguntasTest> arrayPreguntas = new ArrayList<PreguntasTest>(); //declaro para ver las preguntas




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
                PreguntasBD cont = new PreguntasBD(getContext(), arrayPreguntas , lvlPreguntas);
                cont.execute();
                /*
                try {
                    Thread.sleep(4*1000);
                } catch (Exception e) {
                    System.out.println(e);
                }*/



                btnIniciarTest = (Button) root.findViewById(R.id.btnEmpezarTest);
                btnIniciarTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println( "PREGUNTAS LENGHT despues" + lvlPreguntas.getCount());

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


                    }
                });

            }
        });
        return root;
    }


}
