package com.alejandro.android.femina.Fragments.contactos.Seleccion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.R;

public class ContactosSeleccionFragment extends Fragment {

    private ContactosSeleccionViewModel contactosSeleccionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactosSeleccionViewModel =
                ViewModelProviders.of(this).get(ContactosSeleccionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactos_seleccionar, container, false);
        //final TextView textView = root.findViewById(R.id.txt_contactos);
        contactosSeleccionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //        textView.setText(s);
            }
        });
        return root;
    }
}