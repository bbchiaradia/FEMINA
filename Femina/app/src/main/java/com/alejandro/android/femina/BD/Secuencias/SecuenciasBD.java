package com.alejandro.android.femina.BD.Secuencias;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.Adaptadores.AdaptadorContactos;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Entidades.Secuencias;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Fragments.contactos.Principal.ContactosFragment;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;
import com.alejandro.android.femina.Session.SessionContactos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SecuenciasBD extends AsyncTask <String, Void, String> {


    private Secuencias sec;
    private EditText secuencia;
    TextView id_secuencia;
    private Switch activado;
    private Usuarios user;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private Boolean activo,dejo_loguear,secuencia_ok,usuario_existe,sec_activa;
    private String id_sec,secuencia_;


    public SecuenciasBD(Secuencias sec, Context ct, String que) {
        ses = new Session();
        this.sec = new Secuencias();
        this.sec = sec;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        secuencia_ok = false;
        activo = false;
        usuario_existe = false;
        dejo_loguear = false;
        user = new Usuarios();
    }

    public SecuenciasBD(Secuencias sec, Context ct, EditText secuencia, Switch activado, TextView id_sec, String que) {
        ses = new Session();
        this.sec = new Secuencias();
        this.sec = sec;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        user = new Usuarios();
        this.secuencia = secuencia;
        this.activado = activado;
        this.id_secuencia = id_sec;
        sec_activa = false;
        this.id_sec = "";
        secuencia_ = "";
    }


    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;

        if (que_hacer.equals("TraerSecuencia")) {

            response = "";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM Secuencias where idUsuario='" + sec.getId_usuario().getId_usuario() + "'" );

                if(rs.next()) {
                    sec.setSecuencia(rs.getString("Secuencia"));
                    sec.setId_secuencia(rs.getInt("idSecuencia"));
                    sec.setActivado(rs.getBoolean("Activo"));
                }

                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                response = "Conexion no exitosa";
            }


        }

        if (que_hacer.equals("LeerSecuencia")) {

            Log.d("SECUENCIA",sec.getSecuencia());

            response = "";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM Usuarios where usuario='" + sec.getId_usuario().getUsuario() + "'" );

                if(rs.next()) {
                    user.setId_usuario(rs.getInt("idUsuario"));
                    user.setUsuario(rs.getString("Usuario"));
                    sec.setId_usuario(user);
                    usuario_existe = true;
                }

                if(usuario_existe) {


                    rs = st.executeQuery("SELECT * FROM Secuencias where idUsuario='" + sec.getId_usuario().getId_usuario() + "'" );

                    if(rs.next()) {
                        if(rs.getString("Secuencia").equals(sec.getSecuencia()))
                            secuencia_ok = true;

                        if(secuencia_ok)
                            if(rs.getBoolean("Activo"))
                                activo = true;
                    }


                    if(secuencia_ok) {

                        if(activo) {


                            rs = st.executeQuery("SELECT * FROM Usuarios where usuario='" + sec.getId_usuario().getUsuario() + "'");

                            if (rs.next()) {
                                dejo_loguear = true;
                                mensaje_devuelto = "Bienvenido/a: " + user.getUsuario();
                                ses.setId_usuario(rs.getInt("idUsuario"));
                                ses.setNombre(rs.getString("nombre"));
                                ses.setApellido(rs.getString("apellido"));
                                ses.setUsuario(rs.getString("Usuario"));
                                ses.setContrasena(rs.getString("Contraseña"));
                                ses.setSexo(rs.getString("sexo"));
                                ses.setTelefono(rs.getString("telefono"));
                                ses.setEs_admin(rs.getBoolean("esAdmin"));
                                ses.setCt(context);

                                ses.nueva_session();

                            }

                        }else
                            mensaje_devuelto = "La secuencia se encuentra deshabilitada";

                    }
                    else
                        mensaje_devuelto = "Secuencia incorrecta";


                }
                else
                    mensaje_devuelto = "Usuario inexistente";

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
                ps = con.prepareStatement("INSERT INTO Secuencias (idUsuario, Secuencia, Activo) " +
                        "VALUES (?,NULL,false)");

                Statement st = con.createStatement();
                ResultSet rs;

                ps.setInt(1, sec.getId_usuario().getId_usuario());

                int filas = ps.executeUpdate();

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar secuencia!!";
            }
        }

        if (que_hacer.equals("Modificar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("UPDATE Secuencias set Secuencia=? where idSecuencia=?");

                Statement st = con.createStatement();
                ResultSet rs;

                ps.setString(1, sec.getSecuencia());
                ps.setInt(2, sec.getId_secuencia());

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Secuencia actualizada!";
                    } else
                        mensaje_devuelto = "Error al actualizar secuencia!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al actualizar secuencia!!";
            }
        }

        if (que_hacer.equals("Activar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("UPDATE Secuencias set Activo=? where idSecuencia=?");

                Statement st = con.createStatement();
                ResultSet rs;

                ps.setBoolean(1, false);
                ps.setInt(2, sec.getId_secuencia());

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Secuencia activada!";
                    } else
                        mensaje_devuelto = "Error al activar secuencia!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al activar secuencia!!";
            }
        }

        if (que_hacer.equals("Desactivar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("UPDATE Secuencias set Activo=? where idSecuencia=?");

                Statement st = con.createStatement();
                ResultSet rs;

                ps.setBoolean(1, true);
                ps.setInt(2, sec.getId_secuencia());

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Secuencia desactivada!";
                    } else
                        mensaje_devuelto = "Error al desactivar secuencia!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al desactivar secuencia!!";
            }
        }

        return response;


    }


    @Override
    protected void onPreExecute() {
        if(!que_hacer.equals("Insertar")) {
            dialog.show();
            dialog.setContentView(R.layout.progress_dialog);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    protected void onPostExecute(String response) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(que_hacer.equals("LeerSecuencia")){
            if(dejo_loguear){
                Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id_articulo",-1);
                intent.putExtra("LOGUEO","EMERGENCIA");
                context.startActivity(intent);
                ((Activity) context).finish();
            }
            else
                Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_LONG).show();
        }

        if(que_hacer.equals("TraerSecuencia")){

            if(sec.getActivado())
                activado.setChecked(true);
            else
                activado.setChecked(false);

            for(int i= 0; i < sec.getSecuencia().length(); i ++){

                if(sec.getSecuencia().charAt(i) == '0')
                    secuencia_ += "ARRIBA;";

                if(sec.getSecuencia().charAt(i) == '1')
                    secuencia_ += "DERECHA;";

                if(sec.getSecuencia().charAt(i) == '2')
                    secuencia_ += "ABAJO;";

                if(sec.getSecuencia().charAt(i) == '3')
                    secuencia_ += "IZQUIERDA;";
            }

            secuencia.setText(secuencia_);

            id_secuencia.setText("" + sec.getId_secuencia());

        }

        if(que_hacer.equals("Modificar") || que_hacer.equals("Activar") || que_hacer.equals("Desactivar")){
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();
        }


    }


}


