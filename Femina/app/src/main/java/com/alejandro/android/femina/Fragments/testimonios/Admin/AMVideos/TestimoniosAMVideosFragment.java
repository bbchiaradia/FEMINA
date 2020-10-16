package com.alejandro.android.femina.Fragments.testimonios.Admin.AMVideos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.R;

import java.util.Set;

public class TestimoniosAMVideosFragment extends Fragment {

    private TestimoniosAMVideosViewModel testimoniosAMVideosViewModel;

    private Set<String> categorias;
    private String[] lista_Categorias;
    private Spinner spn;
    private EditText tituloam,urlam;
    private Videos video;
    private Button guardar_cambios_videos;
    private int id_video;
    private TestimoniosAMVideosViewModel amVideosViewModel;
    private String initializeItem;

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
        tituloam = (EditText) root.findViewById(R.id.txt_titulo_video_am);
        urlam = (EditText) root.findViewById(R.id.txt_url_am);
        spn = (Spinner) root.findViewById(R.id.spn_categoria_am);
        guardar_cambios_videos = (Button) root.findViewById(R.id.btn_update_video);

        id_video = -1;

        SharedPreferences preferences = getContext().getSharedPreferences("listita_categorias", Context.MODE_PRIVATE);
        categorias = preferences.getStringSet("categorias",null);
        lista_Categorias = new String[preferences.getInt("tamaño",0)];

        int i = 0;
        for(String s : categorias){
            lista_Categorias[i] = s;
            i++;
        }
        spn.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, lista_Categorias));

        final Bundle recoveredVideoData = getArguments();
        if (recoveredVideoData != null) {
            tituloam.setText(recoveredVideoData.getString("titulo"));
            urlam.setText(recoveredVideoData.getString("url").replaceAll("/embed",""));
            spn.setSelection(obtenerPosicionItem(spn,recoveredVideoData.getString("catDesc")));
        }



        return root;
    }

        //Método para obtener la posición de un ítem del spinner
    public static int obtenerPosicionItem(Spinner spinner, String categoria) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String categoria`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(categoria)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }
}
