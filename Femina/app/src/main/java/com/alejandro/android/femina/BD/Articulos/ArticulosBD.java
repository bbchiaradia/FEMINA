package com.alejandro.android.femina.BD.Articulos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.Adaptadores.AdaptadorArticulos;
import com.alejandro.android.femina.Adaptadores.AdaptadorContactos;
import com.alejandro.android.femina.Adaptadores.AdapterVideos;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Articulos;
import com.alejandro.android.femina.Entidades.ArticulosExtra;
import com.alejandro.android.femina.Entidades.Categorias;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Entidades.Usuarios;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.Fragments.contactos.Principal.ContactosFragment;
import com.alejandro.android.femina.Fragments.que_hacer.Admin.Alta_Modificacion.QueHacerAMFragment;
import com.alejandro.android.femina.GestionImagen.GestionarImagen;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ArticulosBD extends AsyncTask<String, Void, String> {


    private Articulos art,art_;
    private Categorias cat;
    private ArticulosExtra art_ext;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private ListView larticulos;
    private TextView no_hay;
    private Boolean modifico,imagen_nula,no_hay_art;
    private int max_articulo;
    private Bitmap image = null;
    private EditText titulo,descripcion;
    private ImageButton img;
    private Spinner spn_cat;
    private SearchView buscar;

    private static ArrayList<ArticulosExtra> listaArticulos = new ArrayList<ArticulosExtra>();

    private static ArrayList<String> datosSpinner = new ArrayList<String>();

    public ArticulosBD(Context ct, Articulos art, String que){

        this.ses = new Session();
        dialog = new ProgressDialog(ct);
        this.context = ct;
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;
        this.art = art;
    }

    public ArticulosBD(Context ct, String que){
        this.ses = new Session();
        dialog = new ProgressDialog(ct);
        this.context = ct;
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;
        this.max_articulo = 0;
    }

    public ArticulosBD(Context context, String que, Spinner spn){
        datosSpinner.clear();
        this.context = context;
        this.que_hacer = que;
        this.spn_cat = spn;
        dialog = new ProgressDialog(context);
    }


    public ArticulosBD(Context ct, Articulos art, ImageButton img, EditText tit, EditText desc,
                       Spinner sp, String que){
        datosSpinner.clear();
        this.ses = new Session();
        dialog = new ProgressDialog(ct);
        this.context = ct;
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;
        this.art = art;
        this.img = img;
        this.titulo = tit;
        this.descripcion = desc;
        this.spn_cat = sp;
        this.art_ = new Articulos();
        this.cat = new Categorias();
    }

    public ArticulosBD(Context context, ListView list, TextView no,Spinner cat,SearchView sv, String que){
        datosSpinner.clear();
        listaArticulos.clear();
        this.context = context;
        this.larticulos = list;
        this.que_hacer = que;
        this.buscar = sv;
        dialog = new ProgressDialog(context);
        no_hay = no;
        no_hay_art = true;
        this.spn_cat = cat;
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
                Statement st_aux = con.createStatement();
                ResultSet rs;
                ResultSet rs_aux;

                rs = st.executeQuery("SELECT * from Categorias");

                while(rs.next()){
                    datosSpinner.add(rs.getString("Descripcion"));
                }

                rs = st.executeQuery("SELECT * FROM Articulos");

                while (rs.next()) {
                    no_hay_art = false;
                    art = new Articulos();
                    art_ext = new ArticulosExtra();

                    art.setId_articulo(rs.getInt("idArticulo"));
                    art.setTitulo(rs.getString("Titulo"));
                    art.setDescripcion(rs.getString("Descripcion"));
                    art.setFecha_carga(rs.getDate("fechaCarga"));
                    art.setVistas(rs.getInt("vistas"));

                    String add = "http://femina.webcindario.com/getImage.php?id="+rs.getInt("idArticulo");
                    URL url = null;
                    try {
                        url = new URL(add);
                        image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    rs_aux = st_aux.executeQuery("SELECT imagen from Articulos where idArticulo =" + rs.getInt("idArticulo"));

                    if (rs_aux.next()) {

                        if(rs_aux.getBlob(1)==null)
                            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.nodisponible);
                    }

                    art_ext.setArticulo(art);
                    art_ext.setImagen_articulo(image);

                    listaArticulos.add(art_ext);
                }

                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                response = "Conexion no exitosa";
            }


        }

        if (que_hacer.equals("CargarArticulo")) {

            imagen_nula = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * from Categorias");

                while(rs.next()){
                    datosSpinner.add(rs.getString("Descripcion"));
                }


                rs = st.executeQuery("SELECT a.Descripcion,a.Titulo,c.Descripcion,c.idCategoria from Articulos a" +
                        " inner join Categorias c on a.idCategoria = c.idCategoria where idArticulo =" + art.getId_articulo());

                if (rs.next()) {

                    art_.setDescripcion(rs.getString(1));
                    art_.setTitulo(rs.getString(2));
                    cat.setDescripcion(rs.getString(3));
                    cat.setId_categoria(rs.getInt(4));
                    art_.setId_categoria(cat);
                    //art_.setImagen(rs.getBlob(4));

                    //String id = urls[0];
                    String add = "http://femina.webcindario.com/getImage.php?id="+art.getId_articulo();
                    URL url = null;
                    //Bitmap image = null;
                    try {
                        url = new URL(add);
                        image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    rs = st.executeQuery("SELECT imagen from Articulos where idArticulo =" + art.getId_articulo());

                    if (rs.next()) {

                        if(rs.getBlob(1)==null)
                            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.nodisponible);
                    }


                } else
                    mensaje_devuelto = "Error al cargar articulo!";

                response = "ok";

                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al cargar articulo!";
            }
        }

        if(que_hacer.equals("EliminarFoto")) {

            modifico = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                ps = con.prepareStatement("UPDATE Articulos SET imagen=NULL where idArticulo=?");

                ps.setInt(1, art.getId_articulo());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    mensaje_devuelto = "Imagen eliminada!";
                    modifico = true;
                }
                else
                    mensaje_devuelto = "Error al eliminar imagen!";


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al eliminar imagen!";
            }
        }

        if (que_hacer.equals("InsertNull")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Statement st = con.createStatement();
                ResultSet rs;

                int id = 0;

                rs = st.executeQuery("SELECT max(idArticulo) FROM Articulos");

                if (rs.next()) {

                    id = rs.getInt(1) + 1;

                }

                ps = con.prepareStatement("INSERT INTO Articulos (idArticulo, Titulo, idCategoria, Descripcion,imagen,fechaCarga,vistas) " +
                        "VALUES (?,NULL,1,NULL,NULL,CURRENT_DATE(),0)");

                ps.setInt(1,id);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    max_articulo = id;
                } else
                    max_articulo = -1;



                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar articulo!!";
            }
        }

        if(que_hacer.equals("CargarSpinner")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                ResultSet rs;
                Statement st = con.createStatement();

                rs = st.executeQuery("SELECT * from Categorias");


                while(rs.next()){
                    datosSpinner.add(rs.getString("Descripcion"));
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar categorias!";
            }
        }

        return response;


    }


    @Override
    protected void onPreExecute() {

        if(!que_hacer.equals("InsertNull") || !que_hacer.equals("CargarSpinner")) {
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
            final AdaptadorArticulos adapter = new AdaptadorArticulos(context, listaArticulos);
            larticulos.setAdapter(adapter);

            buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return true;
                }
            });

            if(no_hay_art) {
                no_hay.setVisibility(View.VISIBLE);
                larticulos.setVisibility(View.GONE);
            }
            else {
                no_hay.setVisibility(View.GONE);
                larticulos.setVisibility(View.VISIBLE);
            }

            spn_cat.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, datosSpinner));
            spn_cat.setSelection(6);

        }

        if(que_hacer.equals("EliminarFoto")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            if(modifico){

                Bundle datosAEnviar = new Bundle();

                datosAEnviar.putInt("id_articulo", art.getId_articulo());

                Fragment fragmento = new QueHacerAMFragment();
                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
            }
        }

        if(que_hacer.equals("InsertNull")) {

                Bundle datosAEnviar = new Bundle();

                datosAEnviar.putInt("id_articulo", max_articulo);

                Fragment fragmento = new QueHacerAMFragment();
                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
        }

        if(que_hacer.equals("CargarSpinner")) {

            spn_cat.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, datosSpinner));
            //categoria.setSelection(6);

        }

        if (que_hacer.equals("CargarArticulo")) {

            spn_cat.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, datosSpinner));

            titulo.setText(art_.getTitulo());
            descripcion.setText(art_.getDescripcion());
            img.setImageBitmap(image);

            spn_cat.setSelection(art_.getId_categoria().getId_categoria() - 1);

        }


    }


}
