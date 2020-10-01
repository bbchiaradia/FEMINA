package com.alejandro.android.femina.Fragments.que_hacer.User.Detalle;

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

public class QueHacerDetalleFragment extends Fragment {

    private QueHacerDetalleViewModel queHacerDetalleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queHacerDetalleViewModel =
                ViewModelProviders.of(this).get(QueHacerDetalleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_que_hacer_detalle, container, false);
        //final TextView textView = root.findViewById(R.id.txt_que_hacer);
        queHacerDetalleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        return root;
    }
}