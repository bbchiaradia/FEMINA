package com.alejandro.android.femina.Fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.Fragments.ayuda.AyudaFragment;
import com.alejandro.android.femina.Fragments.contactos.Principal.ContactosFragment;
import com.alejandro.android.femina.Fragments.icono.IconoFragment;
import com.alejandro.android.femina.Fragments.perfil.PerfilFragment;
import com.alejandro.android.femina.Fragments.que_hacer.Principal.QueHacerFragment;
import com.alejandro.android.femina.Fragments.test_violencia.Principal.TestViolenciaFragment;
import com.alejandro.android.femina.Fragments.testimonios.Principal.TestimoniosFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.SessionContactos;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String LOGUEO = "NORMAL";


    CardView cardDatos, cardIcono, cardContactos, cardTest, cardQuehacer, cardTestimonios, cardAyuda;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                SessionContactos sessionContactos = new SessionContactos();
                sessionContactos.setContext(getContext());
                sessionContactos.cargar_session();

                String[] contactos;
                contactos = sessionContactos.getContactos();


                SharedPreferences preferences = getContext().getSharedPreferences("LOGUEO", Context.MODE_PRIVATE);
                LOGUEO = preferences.getString("LOGUEO", "NORMAL");

                int i = 1;

                for (String x : contactos) {
                    Log.d("Contacto", x);
                    i++;
                }


                cardDatos = root.findViewById(R.id.cardMisDatos);
                cardDatos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        PerfilFragment fragment = new PerfilFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                    }
                });

                cardIcono = root.findViewById(R.id.cardIconoInicio);
                cardIcono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        IconoFragment fragment = new IconoFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                    }
                });

                cardContactos = root.findViewById(R.id.cardContactoInicio);
                cardContactos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        ContactosFragment fragment = new ContactosFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                    }
                });


                cardTest = root.findViewById(R.id.cardTestInicio);
                cardTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        TestViolenciaFragment fragment = new TestViolenciaFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                    }
                });


                cardQuehacer = root.findViewById(R.id.cardQueHacerInicio);
                cardQuehacer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        QueHacerFragment fragment = new QueHacerFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                    }
                });


                cardTestimonios = root.findViewById(R.id.cardTestimoniosIni);
                cardTestimonios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        TestimoniosFragment fragment = new TestimoniosFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                    }
                });


                cardAyuda = root.findViewById(R.id.cardAyudaInicio);
                cardAyuda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        AyudaFragment fragment = new AyudaFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                    }
                });


                if (LOGUEO.equals("NORMAL")) {
                    cardDatos.setVisibility(View.VISIBLE);
                    cardIcono.setVisibility(View.VISIBLE);
                    cardContactos.setVisibility(View.VISIBLE);
                    cardTest.setVisibility(View.VISIBLE);
                    cardQuehacer.setVisibility(View.VISIBLE);
                    cardTestimonios.setVisibility(View.VISIBLE);
                    cardAyuda.setVisibility(View.VISIBLE);
                } else {
                    cardDatos.setVisibility(View.GONE);
                    cardIcono.setVisibility(View.GONE);
                    cardContactos.setVisibility(View.GONE);
                    cardTest.setVisibility(View.GONE);
                    cardQuehacer.setVisibility(View.GONE);
                    cardTestimonios.setVisibility(View.GONE);
                    cardAyuda.setVisibility(View.GONE);
                }

            }
        });

        return root;
    }
}