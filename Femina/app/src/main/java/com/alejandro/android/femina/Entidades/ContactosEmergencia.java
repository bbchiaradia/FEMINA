package com.alejandro.android.femina.Entidades;

public class ContactosEmergencia {

    private int id_contacto_emergencia;
    private Usuarios id_usuario;
    private String telefono;
    private String nombre_contacto;

    public ContactosEmergencia() {
    }

    public ContactosEmergencia(int id_contacto_emergencia, Usuarios id_usuario, String telefono, String nombre_contacto) {
        this.id_contacto_emergencia = id_contacto_emergencia;
        this.id_usuario = id_usuario;
        this.telefono = telefono;
        this.nombre_contacto = nombre_contacto;
    }

    public int getId_contacto_emergencia() {
        return id_contacto_emergencia;
    }

    public void setId_contacto_emergencia(int id_contacto_emergencia) {
        this.id_contacto_emergencia = id_contacto_emergencia;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }
}
