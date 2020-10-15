package com.alejandro.android.femina.Fragments.testimonios.Admin.AMVideos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.R;

import java.util.Set;

public class TestimoniosAMVideosFragment extends Fragment {

    private TestimoniosAMVideosViewModel testimoniosAMVideosViewModel;

    private Set<String> categorias;
    private String[] lista_Categorias;
    private Spinner spn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosAMVideosViewModel =
                ViewModelProviders.of(this).get(TestimoniosAMVideosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testimonios_videos_am, container, false);
        //final TextView textView = root.findViewById(R.id.txt_testimonios);
        testimoniosAMVideosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        spn = (Spinner) root.findViewById(R.id.spn_categoria_am);

        SharedPreferences preferences = getContext().getSharedPreferences("listita_categorias", Context.MODE_PRIVATE);
        categorias = preferences.getStringSet("categorias",null);
        lista_Categorias = new String[preferences.getInt("tamaño",0)];

        int i = 0;
        for(String s : categorias){
            lista_Categorias[i] = s;
            i++;
        }

        spn.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, lista_Categorias));


        return root;
    }
}