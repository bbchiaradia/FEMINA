package com.alejandro.android.femina.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Servicio.CerrarNoti;
import com.alejandro.android.femina.Servicio.Servicio;
import com.alejandro.android.femina.Session.Session;
import com.alejandro.android.femina.Session.SessionContactos;

import java.util.ArrayList;

public class DialogoSOS extends AppCompatDialogFragment {


    private EditText contraseña;
    private String contrasena_,bien;
    private Context context;
    private String[] contactos;


    public DialogoSOS(Context c, String bien){
        this.context = c;
        this.bien = bien;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_sos, null);

        builder.setView(view);
        builder.setTitle(R.string.ingrese_contraseña);

        contraseña = view.findViewById(R.id.contraseña_sos);

        final Session session = new Session();
        session.setCt(context);
        session.cargar_session();

        SessionContactos sessionContactos = new SessionContactos();
        sessionContactos.setContext(context);
        sessionContactos.cargar_session();

        contactos = sessionContactos.getContactos();

        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                contrasena_ = contraseña.getText().toString();
                boolean validacion = false;

                if (!contrasena_.isEmpty()) {

                    String mensaje_completo = "";

                    if(contrasena_.equals(session.getContrasena())){

                        if(bien.equals("BIEN"))
                            mensaje_completo = session.getNombre() + " " + session.getApellido()
                    + " Se encuentra bien y fuera de peligro.";

                        if(bien.equals("MAL"))
                            mensaje_completo = session.getNombre() + " " + session.getApellido()
                                    + " Está fuera de peligro, pero no se encuentra bien.";

                        validacion = true;
                    }else
                        Toast.makeText(getContext(), "Contraseña incorrecta!", Toast.LENGTH_SHORT).show();

                    if(validacion){
                        enviar_mensaje(mensaje_completo);
                    }

                } else {
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();


    }

    public void enviar_mensaje(String mensaje){

        for(String s : contactos){
            try {
                String number = s;
                Log.d("CONTACTO",number);

                SmsManager smsManager = SmsManager.getDefault();

                ArrayList<String> msgArray = smsManager.divideMessage(mensaje);
                smsManager.sendMultipartTextMessage(number, null,msgArray, null, null);

            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }

        ((CerrarNoti)context).terminar_servicio();

    }


}
