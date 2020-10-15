package com.alejandro.android.femina.Dialogos;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.BD.Contactos.ContactosBD;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.R;

import java.util.ArrayList;
import java.util.List;

public class DialogoAEContactos extends AppCompatDialogFragment {

    private ContactosEmergencia cont;
    private static ArrayList<String> arrayList = new ArrayList<>();

    public DialogoAEContactos(ContactosEmergencia c) {

        this.cont = c;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");
        builder.setItems(R.array.gestion_contacto, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                ContactosBD contt;
                Bundle datosAEnviar = new Bundle();

                switch (which) {

                    case 0:

                        Uri number = Uri.parse("tel:" + cont.getTelefono());
                    /*Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

                    PackageManager packageManager = getActivity().getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(callIntent,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;

                    if (isIntentSafe) {
                        startActivity(callIntent);
                    }*/


                        Intent intent = new Intent(Intent.ACTION_DIAL, number);

                        //String title = getResources().getString(R.string.chooser_title);
                        // Create intent to show chooser
                        Intent chooser = Intent.createChooser(intent, "");

                        // Verify the intent will resolve to at least one activity
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(chooser);
                        }

                        break;

                    case 1:

                        SharedPreferences preferencias = getContext().getSharedPreferences("accion_contactos", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putString("accion", "Modificar");
                        editor.apply();

                        datosAEnviar.putInt("idContacto", cont.getId_contacto_emergencia());
                        datosAEnviar.putString("nombre", cont.getNombre_contacto());
                        datosAEnviar.putString("telefono", cont.getTelefono());


                        Fragment fragmento = new ContactosAEFragment();
                        fragmento.setArguments(datosAEnviar);
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
                        break;


                    case 2:
                        contt = new ContactosBD(cont, getContext(), "Eliminar");
                        contt.execute();
                        break;
                }
            }
        });
        return builder.create();
    }


}