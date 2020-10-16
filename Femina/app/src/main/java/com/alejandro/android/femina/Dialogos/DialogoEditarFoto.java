package com.alejandro.android.femina.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.alejandro.android.femina.BD.Articulos.ArticulosBD;
import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.GestionImagen.GestionarImagen;
import com.alejandro.android.femina.R;

public class DialogoEditarFoto extends AppCompatDialogFragment {

    private int PICK_IMAGE_REQUEST = 1;
    public static final int RESULT_OK = -1;
    private Articulos art;

    public DialogoEditarFoto(Articulos ar){
        this.art = ar;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("")
                .setItems(R.array.gestion_foto, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){

                            case 0:
                                ArticulosBD usu = new ArticulosBD(getContext(),art,"EliminarFoto");
                                usu.execute();
                                break;

                            case 1:
                                Intent intent = new Intent(getContext(), GestionarImagen.class);
                                intent.putExtra("id_articulo",art.getId_articulo());
                                startActivity(intent);
                                break;
                        }
                    }
                });
        return builder.create();
    }

}
