package com.alejandro.android.femina.Fragments.que_hacer.Principal;

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

public class QueHacerFragment extends Fragment {

    private QueHacerViewModel queHacerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queHacerViewModel =
                ViewModelProviders.of(this).get(QueHacerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_que_hacer_principal, container, false);
        //final TextView textView = root.findViewById(R.id.txt_que_hacer);
        queHacerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;
    }
}