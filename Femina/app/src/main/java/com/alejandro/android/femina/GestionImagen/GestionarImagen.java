package com.alejandro.android.femina.GestionImagen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alejandro.android.femina.Fragments.contactos.Agregar_editar.ContactosAEFragment;
import com.alejandro.android.femina.Fragments.que_hacer.Admin.Alta_Modificacion.QueHacerAMFragment;
import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static java.security.AccessController.getContext;


public class GestionarImagen extends AppCompatActivity implements View.OnClickListener {

    public static final String UPLOAD_URL = "http://femina.webcindario.com/upload.php";
    public static final String UPLOAD_KEY = "imagen";
    public static String ID_ARTICULO = "";
    public static final String TAG = "MY MESSAGE";

    private int id_articulo;

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;
    private Button buttonView;

    private ImageView imageView;

    private Bitmap bitmap;

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_foto);
        imageView =  findViewById(R.id.imageView_Art);
        showFileChooser();

        id_articulo  = getIntent().getIntExtra("id_articulo",-1);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmp, 325 /*Ancho*/, 285 /*Alto*/, false /* filter*/);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GestionarImagen.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {

                Bundle datosAEnviar = new Bundle();

                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                datosAEnviar.putInt("id_articulo", id_articulo);

                Intent intent = new Intent(GestionarImagen.this, MainActivity.class);
                intent.putExtra("id_articulo",id_articulo);
                GestionarImagen.this.startActivity(intent);

            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                Log.d("ALTO:","" + bitmap.getHeight());
                Log.d("ANCHO:","" + bitmap.getWidth());
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                ID_ARTICULO = "" + id_articulo;
                data.put("idarticulo",ID_ARTICULO);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadImage();
        }
        if(v == buttonView){
            viewImage();
        }
    }

    private void viewImage() {
        startActivity(new Intent(this, ViewImage.class));
    }
}
