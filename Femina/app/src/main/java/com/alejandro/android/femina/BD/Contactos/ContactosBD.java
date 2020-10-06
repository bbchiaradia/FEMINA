package com.alejandro.android.femina.BD.Contactos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.Adaptadores.AdaptadorContactos;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Session.Session;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class ContactosBD extends AsyncTask<String, Void, String> {


    private ContactosEmergencia cont;
    private Usuarios user;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private ListView lcontactos;
    private TextView no_hay;
    private Boolean no_hay_cont;

    private static ArrayList<ContactosEmergencia> listaContactos = new ArrayList<ContactosEmergencia>();

    public ContactosBD(ContactosEmergencia contac, Context ct, String que) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        cont = new ContactosEmergencia();
        this.cont = contac;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
    }

    public ContactosBD(Context ct, ListView lv, TextView tx, String que) {
        listaContactos.clear();
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        user = new Usuarios();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        lcontactos = lv;
        no_hay = tx;
        no_hay_cont = true;
    }





    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;

        if (que_hacer.equals("Listar")) {

            response = "";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM ContactosEmergencia where idUsuario=" + ses.getId_usuario());

                while (rs.next()) {
                    no_hay_cont = false;
                    cont = new ContactosEmergencia();
                    cont.setId_contacto_emergencia(rs.getInt("idContactoEmergencia"));
                    user.setId_usuario(rs.getInt("idUsuario"));
                    cont.setId_usuario(user);
                    cont.setNombre_contacto(rs.getString("nombreContacto"));
                    cont.setTelefono(rs.getString("nroTelefono"));
                    listaContactos.add(cont);
                }

                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                response = "Conexion no exitosa";
            }


        }

        return response;


    }


    @Override
    protected void onPreExecute() {
        dialog.setMessage("Procesando...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String response) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }


        if(que_hacer.equals("Listar")) {
            final AdaptadorContactos adapter = new AdaptadorContactos(context, listaContactos);
            lcontactos.setAdapter(adapter);

            if(no_hay_cont) {
                no_hay.setVisibility(View.VISIBLE);
                lcontactos.setVisibility(View.GONE);
            }
            else {
                no_hay.setVisibility(View.GONE);
                lcontactos.setVisibility(View.VISIBLE);
            }
        }

    }


}
