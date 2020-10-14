package com.alejandro.android.femina.BD.Videos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Adaptadores.AdapterVideos;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Categorias;
import com.alejandro.android.femina.Entidades.Videos;
import com.alejandro.android.femina.R;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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

    if(!que_hacer.equals("CargarSpinner")) {
        dialog.setMessage("Procesando...");
        dialog.show();
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
            categoria.setSelection(6);
        }

    }



}
