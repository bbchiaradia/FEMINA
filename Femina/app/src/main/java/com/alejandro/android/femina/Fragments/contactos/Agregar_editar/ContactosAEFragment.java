package com.alejandro.android.femina.Fragments.contactos.Agregar_editar;

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

public class ContactosAEFragment extends Fragment {

    private ContactosAEViewModel contactosAEViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactosAEViewModel =
                ViewModelProviders.of(this).get(ContactosAEViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactos_ae, container, false);
        //final TextView textView = root.findViewById(R.id.txt_contactos);
        contactosAEViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //        textView.setText(s);
            }
        });
        return root;
    }
}