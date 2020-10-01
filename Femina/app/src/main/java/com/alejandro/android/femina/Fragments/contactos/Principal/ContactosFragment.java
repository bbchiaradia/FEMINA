package com.alejandro.android.femina.Fragments.contactos.Principal;

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

public class ContactosFragment extends Fragment {

    private ContactosViewModel contactosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactosViewModel =
                ViewModelProviders.of(this).get(ContactosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactos, container, false);
        //final TextView textView = root.findViewById(R.id.txt_contactos);
        contactosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
        //        textView.setText(s);
            }
        });
        return root;
    }
}
