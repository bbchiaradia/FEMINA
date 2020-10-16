package com.alejandro.android.femina.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.alejandro.android.femina.BD.Articulos.ArticulosBD;
import com.alejandro.android.femina.R;

public class DialogoSINOArticulos extends AppCompatDialogFragment {

    public DialogoSINOArticulos(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Esta seguro?")
                .setItems(R.array.SINO, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){

                            case 0:
                                ArticulosBD art = new ArticulosBD(getContext(),"InsertNull");
                                art.execute();
                                break;

                            case 1:
                                break;
                        }
                    }
                });
        return builder.create();
    }

}
