package com.alejandro.android.femina.Fragments.icono;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.Adapter.AdapterIconos;
import com.alejandro.android.femina.Entidades.EntidadIcono;
import com.alejandro.android.femina.R;

import java.util.ArrayList;

public class IconoFragment extends Fragment {

    private IconoViewModel iconoViewModel;
    public ListView lvIconos;
    public AdapterIconos adaptador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        iconoViewModel =
                ViewModelProviders.of(this).get(IconoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_icono, container, false);
        //final TextView textView = root.findViewById(R.id.txt_icono);
        iconoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                lvIconos = (ListView) root.findViewById(R.id.lvIconos);


                adaptador = new AdapterIconos(getContext(), GetArrayIconos());
                lvIconos.setAdapter(adaptador);
            }
        });
        return root;
    }

    private ArrayList<EntidadIcono> GetArrayIconos(){
        ArrayList<EntidadIcono> listIconos = new ArrayList<>();

        listIconos.add(new EntidadIcono(R.drawable.icono_horoscopo, "HOROSCOPO"));
        listIconos.add(new EntidadIcono(R.drawable.icono_moda, "MODA"));
        listIconos.add(new EntidadIcono(R.drawable.icono_recetas, "RECETAS"));
        listIconos.add(new EntidadIcono(R.drawable.icono_peluqueria, "PELUQUERIA"));

        return listIconos;
    }

}
