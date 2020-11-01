package com.alejandro.android.femina.Fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    ImageButton btn_misDatos_inicio;
    ImageButton btn_icono_inicio;
    ImageButton btn_contactos_inicio;
    ImageButton btn_quehacer_inicio;
    ImageButton btn_preguntas_inicio;
    ImageButton btn_test_inicio;
    ImageButton btn_testimonios_inicio;
    ImageButton btn_videos_inicio;

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

                int i = 1;

                for(String x : contactos){
                    Log.d("Contacto" , x );
                    i++;
                }

                btn_misDatos_inicio = (ImageButton) root.findViewById(R.id.btn_misdatos_inicio);
                btn_misDatos_inicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        PerfilFragment fragment = new PerfilFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                        // Toast.makeText(root.getContext(),"MI PERFIL",Toast.LENGTH_SHORT).show();
                    }
                });
                btn_icono_inicio = (ImageButton) root.findViewById(R.id.btn_icono_inicio);
                btn_icono_inicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        IconoFragment fragment = new IconoFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                        //  Toast.makeText(root.getContext(),"ICONO",Toast.LENGTH_SHORT).show();
                    }
                });
                btn_contactos_inicio = (ImageButton) root.findViewById(R.id.btn_contactos_inicio);
                btn_contactos_inicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        ContactosFragment fragment = new ContactosFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                        // Toast.makeText(root.getContext(),"CONTACTO",Toast.LENGTH_SHORT).show();
                    }
                });
                btn_quehacer_inicio = (ImageButton) root.findViewById(R.id.btn_quehacer_inicio);
                btn_quehacer_inicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        QueHacerFragment fragment = new QueHacerFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                        //   Toast.makeText(root.getContext(),"QUE HACER",Toast.LENGTH_SHORT).show();
                    }
                });
                btn_preguntas_inicio = (ImageButton) root.findViewById(R.id.btn_preguntas_inicio);
                btn_preguntas_inicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        AyudaFragment fragment = new AyudaFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                        //  Toast.makeText(root.getContext(),"PREGUNTAS",Toast.LENGTH_SHORT).show();
                    }
                });
                btn_test_inicio = (ImageButton) root.findViewById(R.id.btn_test_inicio);
                btn_test_inicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        TestViolenciaFragment fragment = new TestViolenciaFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                        //  Toast.makeText(root.getContext(),"TEST",Toast.LENGTH_SHORT).show();
                    }
                });
                btn_testimonios_inicio = (ImageButton) root.findViewById(R.id.btn_testimonios_inicio);
                btn_testimonios_inicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        TestimoniosFragment fragment = new TestimoniosFragment();
                        fragmentTransaction.replace(R.id.content_main, fragment).commit();
                        // Toast.makeText(root.getContext(),"TESTIMONIOS",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        return root;
    }
}