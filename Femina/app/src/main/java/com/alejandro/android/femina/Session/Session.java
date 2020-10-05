package com.alejandro.android.femina.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private int id_usuario;
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasena;
    private String telefono;
    private boolean es_admin;
    private String sexo;
    private Context ct;

    public Session(int id_usuario, String nombre, String apellido, String usuario, String contrasena, String telefono, boolean es_admin, String sexo, Context ct) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.es_admin = es_admin;
        this.sexo = sexo;
        this.ct = ct;
    }

    public Session(){
    }

    public void nueva_session(){
        SharedPreferences preferencias = ct.getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_usuario",id_usuario);
        editor.putString("sexo", String.valueOf(sexo));
        editor.putString("nombre",nombre);
        editor.putString("apellido",apellido);
        editor.putString("usuario",usuario);
        editor.putString("contrasena",contrasena);
        editor.putString("telefono",telefono);
        editor.putBoolean("es_admin",es_admin);
        editor.apply();

    }

    public void cargar_session(){

        SharedPreferences preferences = ct.getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        this.id_usuario = preferences.getInt("id_usuario",-1);
        this.nombre = preferences.getString("nombre","");
        this.apellido = preferences.getString("apellido","");
        this.usuario = preferences.getString("usuario","");
        this.contrasena = preferences.getString("contrasena","");
        this.telefono = preferences.getString("telefono","");
        this.sexo = preferences.getString(sexo,"");
        this.es_admin = preferences.getBoolean("es_admin",false);

    }

    public void cerrar_session(){

        SharedPreferences preferencias = ct.getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        this.contrasena = "";
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear();
        editor.apply();

    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEs_admin() {
        return es_admin;
    }

    public void setEs_admin(boolean es_admin) {
        this.es_admin = es_admin;
    }

    public Context getCt() {
        return ct;
    }

    public void setCt(Context ct) {
        this.ct = ct;
    }
}
