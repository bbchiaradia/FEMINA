package com.alejandro.android.femina.Dialogos;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Fragments.testimonios.Admin.AMVideos.TestimoniosAMVideosFragment;
import com.alejandro.android.femina.R;

public class DialogoAMVideos extends AppCompatDialogFragment {

//    private ContactosEmergencia cont;
    private Videos videos;
    private Context cntxt;

    public DialogoAMVideos(Videos vi) {

        this.videos = vi;

    }

    public DialogoAMVideos(Context context) {
        this.cntxt = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elegi una accion!");
        builder.setItems(R.array.gestion_video, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //ContactosBD contt;
                Bundle datosAEnviar = new Bundle();


                switch (which) {

                    case 0:

                        Toast.makeText(cntxt,"Click On Insertar" ,Toast.LENGTH_SHORT).show();
                        break;

                    case 1:

                        SharedPreferences preferencias = getContext().getSharedPreferences("accion_videos", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putString("accion", "Modificar");
                        editor.apply();

                        datosAEnviar.putString("titulo", videos.getTitulo());
                        datosAEnviar.putString("url",videos.getUrl_video());


                        //Toast.makeText(cntxt,"Click On Modificar" ,Toast.LENGTH_SHORT).show();
                        Fragment fragmento = new TestimoniosAMVideosFragment();
                        fragmento.setArguments(datosAEnviar);
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                        break;


                    case 2:
                        Toast.makeText(cntxt,"Click On Eliminar" ,Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        });
        return builder.create();
    }


}


