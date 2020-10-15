package com.alejandro.android.femina.Entidades;

import android.widget.ListView;

public class Ayuda {

    private int id_ayuda;
    private String titulo;
    private Categorias id_categoria;
    private String descripcion;
    private int imgAyuda;


    public Ayuda() {
    }

    public Ayuda(int id_ayuda, String titulo, int imgAyuda, String descripcion) {
        this.id_ayuda = id_ayuda;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgAyuda = imgAyuda;
    }

    public Ayuda(int imgAyuda, String titulo, String descripcion) {
        this.id_ayuda = id_ayuda;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgAyuda = imgAyuda;
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


    public  int getImgAyuda(){return  imgAyuda; }

    private ListView lvAyuda;

}
