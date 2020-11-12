package com.alejandro.android.femina.Entidades;

import android.widget.ListView;

public class Ayuda {

    private int id_ayuda;
    private String titulo;
    private String descripcion;
    private String img_ayuda;

    public Ayuda() {
    }

    public Ayuda(int id_ayuda, String titulo, String descripcion, String img_ayuda) {
        this.id_ayuda = id_ayuda;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.img_ayuda = img_ayuda;
    }

    public int getId_ayuda() {
        return id_ayuda;
    }

    public void setId_ayuda(int id_ayuda) {
        this.id_ayuda = id_ayuda;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg_ayuda() {
        return img_ayuda;
    }

    public void setImg_ayuda(String img_ayuda) {
        this.img_ayuda = img_ayuda;
    }



}
