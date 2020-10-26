package com.alejandro.android.femina.Servicio;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CerrarNoti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent serviceIntent = new Intent(getApplicationContext(),Servicio.class);
        stopService(serviceIntent);
        finish();
        //setContentView(R.layout.activity_cerrar_noti);

    }
}
