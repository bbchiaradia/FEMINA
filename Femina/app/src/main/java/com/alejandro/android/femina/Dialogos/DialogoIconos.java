package com.alejandro.android.femina.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.Entidades.EntidadIcono;
import com.alejandro.android.femina.Fragments.que_hacer.Admin.Alta_Modificacion.QueHacerAMFragment;
import com.alejandro.android.femina.Fragments.que_hacer.User.Detalle.QueHacerDetalleFragment;
import com.alejandro.android.femina.R;

public class DialogoIconos extends AppCompatDialogFragment {

    private EntidadIcono art;

    public DialogoIconos(EntidadIcono art){

        this.art = art;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("")
                .setItems(R.array.gestion_icono, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle datosAEnviar = new Bundle();

                        switch (which){

                            case 0:

                           

                                break;


                        }
                    }
                });
        return builder.create();
    }

}
