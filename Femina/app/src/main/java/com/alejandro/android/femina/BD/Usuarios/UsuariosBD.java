package com.alejandro.android.femina.BD.Usuarios;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.Session.Session;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

public class UsuariosBD extends AsyncTask<String, Void, String> {


    private Usuarios user;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private boolean dejo_loguear,insertamos,me_voy_pantalla;
    private Session session_usuario;
    private int filas;



    public UsuariosBD(Usuarios us, Context ct, String que) {

        user = new Usuarios();
        user = us;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.dejo_loguear = false;
        this.session_usuario = new Session();
    }





    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;
        PreparedStatement ps_aux = null;

       if(que_hacer.equals("Insertar")) {

            insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                ps = con.prepareStatement("INSERT INTO Usuarios (Usuario, Contraseña, nombre, " +
                        "apellido, sexo, telefono, esAdmin) VALUES (?,?,?,?,?,?,false)");

                ps.setString(1, user.getUsuario());
                ps.setString(2, user.getContrasena());
                ps.setString(3, user.getNombre());
                ps.setString(4, user.getApellido());
                ps.setString(5, String.valueOf(user.getSexo()));
                ps.setString(6, user.getTelefono());

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT idUsuario FROM Usuarios where Usuario ='" + user.getUsuario()+"'");

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "Usuario ya registrado!";

                }


                if(insertamos)
                     filas = ps.executeUpdate();


                    if (filas > 0) {

                        mensaje_devuelto = "Bienvenido/a: " + user.getUsuario();

                        rs = st.executeQuery("SELECT idUsuario FROM Usuarios where Usuario ='" + user.getUsuario()+"'");

                        if (rs.next())
                            session_usuario.setId_usuario(rs.getInt("idUsuario"));

                        session_usuario.setNombre(user.getNombre());
                        session_usuario.setApellido(user.getApellido());
                        session_usuario.setUsuario(user.getUsuario());
                        session_usuario.setContrasena(user.getContrasena());
                        if(user.getSexo() == 'M')
                            session_usuario.setSexo("Masculino");
                        if(user.getSexo() == 'F')
                            session_usuario.setSexo("Femenino");
                        if(user.getSexo() == 'O')
                            session_usuario.setSexo("Otro");
                        session_usuario.setTelefono(user.getTelefono());
                        session_usuario.setEs_admin(false);
                        session_usuario.setCt(context);

                        session_usuario.nueva_session();

                    }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                mensaje_devuelto = "Error al registrar usuario!!";
            }
        }

        if(que_hacer.equals("Loguin")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Statement st = con.createStatement();
                ResultSet rs;

                    rs = st.executeQuery("SELECT * FROM Usuarios where usuario='" + user.getUsuario() + "'" +
                            " and contraseña='" + user.getContrasena() + "'");

                    if (rs.next()) {
                        dejo_loguear = true;
                        mensaje_devuelto = "Bienvenido/a: " + user.getUsuario();
                        session_usuario.setId_usuario(rs.getInt("idUsuario"));
                        session_usuario.setNombre(rs.getString("nombre"));
                        session_usuario.setApellido(rs.getString("apellido"));
                        session_usuario.setUsuario(rs.getString("Usuario"));
                        session_usuario.setContrasena(rs.getString("Contraseña"));
                        session_usuario.setSexo(rs.getString("sexo"));
                        session_usuario.setTelefono(rs.getString("telefono"));
                        session_usuario.setEs_admin(rs.getBoolean("esAdmin"));
                        session_usuario.setCt(context);

                        session_usuario.nueva_session();

                    } else{

                        rs = st.executeQuery("SELECT * FROM Usuarios where usuario='" + user.getUsuario() + "'");

                        if(rs.next())
                            mensaje_devuelto = "Contraseña incorrecta";
                        else
                            mensaje_devuelto = "Usuario incorrecto";
                    }

                response = "Conexion OK";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                mensaje_devuelto = "Error al conectarse con base de datos!!";
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


          if(que_hacer.equals("Insertar")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            if(insertamos) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }

              Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();

        }

        if(que_hacer.equals("Loguin")){
            if(dejo_loguear){
                Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
            else
                Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_LONG).show();


        }



    }

}


