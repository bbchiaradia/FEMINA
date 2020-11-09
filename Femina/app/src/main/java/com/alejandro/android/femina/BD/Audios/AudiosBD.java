package com.alejandro.android.femina.BD.Audios;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Adaptadores.AdaptadorAudios;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Audios;
import com.alejandro.android.femina.Entidades.Categorias;
import com.alejandro.android.femina.Fragments.testimonios.Audios.ObjectSerializer;
import com.alejandro.android.femina.Fragments.testimonios.Audios.TestimoniosAudiosFragment;
import com.alejandro.android.femina.R;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//
// Created by Juan Manuel on 29/10/2020.
//
public class AudiosBD extends AsyncTask<String, Void, String> {

    Context mContext;
    ArrayList<Audios> audiosList;
    RecyclerView rv;
    private ProgressDialog dialog;
    private AdaptadorAudios adapter;
    private Audios audio;
    private String mensaje_devuelto;
    private String que_hacer, cate;
    private Boolean no_hay_cont;
    private TextView no_hay;
    private Categorias cat;
    private final TestimoniosAudiosFragment testimoniosAudiosFragment;
    Activity activity;
    private Set<String> lista_audios_set;
    private String[] lista_audios;
    ArrayList<Audios> currentAudios;
    private Spinner categoria;
    private static ArrayList<String> datosSpinner = new ArrayList<String>();
    private Set<String> lista_categorias_set;
    private String[] lista_categorias;
    private SearchView buscar;

    public AudiosBD(Context context, ArrayList<Audios> listaAudios, TextView tx, String que, TestimoniosAudiosFragment fragment, RecyclerView myRv) {
        listaAudios.clear();
        this.mContext = context;
        this.audiosList = listaAudios;
        no_hay = tx;
        no_hay_cont = true;
        this.que_hacer = que;
        this.testimoniosAudiosFragment = fragment;
        this.rv = myRv;
        dialog = new ProgressDialog(context);
    }

    public AudiosBD(Context context, ArrayList<Audios> listaAudios, String que, String cat, TextView tx, TestimoniosAudiosFragment fragment, RecyclerView myRv) {
        listaAudios.clear();
        this.mContext = context;
        this.audiosList = listaAudios;
        this.que_hacer = que;
        this.cate = cat;
        no_hay = tx;
        this.testimoniosAudiosFragment = fragment;
        this.rv = myRv;
        dialog = new ProgressDialog(context);
        no_hay_cont = true;
    }

    public AudiosBD(Context context, String que, Spinner spn, TestimoniosAudiosFragment fragment) {
        datosSpinner.clear();
        this.mContext = context;
        this.que_hacer = que;
        this.categoria = spn;
        this.testimoniosAudiosFragment = fragment;
        dialog = new ProgressDialog(context);
    }

    public AudiosBD(Audios a, Context context, String que,TestimoniosAudiosFragment fragment){
        this.audio = a;
        this.mContext = context;
        this.que_hacer = que;
        this.testimoniosAudiosFragment = fragment;
        dialog = new ProgressDialog(context);
    }



    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;

        if (que_hacer.equals("Listar") || que_hacer.equals("SelCategoria") || que_hacer.equals("Buscar")) {

            response = "";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;
                  Log.d("que_hacer", "que: " + que_hacer);

                if (que_hacer.equals("Listar"))
                    rs = st.executeQuery("SELECT a.idAudio, a.Titulo, c.Descripcion, a.urlAudio FROM Audios a inner join Categorias c on a.idCategoria = c.idCategoria");

                else if (cate.equals("Todas"))
                    rs = st.executeQuery("SELECT a.idAudio, a.Titulo, c.Descripcion, a.urlAudio FROM Audios a inner join Categorias c on a.idCategoria = c.idCategoria");

                else if (que_hacer.equals("Buscar"))
                    rs = st.executeQuery("SELECT a.idAudio, a.Titulo, c.Descripcion, a.urlAudio FROM Audios a inner join Categorias c on a.idCategoria = c.idCategoria where a.Titulo LIKE '%" + cate + "%'");
                else
                    rs = st.executeQuery("SELECT a.idAudio, a.Titulo, c.Descripcion, a.urlAudio FROM Audios a inner join Categorias c on a.idCategoria = c.idCategoria where c.Descripcion='" + cate + "'");


                while (rs.next()) {
                    no_hay_cont = false;
                    audio = new Audios();
                    cat = new Categorias();
                    audio.setId_audio(rs.getInt("idAudio"));
                    audio.setTitulo(rs.getString("Titulo"));
                    cat.setDescripcion(rs.getString("Descripcion"));
                    audio.setId_categoria(cat);
                    audio.setUrl_audio(rs.getString("urlAudio"));
                    audiosList.add(audio);
                    addAudios(audio);
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                response = "Conexion no exitosa";
            }

        }

        if (que_hacer.equals("CargarSpinner")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                ResultSet rs;
                Statement st = con.createStatement();

                rs = st.executeQuery("Select count(*) from Categorias");

                while (rs.next()) {
                    lista_categorias = new String[rs.getInt(1)];
                }

                rs = st.executeQuery("SELECT * from Categorias");

                int i = 0;
                datosSpinner.add("Todas");
                while (rs.next()) {

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
                ps = con.prepareStatement("UPDATE Audios SET Titulo=?, idCategoria=?, urlAudio=? WHERE idAudio=?");

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("Select idCategoria from Categorias where Descripcion='"
                        + audio.getId_categoria().getDescripcion() + "'");

                if (rs.next())
                    ps.setInt(2, rs.getInt(1));
                else
                    insertamos = false;

                ps.setString(1,audio.getTitulo());
                ps.setString(3,audio.getUrl_audio());
                ps.setInt(4,audio.getId_audio());

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Audio actualizado!";
                    } else
                        mensaje_devuelto = "Error al actualizar el audio!";
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al actualizar el audio!!";
            }
        }

        if (que_hacer.equals("Insertar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("INSERT INTO Audios (Titulo, idCategoria, urlAudio) VALUES(?, ?, ?)");

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("Select idCategoria from Categorias where Descripcion='"
                        + audio.getId_categoria().getDescripcion() + "'");

                if (rs.next())
                    ps.setInt(2, rs.getInt(1));
                else
                    insertamos = false;

                Statement st_ = con.createStatement();
                ResultSet rs_;

                ps.setString(1, audio.getTitulo());
                ps.setString(3, audio.getUrl_audio());

                rs_ = st_.executeQuery("SELECT 1 FROM Audios where urlAudio ='" + audio.getUrl_audio() + "'");

                if (rs_.next()) {
                    insertamos = false;
                    mensaje_devuelto = "La URL ya esta registrada";
                }

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Audio registrado!";
                    } else
                        mensaje_devuelto = "Error al registrar el Audio!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                mensaje_devuelto = "Error al registrar el Audio!";
            }
        }

        if (que_hacer.equals("Eliminar")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("DELETE FROM Audios WHERE idAudio=?");

                ps.setInt(1, audio.getId_audio());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    mensaje_devuelto = "Audio eliminado!";
                } else
                    mensaje_devuelto = "Error al eliminar el Audio!";

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al eliminar el Audio!!";
            }
        }

        return response;
    }

    @Override
    protected void onPreExecute() {

        if (!que_hacer.equals("CargarSpinner")) {
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

        if (que_hacer.equals("Listar") || que_hacer.equals("SelCategoria") || que_hacer.equals("Buscar") ) {

            // Metodo que captura el array enviado por el metodo addAudios
            testimoniosAudiosFragment.getAudioList();

            if (no_hay_cont) {
                no_hay.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
            } else {
                no_hay.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
            }

        }


        if (que_hacer.equals("CargarSpinner")) {

            categoria.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, datosSpinner));
            categoria.setSelection(0);

            lista_categorias_set = new HashSet<>(Arrays.asList(lista_categorias));
            lista_categorias_set.remove("Todas");

            SharedPreferences preferencias = mContext.getSharedPreferences("listita_categorias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();

            editor.putInt("tamaño", lista_categorias.length);
            editor.putStringSet("categorias", lista_categorias_set);
            editor.apply();
        }


        if(que_hacer.equals("Insertar")) {
            Toast.makeText(mContext,mensaje_devuelto,Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new TestimoniosAudiosFragment()).commit();
        }

        if(que_hacer.equals("Modificar") || que_hacer.equals("Eliminar")){
            Toast.makeText(mContext,mensaje_devuelto, Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new TestimoniosAudiosFragment()).commit();

        }

    }

    public void addAudios(Audios a) {
        if (null == currentAudios) {
            currentAudios = new ArrayList<Audios>();
        }
        currentAudios.add(a);
        Log.d("MetAdd", "SizeListAdd: " + currentAudios.size());
        // save the audio list to preference
        SharedPreferences preferencias = mContext.getSharedPreferences("lista_audios", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        try {
            editor.putString("AudioList", ObjectSerializer.serialize(currentAudios));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

}
