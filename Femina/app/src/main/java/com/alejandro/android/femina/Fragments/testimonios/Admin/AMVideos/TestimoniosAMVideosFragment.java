package com.alejandro.android.femina.Fragments.testimonios.Admin.AMVideos;

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
import com.alejandro.android.femina.BD.Videos.VideosBD;
import com.alejandro.android.femina.Entidades.Categorias;
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
    private TextView idvideo;

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
        idvideo = (TextView) root.findViewById(R.id.txt_idvideo_am);

        id_video = -1;

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
            urlam.setText(recoveredVideoData.getString("url").replaceAll("/embed",""));
            spn.setSelection(obtenerPosicionItem(spn,recoveredVideoData.getString("catDesc")));
            //idvideo.setInputType(recoveredVideoData.getInt("idvideo"));
            id_video =recoveredVideoData.getInt("idvideo");
            Log.d("id_video","" + id_video);
        }

        guardar_cambios_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!tituloam.getText().toString().isEmpty() && !urlam.getText().toString().isEmpty()){
                    if(esUrlYoutube(urlam.getText().toString())) {
                        video = new Videos();
                        //video.setId_video(idvideo.getInputType());
                        video.setTitulo(tituloam.getText().toString());
                        Categorias idc = new Categorias();
                        //idc.setId_categoria(spn.getSelectedItemPosition() + 1);
                        idc.setDescripcion(spn.getSelectedItem().toString());
                        video.setIdCategoria(idc);
                        video.setUrl_video(formateoUrlYoutube(urlam.getText().toString()));
                        if (id_video != -1)
                            video.setId_video(id_video);

                        SharedPreferences preferences = getContext().getSharedPreferences("accion_videos", Context.MODE_PRIVATE);

                        if (preferences.getString("accion", "").equals("Modificar")) {
                            VideosBD videosBD = new VideosBD(video, getContext(), "Modificar");
                            videosBD.execute();
                        }

                        if (preferences.getString("accion", "").equals("Insertar")) {
                            VideosBD videosBD = new VideosBD(video, getContext(), "Insertar");
                            videosBD.execute();
                        }
                    } else {
                        Toast.makeText(getContext(),"La Url no pertenece a Youtube!",Toast.LENGTH_SHORT).show();
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

    public static String formateoUrlYoutube (String myurl){
        int pos = myurl.indexOf("&");
        if(pos > -1) {
            myurl = myurl.substring(0, pos);
        }
        if(myurl.contains("youtu.be")) {
            myurl = myurl.replace(".com/", ".com/embed/").replace("youtu.be/", "www.youtube.com/");
            myurl = myurl.replace(".com/", ".com/embed/");
        } else {
            if (myurl.contains("watch")) {
                myurl = myurl.replaceFirst(".com/", ".com/embed/").replace("watch?v=", "");
            } else {
                myurl = myurl.replaceFirst(".com/", ".com/embed/");
            }
        }
       return myurl;
    }

    public static boolean esUrlYoutube(String youTubeURl)
    {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern)){
            success = true;
        } else {
            // No es valida youtube URL
            success = false;
        }
        return success;
    }

}
