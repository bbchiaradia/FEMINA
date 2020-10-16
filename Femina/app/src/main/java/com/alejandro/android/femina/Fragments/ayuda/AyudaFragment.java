package com.alejandro.android.femina.Fragments.ayuda;

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

import com.alejandro.android.femina.Adaptadores.AdapterAyuda;
import com.alejandro.android.femina.BD.Ayuda.AyudaDB;
import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.Entidades.Ayuda;
import com.alejandro.android.femina.R;

import java.util.ArrayList;

public class AyudaFragment extends Fragment {

    private AyudaViewModel ayudaViewModel;
    public ListView lvAyuda;
    public AdapterAyuda adaptador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ayudaViewModel =
                ViewModelProviders.of(this).get(AyudaViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_ayuda, container, false);
        //final TextView textView = root.findViewById(R.id.txt_ayuda);
        ayudaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                lvAyuda = root.findViewById(R.id.lvAyuda);

                AyudaDB cont = new AyudaDB(getContext(),lvAyuda,"Listar");
                cont.execute();


            }
        });
        return root;
    }



}
