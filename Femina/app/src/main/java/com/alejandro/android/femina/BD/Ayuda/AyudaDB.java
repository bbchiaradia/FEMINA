package com.alejandro.android.femina.BD.Ayuda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.Adaptadores.AdaptadorContactos;
import com.alejandro.android.femina.Adaptadores.AdapterAyuda;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Ayuda;
import com.alejandro.android.femina.Entidades.ContactosEmergencia;
import com.alejandro.android.femina.Fragments.contactos.Principal.ContactosFragment;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AyudaDB extends AsyncTask<String, Void, String> {


    private Ayuda ayu;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private ListView layuda;

    private static ArrayList<Ayuda> listaAyuda = new ArrayList<Ayuda>();

    public  AyudaDB(Context ct, ListView lv, String que) {
        listaAyuda.clear();
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        ayu = new Ayuda();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        layuda = lv;

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

                rs = st.executeQuery("SELECT * FROM Ayuda ");

                while (rs.next()) {

                    ayu = new Ayuda();
                    ayu.setId_ayuda(rs.getInt("idAyuda"));
                    ayu.setTitulo(rs.getString("Titulo"));
                    ayu.setDescripcion(rs.getString("Descripcion"));
                    if (ayu.getId_ayuda() == 1) {
                        listaAyuda.add(new Ayuda(R.drawable.new_logo_femina, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 2){
                        listaAyuda.add(new Ayuda(R.drawable.new_testimonios, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 3){
                        listaAyuda.add(new Ayuda(R.drawable.new_contactosemergencia, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 4){
                        listaAyuda.add(new Ayuda(R.drawable.new_test, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 5){
                        listaAyuda.add(new Ayuda(R.drawable.new_quehacer, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 6){
                        listaAyuda.add(new Ayuda(R.drawable.new_perfil, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 7){
                        listaAyuda.add(new Ayuda(R.drawable.new_cambiaricono, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 8){
                        listaAyuda.add(new Ayuda(R.drawable.ic_sms, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 9){
                        listaAyuda.add(new Ayuda(R.drawable.ic_calls, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 10){
                        listaAyuda.add(new Ayuda(R.drawable.ic_calls, ayu.getTitulo(),ayu.getDescripcion()));
                    }else if(ayu.getId_ayuda() == 11){
                        listaAyuda.add(new Ayuda(R.drawable.ic_arriba, ayu.getTitulo(),ayu.getDescripcion()));
                    }


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

        if(que_hacer.equals("Listar")) {

            final AdapterAyuda adapter = new AdapterAyuda(context, listaAyuda);
            layuda.setAdapter(adapter);

        }



    }


}
