package com.alejandro.android.femina.Fragments.testimonios.Admin.AMAudios;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alejandro.android.femina.BD.Audios.AudiosBD;
import com.alejandro.android.femina.Entidades.Audios;
import com.alejandro.android.femina.Entidades.Categorias;
import com.alejandro.android.femina.Fragments.testimonios.Audios.TestimoniosAudiosFragment;
import com.alejandro.android.femina.R;

import java.util.Set;

public class TestimoniosAMAudiosFragment extends Fragment {

    private TestimoniosAMAudiosViewModel testimoniosAMAudiosViewModel;
    private Set<String> categorias;
    private String[] lista_Categorias;
    private Spinner spn;
    private EditText tituloam,urlam;
    private Audios audio;
    private Button guardar_cambios_audios;
    private int id_audio;
    private TextView idaudio;
    private TestimoniosAudiosFragment fragmentAudios = new TestimoniosAudiosFragment();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testimoniosAMAudiosViewModel =
                ViewModelProviders.of(this).get(TestimoniosAMAudiosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_testimonios_audios_am, container, false);
        //final TextView textView = root.findViewById(R.id.txt_testimonios);
        testimoniosAMAudiosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        tituloam = (EditText) root.findViewById(R.id.txt_titulo_audio_am);
        urlam = (EditText) root.findViewById(R.id.txturl_am);
        spn = (Spinner) root.findViewById(R.id.spn_categoriaam);
        guardar_cambios_audios = (Button) root.findViewById(R.id.btn_update_audio);
        idaudio = (TextView) root.findViewById(R.id.txt_idaudio_am);


        id_audio = -1;

        final SharedPreferences preferences = getContext().getSharedPreferences("listita_categorias", Context.MODE_PRIVATE);
        categorias = preferences.getStringSet("categorias",null);
        lista_Categorias = new String[preferences.getInt("tamaño",0)];

        int i = categorias.size()-1;
        for(String s : categorias){
            lista_Categorias[i] = s;
            i--;
        }

        spn.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, lista_Categorias));

        final Bundle recoveredVideoData = getArguments();
        if (recoveredVideoData != null) {
            tituloam.setText(recoveredVideoData.getString("titulo"));
            urlam.setText(recoveredVideoData.getString("url"));
            spn.setSelection(obtenerPosicionItem(spn,recoveredVideoData.getString("catDesc")));
            id_audio =recoveredVideoData.getInt("idaudio");
            Log.d("id_audio","" + id_audio);
        }

        guardar_cambios_audios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!tituloam.getText().toString().isEmpty() && !urlam.getText().toString().isEmpty()){
                    if(esUrlAudio(urlam.getText().toString())) {
                        audio = new Audios();

                        audio.setTitulo(tituloam.getText().toString());
                        Categorias idc = new Categorias();
                        idc.setDescripcion(spn.getSelectedItem().toString());
                        audio.setId_categoria(idc);
                        audio.setUrl_audio(urlam.getText().toString());
                        if (id_audio != -1)
                            audio.setId_audio(id_audio);

                        SharedPreferences preferences = getContext().getSharedPreferences("accion_audios", Context.MODE_PRIVATE);

                        if (preferences.getString("accion", "").equals("Modificar")) {
                            AudiosBD audiosBD = new AudiosBD(audio,getContext(),"Modificar",fragmentAudios);
                            audiosBD.execute();
                        }

                        if (preferences.getString("accion", "").equals("Insertar")) {
                            AudiosBD audiosBD = new AudiosBD(audio,getContext(),"Insertar",fragmentAudios);
                            audiosBD.execute();
                        }
                    } else {
                        Toast.makeText(getContext(),"La Url no pertenece a un audio mp3!",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(),"Completa todos los campos!",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

      public static boolean esUrlAudio(String URl)
    {
        boolean success;
        String pattern = "^(http(s)?):\\/\\/(.*?)\\.(mp3)$";
        if (!URl.isEmpty() && URl.matches(pattern)){
            success = true;
        } else {
            // No es valida youtube URL
            success = false;
        }
        return success;
    }
}