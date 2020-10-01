package com.alejandro.android.femina.Fragments.que_hacer.Admin.Alta_Modificacion;

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

public class QueHacerAMFragment extends Fragment {

    private QueHacerAMViewModel queHacerAMViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queHacerAMViewModel =
                ViewModelProviders.of(this).get(QueHacerAMViewModel.class);
        View root = inflater.inflate(R.layout.fragment_que_hacer_am, container, false);
        //final TextView textView = root.findViewById(R.id.txt_que_hacer);
        queHacerAMViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        return root;
    }
}