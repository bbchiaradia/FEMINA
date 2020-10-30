package com.alejandro.android.femina.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.alejandro.android.femina.BD.Usuarios.UsuariosBD;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.R;

public class DialogoRecuperar extends AppCompatDialogFragment {

    private EditText usuario;
    private String usuario_;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_recuperacion, null);

        builder.setView(view);
        builder.setTitle(R.string.ingrese_usuario);


        usuario = view.findViewById(R.id.usuario_recuperacion);

        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                usuario_ = usuario.getText().toString();
                boolean validacion = true;

                if (!usuario_.isEmpty()) {

                    if(usuario_.length()< 3 || usuario_.length() > 8 ) {
                        Toast.makeText(getContext(), "El usuario debe contar con un mínimo y máximo de 3 y 8 caracteres respectivamente", Toast.LENGTH_SHORT).show();
                        validacion = false;
                    }


                    if(validacion){

                        Usuarios usuarios = new Usuarios();
                        usuarios.setUsuario(usuario_);

                        UsuariosBD usuariosBD = new UsuariosBD(getContext(),"EnviarMensaje",usuarios);
                        usuariosBD.execute();

                    }

                } else {
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return builder.create();


    }


}
