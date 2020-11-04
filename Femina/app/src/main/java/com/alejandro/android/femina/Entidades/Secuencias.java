package com.alejandro.android.femina.Entidades;

public class Secuencias {

    private int id_secuencia;
    private Usuarios id_usuario;
    private String secuencia;
    private Boolean activado;


    public Secuencias() {
    }

    public Secuencias(int id_secuencia, Usuarios id_usuario, String secuencia, Boolean activado) {
        this.id_secuencia = id_secuencia;
        this.id_usuario = id_usuario;
        this.secuencia = secuencia;
        this.activado = activado;
    }

    public int getId_secuencia() {
        return id_secuencia;
    }

    public void setId_secuencia(int id_secuencia) {
        this.id_secuencia = id_secuencia;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
