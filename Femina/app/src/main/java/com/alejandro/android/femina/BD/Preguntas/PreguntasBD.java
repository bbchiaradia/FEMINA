package com.alejandro.android.femina.BD.Preguntas;

        import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
        import android.widget.Toast;

        import androidx.recyclerview.widget.RecyclerView;

import com.alejandro.android.femina.Adaptadores.AdapterIPreguntas;
import com.alejandro.android.femina.BD.Data.DatosBD;
import com.alejandro.android.femina.Entidades.PreguntasTest;
import com.alejandro.android.femina.Entidades.Test;
import com.alejandro.android.femina.Session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

//Permite tener metodos adicionales
//Cuando vamos a ejecutar un proceso en segundo plano utilizamos asyncTask
//onPreExecute() -- > Aqui podemos inicializar variables que vamos a utilizar en onPreExecute()
//DoInbackground -> Es el proceso en si que se ejecuta por atras , SI O SI DEVUELVE RESULTADOS.
//OnPostExecute() --> aqui lo voy a visualizar.
public class PreguntasBD  extends AsyncTask<String, Void, String> {

    private Session ses;
    private Test test;
    private Context context;
    private TextView txtPregunta;
    private RecyclerView recyclerView;
    Activity activity;
    private ProgressDialog dialog;
    private int idTest;

    private AdapterIPreguntas adaptador;
    public ListView lvlPreguntas;

    private ArrayList<PreguntasTest> arrP = new ArrayList<PreguntasTest>();
    //private AdapterPreguntas adapterPreg ;

    public PreguntasBD(Context ct , ArrayList<PreguntasTest> arrayPreguntas , ListView lvlPregunta, int idTest){// , Test tes){ //PreguntasDB
        ses = new Session();
        this.arrP = arrayPreguntas;
        ses.setCt(ct);
        ses.cargar_session();
        test = new Test();
        this.context = ct;
        this.lvlPreguntas = lvlPregunta;
        dialog = new ProgressDialog(ct);
        this.idTest = idTest;
    }





    @Override
    protected String doInBackground(String... strings) { //aqui vamos ejecutar por atras lo que queremos realizar.

        String response = "";
        PreparedStatement ps;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
            Statement st = con.createStatement();
            ResultSet rs;

            rs = st.executeQuery("SELECT * FROM PreguntasTest where idTest = " + this.idTest);

            Test test = new Test();
            test.setId_test(1);
            test.setNombre_test("Test1");
            while (rs.next()) {
                //txtPregunta.setText(rs.getString("textoPregunta"));
                PreguntasTest preg = new PreguntasTest();
                preg.setId_pregunta(rs.getInt("idPregunta"));
                preg.setTexto_pregunta(rs.getString("textoPregunta"));
                preg.setId_test(test);
                arrP.add(preg);
            }
            Iterator it =  arrP.iterator();
            while (it.hasNext()){
                PreguntasTest preg2 = (PreguntasTest) it.next();
                System.out.println(" ID PREGUNTA " + preg2.getId_pregunta());
                System.out.println(" PREGUNTA " + preg2.getTexto_pregunta());
            }
            response = "Conexion exitosa";
            con.close();
        }catch(Exception e){
            e.printStackTrace();
            response = "Conexion no exitosa";
        }
        return response;
    }

    protected void onPreExecute() {
        dialog.setMessage("Cargando Preguntas...");
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
    }

    @Override
    protected void onPostExecute(String response) {//Aqui vamos a tener lo que vamos a visualizar en la pantalla

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(dialog.getProgress() <= dialog.getMax()){
                        Thread.sleep(100);
                        dialog.incrementProgressBy(10);
                        if(dialog.getProgress() == dialog.getMax()){
                            dialog.dismiss();
                        }
                    }
                }catch (Exception e){

                }
            }
        }).start();
        if(arrP.size()==0){
            Toast.makeText(context,"El Test no presenta preguntas cargadas ",Toast.LENGTH_SHORT).show();
        }else {
            adaptador = new AdapterIPreguntas(context, arrP);
            lvlPreguntas.setAdapter(adaptador);
            //System.out.println( "EL VALOR ES BASE DE DATOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS : " + lvlPreguntas.getItemAtPosition(1).);
        }
    }

}

