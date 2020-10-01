package com.alejandro.android.femina.Fragments.test_violencia.Preguntas;

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

public class TestViolenciaPreguntasFragment extends Fragment {

    private TestViolenciaPreguntasViewModel testViolenciaPreguntasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testViolenciaPreguntasViewModel =
                ViewModelProviders.of(this).get(TestViolenciaPreguntasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_test_violencia_pregunta, container, false);
        //final TextView textView = root.findViewById(R.id.txt_test_violencia);
        testViolenciaPreguntasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //    textView.setText(s);
            }
        });
        return root;
    }
}
