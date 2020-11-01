package com.alejandro.android.femina.BD.Audios;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alejandro.android.femina.Adaptadores.AdaptadorAudios;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Audios;
import com.alejandro.android.femina.Entidades.Categorias;
import com.alejandro.android.femina.Fragments.testimonios.Audios.MediaPlayerUtils;
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
    private String que_hacer;
    private Boolean no_hay_cont;
    private TextView no_hay;
    private Categorias cat;
    private MediaPlayerUtils.Listener mListener;
    private final TestimoniosAudiosFragment testimoniosAudiosFragment;
    Activity activity;
    private Set<String> lista_audios_set;
    private String[] lista_audios;
    ArrayList<Audios> currentAudios;

    public AudiosBD(Context context, ArrayList<Audios> listaAudios , TextView tx,String que,TestimoniosAudiosFragment fragment) {
        listaAudios.clear();
        this.mContext = context;
        this.audiosList = listaAudios;
        no_hay = tx;
        no_hay_cont = true;
        this.que_hacer = que;
        this.testimoniosAudiosFragment = fragment;
        dialog = new ProgressDialog(context);
    }


    @Override
    protected String doInBackground(String... strings) {
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

                rs = st.executeQuery("SELECT a.idAudio, a.Titulo, c.Descripcion, a.urlAudio FROM Audios a inner join Categorias c on a.idCategoria = c.idCategoria");

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
            } catch(Exception e){
                e.printStackTrace();
                response = "Conexion no exitosa";
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

        if(que_hacer.equals("Listar") ) {


            testimoniosAudiosFragment.getAudioList();

        if(no_hay_cont) {
            no_hay.setVisibility(View.VISIBLE);
            //rv.setVisibility(View.GONE);
        }
        else {
            no_hay.setVisibility(View.GONE);
            //rv.setVisibility(View.VISIBLE);
        }

      }

    }
    public void addAudios(Audios a) {
      if (null == currentAudios) {
          currentAudios = new ArrayList<Audios>();
      }
        currentAudios.add(a);

      // save the task list to preference
      SharedPreferences preferencias = mContext.getSharedPreferences("lista_audios", Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = preferencias.edit();
        try {
            editor.putString("AudioList", ObjectSerializer.serialize(audiosList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

}
