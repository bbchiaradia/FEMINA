package com.alejandro.android.femina.BD.Videos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alejandro.android.femina.Adaptadores.AdapterVideos;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Videos;

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

    private Videos video;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Boolean no_hay_cont;
 //   private SearchView buscar;
    private TextView no_hay;

    private static ArrayList<Videos> listvideos = new ArrayList<Videos>();

    public VideosBD(Videos vi, Context ct, String que) {
        video = new Videos();
        this.video = vi;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
    }

/*    public VideosDB(Context ct, ListView lv, TextView tx, String que) {
        listvideos.clear();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        listvideos = lv;
        no_hay = tx;
        no_hay_cont = true;
    }*/


    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;

        if (que_hacer.equals("Listar")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT idVideo, Titulo, idCategoria, urlVideo \n" +
                        "FROM Videos");


                while (rs.next()) {
                    no_hay_cont = false;
                    video = new Videos();
                    video.setId_video(rs.getInt("idVIdeo"));
                    video.setTitulo(rs.getString("Titulo"));
                 //   video.setId_categoria(rs.getInt("idCategoria"));
                    video.setUrl_video(rs.getString("urlVideo"));
                    listvideos.add(video);
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


  /*      if(que_hacer.equals("Listar")) {
            final AdapterVideos adapter = new AdapterVideos(context, listvideos);
            listvideos.setAdapter(adapter);

            if(no_hay_cont) {
                no_hay.setVisibility(View.VISIBLE);
                listvideos.setVisibility(View.GONE);
            }
            else {
                no_hay.setVisibility(View.GONE);
                listvideos.setVisibility(View.VISIBLE);
            }
        }*/

    }



}
