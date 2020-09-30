package com.alejandro.android.femina.ui.icono;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.R;

public class IconoFragment extends Fragment {

    private IconoViewModel iconoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        iconoViewModel =
                ViewModelProviders.of(this).get(IconoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_icono, container, false);
        //final TextView textView = root.findViewById(R.id.txt_icono);
        iconoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;
    }
}
