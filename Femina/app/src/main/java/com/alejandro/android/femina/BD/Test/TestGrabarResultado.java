package com.alejandro.android.femina.BD.Test;

import android.os.AsyncTask;

import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.PreguntasTest;
import com.alejandro.android.femina.Entidades.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

public class TestGrabarResultado extends AsyncTask<String, Void, String> {

    private int IdResultado, idUsuario, idTest, sufreViolencia ;

    public TestGrabarResultado (int idUsuario , int idTest , int sufreViolencia){
        this.idUsuario = idUsuario;
        this.idTest = idTest;
        this.sufreViolencia = sufreViolencia;
    }
    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        PreparedStatement ps;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `ResultadosTest` WHERE idUsuario = "+ idUsuario); //
            boolean band = rs.next();
            if (band == true) {
                System.out.println("ENTRO TRUE" + rs.next());
                st.executeUpdate(" UPDATE `ResultadosTest` SET `sufreViolencia` = '"+ sufreViolencia +"' WHERE `idUsuario` = '"+ idUsuario +"'"); //

            }else if(band == false){
                System.out.println("ENTRO false" + rs.next() );
                st.executeUpdate("INSERT INTO `ResultadosTest` (`idUsuario`, `idTest`, `sufreViolencia`) VALUES ( '"+ idUsuario +"', '"+ idTest +"', '"+ sufreViolencia +"')"); //
            }
            response = "Conexion exitosa";
            con.close();

        }catch(Exception e){
            e.printStackTrace();
            response = "Conexion no exitosa";
        }
        return null;
    }
}