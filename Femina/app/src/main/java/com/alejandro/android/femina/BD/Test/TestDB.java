package com.alejandro.android.femina.BD.Test;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.Test;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TestDB extends AsyncTask<String, Void, String> {

    private int IdResultado, idUsuario, idTest, sufreViolencia ;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private ArrayList<Test> arrayTest = new ArrayList<Test>();
    private Session ses;
    private Spinner spinnerTest;

    public TestDB(Context ct , Spinner sp){
      this.context = ct;
      //ses.setCt(ct);
      //ses.cargar_session();
      this.spinnerTest = sp;
      dialog = new ProgressDialog(ct);
    }



    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        PreparedStatement ps;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM `Test` where estado = 1" ); //
                while (rs.next()) {
                   Test test = new Test();
                   test.setId_test(rs.getInt("idTest"));
                   test.setNombre_test(rs.getString("nombreTest"));
                   System.out.println("NOMBRE TEST " + rs.getString("nombreTest"));
                   arrayTest.add(test);
                }
                response = "Conexion exitosa";
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                response = "Conexion no exitosa";
            }
        return null;
    }

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
        ArrayAdapter<Test> adapter = new ArrayAdapter<>(this.context,android.R.layout.simple_spinner_dropdown_item,arrayTest);
        spinnerTest.setAdapter(adapter);

    }
}