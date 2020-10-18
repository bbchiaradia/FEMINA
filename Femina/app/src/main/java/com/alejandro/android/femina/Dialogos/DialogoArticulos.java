package com.alejandro.android.femina.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.BD.Articulos.ArticulosBD;
import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.Fragments.que_hacer.User.Detalle.QueHacerDetalleFragment;
import com.alejandro.android.femina.R;

public class DialogoArticulos extends AppCompatDialogFragment {

    private Articulos art;

    public DialogoArticulos(Articulos art){

        this.art = art;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("")
                .setItems(R.array.Dialogo_articulos, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){

                            case 0:

                                Bundle datosAEnviar = new Bundle();

                                datosAEnviar.putInt("id_articulo", art.getId_articulo());

                                Fragment fragmento = new QueHacerDetalleFragment();
                                fragmento.setArguments(datosAEnviar);
                                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
                                break;

                            case 1:
                                break;

                            case 2:
                                break;
                        }
                    }
                });
        return builder.create();
    }

}
