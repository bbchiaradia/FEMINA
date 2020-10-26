package com.alejandro.android.femina.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionContactos {

    private String[] contactos;
    private int cant_contactos;
    private Context context;

    public SessionContactos(){}

    public SessionContactos(String[] contactos, int cant_contactos, Context context) {
        this.contactos = contactos;
        this.cant_contactos = cant_contactos;
        this.context = context;
    }

    public void nueva_session(){
        SharedPreferences preferencias = context.getSharedPreferences("contactos_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        if(this.cant_contactos == 1)
            editor.putString("contacto_0",this.contactos[0]);

        if(this.cant_contactos == 2) {
            editor.putString("contacto_0", this.contactos[0]);
            editor.putString("contacto_1", this.contactos[1]);
        }

        if(this.cant_contactos == 3) {
            editor.putString("contacto_0", this.contactos[0]);
            editor.putString("contacto_1", this.contactos[1]);
            editor.putString("contacto_2", this.contactos[2]);
        }

        if(this.cant_contactos == 4) {
            editor.putString("contacto_0", this.contactos[0]);
            editor.putString("contacto_1", this.contactos[1]);
            editor.putString("contacto_2", this.contactos[2]);
            editor.putString("contacto_3", this.contactos[3]);
        }

        editor.putInt("cantidad_contactos",cant_contactos);

        editor.apply();

    }

    public void cargar_session(){

        SharedPreferences preferences = context.getSharedPreferences("contactos_usuario", Context.MODE_PRIVATE);
        this.cant_contactos = preferences.getInt("cantidad_contactos",0);

        this.contactos = new String[preferences.getInt("cantidad_contactos",0)];

        if(cant_contactos!=0){

            int i ;
            String contacto;

            for(i = 0; i<cant_contactos;i++) {
                contacto = "contacto_" + i;
                this.contactos[i] = preferences.getString(contacto,"");
            }

            }

        }

    public void cerrar_session(){

        SharedPreferences preferencias = context.getSharedPreferences("contactos_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear();
        editor.apply();

    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getCant_contactos() {
        return cant_contactos;
    }

    public void setCant_contactos(int cant_contactos) {
        this.cant_contactos = cant_contactos;
        this.contactos = new String[cant_contactos];
    }

    public String[] getContactos() {
        return contactos;
    }

    public void setContactos(String[] contactos) {
        this.contactos = contactos;
    }
}
