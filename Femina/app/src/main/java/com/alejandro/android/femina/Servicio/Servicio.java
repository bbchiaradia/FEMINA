package com.alejandro.android.femina.Servicio;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.alejandro.android.femina.Main.MainActivity;
import com.alejandro.android.femina.R;
import com.alejandro.android.femina.Session.Session;
import com.alejandro.android.femina.Session.SessionContactos;
import com.roughike.bottombar.OnTabReselectListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.alejandro.android.femina.Servicio.App.CHANNEL_ID;

public class Servicio extends android.app.Service {

    private myTask miTarea;
    private LocationManager mlocManager;
    private Localizacion Local;
    private volatile boolean stopHilo = false;
    private String direccion,latitud,longitud;
    private MainActivity mainActivity;
    private Handler handler = new Handler();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //String input = intent.getExtras().getString("inputExtra");

        Intent notificationIntent = new Intent(this, CerrarNoti.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Alerta SOS activada")
                .setContentText("Toca para cancelar")
                .setSmallIcon(R.drawable.ic_location)
                .setContentIntent(pendingIntent)
                .build();

        stopHilo = false;

        startForeground(1, notification);

        Toast.makeText(Servicio.this,"SOS ACTIVADO",Toast.LENGTH_LONG).show();

        //HACER TRABAJO PESADO EN UN HILO APARTE

        //stopSelf();

        locationStart();

        myTask myTask = new myTask(3, 5);
        myTask.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopHilo = true;
        mlocManager.removeUpdates(Local);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class myTask extends Thread {

        int minutos;
        int veces;
        int primera_vez;

        public myTask(int minutos, int veces) {
            this.minutos = minutos;
            this.veces = veces;
            primera_vez = 0;
        }

        @Override
        public void run() {

            if(primera_vez == 0){
                try {
                    Thread.sleep(5000);
                    EnviarMensaje();
                    primera_vez = -1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < veces; j++) {
                if (stopHilo)
                    return;
                for (int i = 0; i < minutos; i++) {
                    if (stopHilo)
                        return;
                    Log.d("PASANDO", "" + i);
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(stopHilo)
                    return;
                    Log.d("ENVIANDO", "MENSAJE");
                    EnviarMensaje();


            }

            stopSelf();
        }
    }

    private void locationStart() {
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Local = new Localizacion();
        Local.setMainActivity(mainActivity);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }

        Log.d("LOCALIZACION", "PASA");
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        //latitud.setText("Localización agregada");
        //direccion.setText("");
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion = (DirCalle.getAddressLine(0));
                }

                Log.d("DIRECCION", direccion);
                Log.d("Latitud", latitud);
                Log.d("Longitud",longitud);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void EnviarMensaje(){

        Session session = new Session();
        session.setCt(this);
        session.cargar_session();


        try {
            String messageToSend = "http://maps.google.com/maps?f=q&q=(" + latitud + "," + longitud + ")";
            String mensaje_completo = "" + session.getNombre() + " " + session.getApellido()
              + " se encuentra en peligro, necesita tu ayuda en: " + direccion + " " + messageToSend;
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, Manifest.permission.SEND_SMS)) {
                    ActivityCompat.requestPermissions(mainActivity,
                            new String[]{Manifest.permission.SEND_SMS}, 1);
                } else {
                    ActivityCompat.requestPermissions(mainActivity,
                            new String[]{Manifest.permission.SEND_SMS}, 1);
                }
            }else {

                SessionContactos sessionContactos = new SessionContactos();
                sessionContactos.setContext(getApplicationContext());
                sessionContactos.cargar_session();

                if(sessionContactos.getCant_contactos() == 0){
                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"NO TIENES CONTACTOS REGISTRADOS!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    stopSelf();
                }else{

                    int contactos_cant = sessionContactos.getCant_contactos();
                    String[] contactos = new String[contactos_cant];
                    contactos = sessionContactos.getContactos();

                    for(String s : contactos){

                        try {
                            String number = s;
                            Log.d("CONTACTO",number);

                            SmsManager smsManager = SmsManager.getDefault();
                            ArrayList<String> msgArray = smsManager.divideMessage(mensaje_completo);

                            smsManager.sendMultipartTextMessage(number, null,msgArray, null, null);
                            //Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {
                            //Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                            ex.printStackTrace();
                        }
                    }
                }

               /* try {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(number, null, mensaje_completo,null,null);
                }catch (Exception e){
                    e.printStackTrace();
                }*/

                Log.d("MENSAJEOK","MANDO");
            }
            //Toast.makeText(getApplicationContext(), "Mensaje Enviado!",
              //      Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Fallo el envio!",
                //    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public class Localizacion implements LocationListener {
        MainActivity mainActivity;
        public MainActivity getMainActivity() {
            return mainActivity;
        }
        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String sLatitud = String.valueOf(loc.getLatitude());
            String sLongitud = String.valueOf(loc.getLongitude());
            latitud = (sLatitud);
            longitud = (sLongitud);
            setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //latitud.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //latitud.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }




}
