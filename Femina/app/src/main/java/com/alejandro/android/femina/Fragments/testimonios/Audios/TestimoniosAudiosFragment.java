package com.alejandro.android.femina.Fragments.testimonios.Audios;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alejandro.android.femina.Adaptadores.AdaptadorAudios;
import com.alejandro.android.femina.Entidades.Audios;
import com.alejandro.android.femina.Entidades.Categorias;
import com.alejandro.android.femina.R;

import java.util.ArrayList;
import java.util.List;

public class TestimoniosAudiosFragment extends Fragment {
    private RecyclerView recyclerViewAudio;

    private TestimoniosAudiosViewModel testimoniosAudiosViewModel;
    private List<Audios> listaAudios;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosAudiosViewModel =
                ViewModelProviders.of(this).get(TestimoniosAudiosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testimonios_audios, container, false);
        //final TextView textView = root.findViewById(R.id.txt_testimonios);
        testimoniosAudiosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        recyclerViewAudio = root.findViewById(R.id.recyclerView_audio);
        recyclerViewAudio.setLayoutManager(new GridLayoutManager(getContext(), 1));

        listaAudios = new ArrayList<>();
        Categorias cat = new Categorias();
        cat.setId_categoria(1);
        listaAudios.add(new Audios("Superacion",cat,"http://infinityandroid.com/music/good_times.mp3"));

        RecyclerView.Adapter adaptador = new AdaptadorAudios(getContext(),listaAudios);
        recyclerViewAudio.setAdapter(adaptador);

        return root;
    }
}