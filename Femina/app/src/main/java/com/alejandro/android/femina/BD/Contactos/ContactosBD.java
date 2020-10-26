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
import com.alejandro.android.femina.Fragments.contactos.Principal.ContactosFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;
import com.alejandro.android.femina.Session.SessionContactos;


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
    private int cant_contactos;
    private String[] contactos;

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

    public ContactosBD(Context ct, String que) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        cont = new ContactosEmergencia();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        cant_contactos = 0;
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

        if (que_hacer.equals("TraerContactos")) {

            Log.d("TraerContactos","Actualizando");

            response = "";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT count(*) FROM ContactosEmergencia where idUsuario=" + ses.getId_usuario());

                if(rs.next()){
                    cant_contactos = rs.getInt(1);
                }

                contactos = new String[cant_contactos];

                rs = st.executeQuery("SELECT * FROM ContactosEmergencia where idUsuario=" + ses.getId_usuario());

                int i = 0;

                while (rs.next()) {
                    contactos[i] = rs.getString("nroTelefono");
                    i++;
                }

                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                response = "Conexion no exitosa";
            }


        }

        if (que_hacer.equals("Insertar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("INSERT INTO ContactosEmergencia (idUsuario, nroTelefono, nombreContacto) " +
                        "VALUES (?,?,?)");

                Statement st = con.createStatement();
                ResultSet rs;

                ps.setInt(1, cont.getId_usuario().getId_usuario());
                ps.setString(2, cont.getTelefono());
                ps.setString(3, cont.getNombre_contacto());


                rs = st.executeQuery("SELECT nombreContacto FROM ContactosEmergencia where nroTelefono ='" + cont.getTelefono() +
                        "' and idUsuario=" + cont.getId_usuario().getId_usuario());

                if (rs.next()) {
                    insertamos = false;
                    mensaje_devuelto = "El numero ya esta registrado en tu lista bajo el nombre de "+rs.getString(1)+"!";
                }

                rs = st.executeQuery("SELECT Count(idUsuario) FROM ContactosEmergencia where idUsuario =" + cont.getId_usuario().getId_usuario());

                if (rs.next()) {

                    if (rs.getInt(1) >= 4 ) {
                        insertamos = false;
                        mensaje_devuelto = "Alcanzaste el maximo de contactos en tu cuenta(4)!";
                    }

                }


                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Contacto registrado!";
                    } else
                        mensaje_devuelto = "Error al registrar contacto!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar contacto!!";
            }
        }

        if (que_hacer.equals("Modificar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("UPDATE ContactosEmergencia set nombreContacto=?, nroTelefono=? where idContactoEmergencia=?");

                Statement st = con.createStatement();
                ResultSet rs;

                ps.setString(1, cont.getNombre_contacto());
                ps.setString(2, cont.getTelefono());
                ps.setInt(3, cont.getId_contacto_emergencia());


                rs = st.executeQuery("SELECT nombreContacto FROM ContactosEmergencia where nroTelefono ='" + cont.getTelefono() +
                        "' and idUsuario=" + cont.getId_usuario().getId_usuario() + " and idContactoEmergencia <>" + cont.getId_contacto_emergencia());

                if (rs.next()) {
                    insertamos = false;
                    mensaje_devuelto = "El numero ya esta registrado en tu lista bajo el nombre de "+rs.getString(1)+"!";
                }

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Contacto actualizado!";
                    } else
                        mensaje_devuelto = "Error al actualizar contacto!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al actualizar contacto!!";
            }
        }

        if (que_hacer.equals("Eliminar")) {


            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("Delete from ContactosEmergencia where idContactoEmergencia=?");


                ps.setInt(1, cont.getId_contacto_emergencia());


                int filas = ps.executeUpdate();

                if (filas > 0) {
                    mensaje_devuelto = "Contacto eliminado!";
                } else
                    mensaje_devuelto = "Error al eliminar contacto!";


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al eliminar contacto!!";
            }
        }

        return response;


    }


    @Override
    protected void onPreExecute() {
        if(!que_hacer.equals("TraerContactos")) {
            dialog.setMessage("Procesando...");
            dialog.show();
        }
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

        if(que_hacer.equals("TraerContactos")) {

            SessionContactos sessionContactos = new SessionContactos();
            sessionContactos.setContext(context);
            sessionContactos.setCant_contactos(cant_contactos);
            sessionContactos.setContactos(contactos);
            sessionContactos.nueva_session();

        }

        if(que_hacer.equals("Insertar")) {
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new ContactosFragment()).commit();
        }

        if(que_hacer.equals("Modificar") || que_hacer.equals("Eliminar")){
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new ContactosFragment()).commit();

        }


    }


}
