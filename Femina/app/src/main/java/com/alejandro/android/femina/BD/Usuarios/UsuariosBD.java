package com.alejandro.android.femina.BD.Usuarios;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.Adaptadores.AdaptadorContactos;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.Pantallas_exteriores.Ingresar;
import com.alejandro.android.femina.R;
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
import java.util.ArrayList;
import java.util.Random;

public class UsuariosBD extends AsyncTask<String, Void, String> {


    private Usuarios user;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto,telefono;
    private boolean dejo_loguear,insertamos,me_voy_pantalla,modificamos,guardo_contra,envio_mensaje;
    private Session session_usuario;
    private int filas;
    private Session ses;
    private TextView apeUsu;
    private TextView nomUsu;
    private TextView contraseniaUsu;
    private TextView telUsu;
    private TextView Usu;
    private Spinner sexUsu;
    private int nueva_contra;




    public UsuariosBD(Usuarios us, Context ct, String que) {
        user = new Usuarios();
        user = us;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.dejo_loguear = false;
        this.session_usuario = new Session();
    }


    public UsuariosBD(Context ct,  TextView nm, TextView ap, TextView cn,TextView tel, TextView usu, Spinner sx, String que) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        user = new Usuarios();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        apeUsu = ap;
        nomUsu= nm;
        contraseniaUsu = cn;
        telUsu= tel;
        Usu = usu;
        sexUsu = sx;
    }


    public UsuariosBD(Context ct, String que, Usuarios usu) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.user = new Usuarios();
        this.user = usu;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.nueva_contra = 0;
        this.telefono = "";
        this.guardo_contra = false;

    }


    public UsuariosBD(Context ct, Usuarios us, String que) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
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

        if(que_hacer.equals("EnviarMensaje")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Random r = new Random();
                nueva_contra = 1000 + r.nextInt(9999 - 1000 + 1);

                ps = con.prepareStatement("Update Usuarios set Contraseña=? where Usuario=?");

                ps.setString(1, "" + nueva_contra);
                ps.setString(2, user.getUsuario());

                Statement st = con.createStatement();
                ResultSet rs;


                rs = st.executeQuery("SELECT * FROM Usuarios where Usuario ='" + user.getUsuario()+"'");

                if (!rs.next()) {
                    insertamos = false;
                    mensaje_devuelto = "El usuario no existe";
                }

                rs = st.executeQuery("SELECT Telefono FROM Usuarios where Usuario ='" + user.getUsuario()+"'");

                if (rs.next()) {
                    telefono = rs.getString(1);
                    user.setTelefono(telefono);
                }

                if(insertamos)
                    filas = ps.executeUpdate();


                if (filas > 0) {
                    user.setContrasena("" + nueva_contra);
                guardo_contra = true;
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                mensaje_devuelto = "Error al guardar nueva contraseña!!";
            }
        }

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

        if (que_hacer.equals("Listar")) {

            response = "";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM Usuarios where Usuario='" + ses.getUsuario()+"'");


                while (rs.next()) {

                    user = new Usuarios();
                    user.setApellido(rs.getString("apellido"));
                    user.setNombre(rs.getString("nombre"));
                    user.setContrasena(rs.getString("Contraseña"));
                    user.setTelefono(rs.getString("telefono"));
                    user.setUsuario(rs.getString("usuario"));


                    if((rs.getString("sexo")).charAt(0) == 'M')
                        user.setSexo('M');
                    if((rs.getString("sexo")).charAt(0)  == 'F')
                        user.setSexo('F');
                    if((rs.getString("sexo")).charAt(0)  == 'O')
                        user.setSexo('O');


                }

                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                response = "Conexion no exitosa";
            }


        }

        if(que_hacer.equals("Modificar")) {

            modificamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                ps = con.prepareStatement("UPDATE Usuarios SET Usuario = '"+user.getUsuario()+"', Contraseña = '"+user.getContrasena()+"', nombre = '"+user.getNombre()+"', apellido = '"+user.getApellido()+"', sexo = '"+String.valueOf(user.getSexo())+"', telefono = '"+user.getTelefono()+"' WHERE Usuarios.idUsuario = "+ses.getId_usuario()+"");

                Statement st = con.createStatement();
                ResultSet rs;


                if(modificamos)
                    filas = ps.executeUpdate();


                if (filas > 0) {

                    mensaje_devuelto = "Se actualizó el usuario " + user.getUsuario();
                    

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                mensaje_devuelto = "Error al modificar usuario!!";
            }
        }


        return response;

    }


    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onPostExecute(String response) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(que_hacer.equals("EnviarMensaje")) {
                if(guardo_contra)
                ((Ingresar) context).enviar_datos(user.getTelefono(), user.getContrasena(), user.getUsuario());
                else
                Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
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

        if(que_hacer.equals("Listar")) {
            apeUsu.setText(user.getApellido());
            nomUsu.setText(user.getNombre());
            contraseniaUsu.setText(user.getContrasena());
            telUsu.setText(user.getTelefono());
            Usu.setText(user.getUsuario());


            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.context, R.array.sexo_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            sexUsu.setOnItemSelectedListener(sexUsu.getOnItemSelectedListener());
            sexUsu.setAdapter(adapter);

            if (user.getSexo()=='M')
                sexUsu.setSelection(0);
            if (user.getSexo()=='F')
                sexUsu.setSelection(1);
            if (user.getSexo()=='O')
                sexUsu.setSelection(2);

            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();
        }


        if(que_hacer.equals("Modificar")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            if(modificamos) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }

            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();

        }



    }

}


