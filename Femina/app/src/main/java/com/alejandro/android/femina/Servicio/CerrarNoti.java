package com.alejandro.android.femina.Servicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.alejandro.android.femina.Dialogos.DialogoSOS;
import com.alejandro.android.femina.R;

public class CerrarNoti extends AppCompatActivity {

    private CardView me_encuentro_bien,me_encuentro_mal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pantalla_fin_sos);

        me_encuentro_bien = (CardView)findViewById(R.id.card_si_bien);
        me_encuentro_mal = (CardView)findViewById(R.id.card_no_bien);

        me_encuentro_bien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoSOS dialog = new DialogoSOS(CerrarNoti.this,"BIEN");
                dialog.show(getSupportFragmentManager(), "");
            }
        });

        me_encuentro_mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoSOS dialog = new DialogoSOS(CerrarNoti.this,"MAL");
                dialog.show(getSupportFragmentManager(), "");
            }
        });

    }

    public void terminar_servicio(){
        Intent serviceIntent = new Intent(getApplicationContext(), Servicio.class);
        stopService(serviceIntent);
        Toast.makeText(this,"SOS DESACTIVADO",Toast.LENGTH_LONG).show();
        finish();
    }
}
