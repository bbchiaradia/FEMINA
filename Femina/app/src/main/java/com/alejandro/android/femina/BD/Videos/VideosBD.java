package com.alejandro.android.femina.BD.Videos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Adaptadores.AdapterVideos;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Categorias;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.Fragments.testimonios.Admin.AMVideos.TestimoniosAMVideosFragment;
import com.alejandro.android.femina.Fragments.testimonios.Videos.TestimoniosVideosFragment;
import com.alejandro.android.femina.R;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

//
// Created by Juan Manuel on 7/10/2020.
//
public class VideosBD extends AsyncTask<String, Void, String> {

    private RecyclerView recyclerView;
    private  Context context;
    private AdapterVideos adaptervideos,adapter_video;
    private Videos vi;
    private Categorias cat;
    private ArrayList<Videos> videosArrayList = new ArrayList<>();
    private String que_hacer;
    private ProgressDialog dialog;
    private String mensaje_devuelto,cate;
    private Boolean no_hay_cont;
    private SearchView buscar;
    private TextView no_hay;
    private Spinner categoria;
    Activity activity;

    private static ArrayList<String> datosSpinner = new ArrayList<String>();
    private Set<String> lista_categorias_set;
    private String[] lista_categorias;


public VideosBD(Context context, String que, Spinner spn){
    datosSpinner.clear();
    this.context = context;
    this.que_hacer = que;
    this.categoria = spn;
    dialog = new ProgressDialog(context);

}

    public VideosBD(Context context, AdapterVideos adaptervideos, ArrayList<Videos> videosArrayList, TextView tx, String que, SearchView sv,String cat){
    videosArrayList.clear();
    this.context = context;
    this.adaptervideos = adaptervideos;
    this.videosArrayList = videosArrayList;
    this.que_hacer = que;
    this.buscar = sv;
    dialog = new ProgressDialog(context);
    no_hay = tx;
    no_hay_cont = true;
    activity = (Activity) context;
    this.cate = cat;
    Log.d("Categoria","" + cat);
}

    public VideosBD(Videos vi, Context context, String que){
        this.context = context;
        this.que_hacer = que;
        dialog = new ProgressDialog(context);
        this.vi = vi;
    }


    @Override
    protected String doInBackground(String... strings) {
      String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;
        if (que_hacer.equals("Listar") || que_hacer.equals("SelCategoria")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                if(que_hacer.equals("Listar"))
                rs = st.executeQuery("SELECT v.idVideo, v.Titulo, c.Descripcion, v.urlVideo FROM Videos v" +
                        " inner join Categorias c on v.idCategoria = c.idCategoria");

                else if(cate.equals("Todas"))
                    rs = st.executeQuery("SELECT v.idVideo, v.Titulo, c.Descripcion, v.urlVideo FROM Videos v" +
                            " inner join Categorias c on v.idCategoria = c.idCategoria");
                else
                    rs = st.executeQuery("SELECT v.idVideo, v.Titulo, c.Descripcion, v.urlVideo FROM Videos v" +
                            " inner join Categorias c on v.idCategoria = c.idCategoria where c.Descripcion='" + cate + "'");

                while (rs.next()) {
                    no_hay_cont = false;
                    vi = new Videos();
                    cat = new Categorias();
                    vi.setId_video(rs.getInt("idVIdeo"));
                    vi.setTitulo(rs.getString("Titulo"));
                    cat.setDescripcion(rs.getString("Descripcion"));
                    vi.setIdCategoria(cat);
                    vi.setUrl_video(rs.getString("urlVideo"));
                    videosArrayList.add(vi);
                }

                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                response = "Conexion no exitosa";
            }


        }

        if(que_hacer.equals("CargarSpinner")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                ResultSet rs;
                Statement st = con.createStatement();

                rs = st.executeQuery("Select count(*) from Categorias");

                while(rs.next()){
                    lista_categorias = new String[rs.getInt(1)];
                }

                rs = st.executeQuery("SELECT * from Categorias");

                int i = 0;
                datosSpinner.add("Todas");
                while(rs.next()){

                    datosSpinner.add(rs.getString("Descripcion"));
                    lista_categorias[i] = rs.getString("Descripcion");
                    i++;

                }
                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar categorias!";
            }
        }


        if (que_hacer.equals("Modificar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("UPDATE Videos SET Titulo=?, idCategoria=?, urlVideo=? WHERE idVideo=?");

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("Select idCategoria from Categorias where Descripcion='"
                        + vi.getIdCategoria().getDescripcion() + "'");

                if (rs.next())
                    ps.setInt(2, rs.getInt(1));
                else
                    insertamos = false;

                ps.setString(1,vi.getTitulo());
                ps.setString(3,vi.getUrl_video());
                ps.setInt(4,vi.getId_video());

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Video actualizado!";
                    } else
                        mensaje_devuelto = "Error al actualizar el video!";
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al actualizar el video!!";
            }
        }


        if (que_hacer.equals("Insertar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("INSERT INTO Videos (Titulo, idCategoria, urlVideo) VALUES( ? , ? , ? )");


                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("Select idCategoria from Categorias where Descripcion='"
                        + vi.getIdCategoria().getDescripcion() + "'");

                if (rs.next())
                    ps.setInt(2, rs.getInt(1));
                else
                    insertamos = false;



                Statement st_ = con.createStatement();
                ResultSet rs_;

                ps.setString(1, vi.getTitulo());
                ps.setString(3, vi.getUrl_video());

                rs_ = st_.executeQuery("SELECT 1 FROM Videos where urlVideo ='" + vi.getUrl_video() + "'");

                if (rs_.next()) {
                    insertamos = false;
                    mensaje_devuelto = "La URL ya esta registrada";
                }

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Video registrado!";
                    } else
                        mensaje_devuelto = "Error al registrar el Video!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                mensaje_devuelto = "Error al registrar el Video!";
            }
        }

        if (que_hacer.equals("Eliminar")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("Delete from Videos where idVideo=?");

                ps.setInt(1, vi.getId_video());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    mensaje_devuelto = "Video eliminado!";
                } else
                    mensaje_devuelto = "Error al eliminar el Video!";

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al eliminar el Video!!";
            }
        }


        return response;

    }

    @Override
    protected void onPreExecute() {

    if(!que_hacer.equals("CargarSpinner")) {
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


        if(que_hacer.equals("Listar") || que_hacer.equals("SelCategoria")) {

        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager( new LinearLayoutManager(context));
            adaptervideos = new AdapterVideos(activity,videosArrayList);
            adaptervideos.notifyDataSetChanged();
            recyclerView.setAdapter(adaptervideos);

          //  buscar.setImeOptions(EditorInfo.IME_ACTION_DONE);
            buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    adaptervideos.getFilter().filter(newText);
                    return true;
                }
            });


            if(no_hay_cont) {
                no_hay.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            else {
                no_hay.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }

        if(que_hacer.equals("CargarSpinner")) {

            categoria.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, datosSpinner));
            categoria.setSelection(0);

            lista_categorias_set = new HashSet<>(Arrays.asList(lista_categorias));
            lista_categorias_set.remove("Todas");

            SharedPreferences preferencias = context.getSharedPreferences("listita_categorias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();

            editor.putInt("tamaño",lista_categorias.length);
            editor.putStringSet("categorias",lista_categorias_set);
            editor.apply();
        }

        if(que_hacer.equals("Insertar")) {
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new TestimoniosVideosFragment()).commit();
        }

        if(que_hacer.equals("Modificar") || que_hacer.equals("Eliminar")){
            Toast.makeText(context,mensaje_devuelto, Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new TestimoniosVideosFragment()).commit();

        }


    }



}
