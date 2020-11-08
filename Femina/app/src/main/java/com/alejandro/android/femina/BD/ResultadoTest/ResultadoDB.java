package com.alejandro.android.femina.BD.ResultadoTest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ResultadoDB extends AsyncTask<String, Void, String> {

    private int IdResultado, idUsuario, idTest, sufreViolencia ;
    private String que_hacer;
    private Context context;
    private int resultado;
    private TextView textResultadoTest;
    private Session ses;
    private ProgressDialog dialog;


    public ResultadoDB(int idUsuario , int idTest , int sufreViolencia, String que, Context ct){
        this.idUsuario = idUsuario;
        this.idTest = idTest;
        this.sufreViolencia = sufreViolencia;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
    }

    public ResultadoDB(int idTest, Context ct , String que , TextView txtSufreViolencia){
      ses = new Session();
      ses.setCt(ct);
      ses.cargar_session();
      this.idTest = idTest;
      this.context = ct;
      this.que_hacer = que;
      this.textResultadoTest = txtSufreViolencia;
      dialog = new ProgressDialog(ct);
    }



    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        PreparedStatement ps;

        if (que_hacer.equals("grabarResultado")) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM `ResultadosTest` WHERE idUsuario = " + idUsuario); //
                boolean band = rs.next();
                if (band == true) {
                    System.out.println("ENTRO TRUE" + rs.next());
                    st.executeUpdate(" UPDATE `ResultadosTest` SET `sufreViolencia` = '" + sufreViolencia + "' WHERE `idUsuario` = '" + idUsuario + "'"); //

                } else if (band == false) {
                    System.out.println("ENTRO false" + rs.next());
                    st.executeUpdate("INSERT INTO `ResultadosTest` (`idUsuario`, `idTest`, `sufreViolencia`) VALUES ( '" + idUsuario + "', '" + idTest + "', '" + sufreViolencia + "')"); //
                }
                response = "Conexion exitosa";
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                response = "Conexion no exitosa";
            }
        }
        if (que_hacer.equals("ConsultarTestUsuario")) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT sufreViolencia FROM `ResultadosTest` WHERE idUsuario = " + ses.getId_usuario()); //
                while (rs.next()) {
                    this.resultado = rs.getInt("sufreViolencia");
                }
                response = "Conexion exitosa";
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
                response = "Conexion no exitosa";
            }
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
      if (que_hacer.equals("ConsultarTestUsuario")) {
          if (this.resultado == 1) {
              textResultadoTest.setText("Ultimo Resultado del test : Positivo");
          } else if (this.resultado == 0) {
              textResultadoTest.setText("Ultimo Resultado del test : Negativo");
          } else {
              textResultadoTest.setText("Usted todavia no realizo el test");
          }
      }
    }
}