package com.alejandro.android.femina.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.alejandro.android.femina.BD.Audios.AudiosBD;
import com.alejandro.android.femina.Entidades.Audios;
import com.alejandro.android.femina.Fragments.testimonios.Admin.AMAudios.TestimoniosAMAudiosFragment;
import com.alejandro.android.femina.Fragments.testimonios.Audios.TestimoniosAudiosFragment;
import com.alejandro.android.femina.R;

//
// Created by Juan Manuel on 4/11/2020.
//
public class DialogoAMAudios  extends AppCompatDialogFragment {

    private Context context;
    private Audios audio;
    private TestimoniosAudiosFragment fragmentAudios;

    public  DialogoAMAudios (Audios a, TestimoniosAudiosFragment myFragment){
        this.audio = a;
        this.fragmentAudios = myFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elegi una accion!");
        builder.setItems(R.array.gestion_audio, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                AudiosBD audiosE;
                Bundle datosAEnviar = new Bundle();
                switch (which) {
                    case 0:

                        SharedPreferences preferencias = getContext().getSharedPreferences("accion_audios", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putString("accion", "Modificar");
                        editor.apply();

                        datosAEnviar.putString("titulo", audio.getTitulo());
                        datosAEnviar.putString("url",audio.getUrl_audio());
                        datosAEnviar.putString("catDesc", audio.getId_categoria().getDescripcion());
                        datosAEnviar.putInt("idaudio",audio.getId_audio());

                        Fragment fragmento = new TestimoniosAMAudiosFragment();
                        fragmento.setArguments(datosAEnviar);
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                        break;

                    case 1:
                        audiosE = new AudiosBD(audio,getContext(),"Eliminar",fragmentAudios);
                        audiosE.execute();
                        break;
                }
            }
        });
        return builder.create();
    }
}
