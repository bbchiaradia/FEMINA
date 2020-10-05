package com.alejandro.android.femina.BD.Usuarios;

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
    private boolean dejo_loguear;
    private Session session_usuario;



    public UsuariosBD(Usuarios us, Context ct, String que) {

        user = new Usuarios();
        this.user = us;
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

      /*  if(que_hacer.equals("Insertar")) {

            boolean insertamos = true;
            modifico = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");
                ps = con.prepareStatement("INSERT INTO usuarios (usuario, contraseña, nombre, " +
                        "apellido, dni, sexo, email, fecha_nacimiento, fecha_alta, tiporol, " +
                        "idestado, image) VALUES (?,?,?,?,?,?,?,?,CURRENT_DATE(),?,?,NULL)");

                Statement st = con.createStatement();
                ResultSet rs;
                ResultSet rs_;

                Log.d("ROL",""+ tipo_rol);

                rs = st.executeQuery("SELECT * FROM tiporol where rol='" + tipo_rol + "'");


                if(rs.next()){
                    tipo = new TipoRol();
                    tipo.setId_rol(rs.getInt("idtiporol"));
                    tipo.setRol(rs.getString("rol"));
                    user.setTipo(tipo);

                }

                rs = st.executeQuery("SELECT * FROM estados where estado='" + this.estado + "'");


                if(rs.next()){
                    esta = new Estados();
                    esta.setId_estado(rs.getInt("idestado"));
                    esta.setEstado(rs.getString("estado"));
                    user.setEstado(esta);

                }


                ps.setString(1, user.getUsuario());
                ps.setString(2, user.getContrasena());
                ps.setString(3, user.getNombre());
                ps.setString(4, user.getApellido());
                ps.setString(5, user.getDni());
                ps.setString(6, String.valueOf(user.getSexo()));
                ps.setString(7, user.getMail());
                ps.setDate(8, user.getFecha_nacimiento());
                //ps.setDate(9, user.getFecha_alta());
                ps.setInt(9, user.getTipo().getId_rol());
                ps.setInt(10, user.getEstado().getId_estado());


                rs = st.executeQuery("SELECT idusuario FROM usuarios where email ='" + user.getMail()+"'");

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "Mail ya registrado!";

                }

                rs = st.executeQuery("SELECT idusuario FROM usuarios where usuario ='" + user.getUsuario()+"'");

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "Usuario ya registrado!";

                }

                rs = st.executeQuery("SELECT idusuario FROM usuarios where dni ='" + user.getDni()+"'");

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "Dni ya registrado!";

                }


                if(insertamos){

                    int filas = ps.executeUpdate();


                    rs = st.executeQuery("SELECT * from usuarios where email ='" + user.getMail() + "' and" +
                            " usuario='"+user.getUsuario()+"'");

                    if (rs.next()) {

                        user.setId_usuario(rs.getInt("idusuario"));

                    }


                    switch(tipo_rol){

                        case "Paciente":
                            Pacientes pac = new Pacientes();
                            pac.setId_usuario(user);
                            ps_aux = con.prepareStatement("INSERT INTO pacientes (idusuario, factors, peso, " +
                                    "altura, medicocabecera) VALUES (?,NULL,NULL,NULL,NULL)");
                            ps_aux.setInt(1,pac.getId_usuario().getId_usuario());
                            break;

                        case "Supervisor":
                            Profesionales prof = new Profesionales();
                            prof.setId_usuario(user);
                            ps_aux = con.prepareStatement("INSERT INTO profesionales (idusuario, nromatricula)" +
                                    " VALUES (?,?)");
                            ps_aux.setInt(1,prof.getId_usuario().getId_usuario());
                            ps_aux.setString(2, matricula);
                            break;

                        case "Familiar":
                            Familiares fam = new Familiares();
                            fam.setId_usuario(user);
                            ps_aux = con.prepareStatement("INSERT INTO familiares (idusuario, parentesco)" +
                                    " VALUES (?,NULL)");
                            ps_aux.setInt(1,fam.getId_usuario().getId_usuario());
                            break;

                    }

                    int filas_ = ps_aux.executeUpdate();


                    if (filas > 0 && filas_>0) {
                        mensaje_devuelto = "Usuario registrado!";
                        modifico = true;
                        if (tipo_rol.equals("Supervisor"))
                            mensaje_devuelto += ", debes aguardar confirmacion";
                    }
                    else
                        mensaje_devuelto = "Error al registrar usuario!";

                }
                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar usuario!!";
            }
        }*/

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


        /*  if(que_hacer.equals("Insertar")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            if(modifico) {
                Intent intent = new Intent(context, iniciar_sesion.class);
                context.startActivity(intent);
            }
        }*/

        if(que_hacer.equals("Loguin")){
            if(dejo_loguear){
                Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
            else
                Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();

        }



    }

}


