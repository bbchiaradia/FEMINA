package com.alejandro.android.femina.Entidades;

public class AyudaList {

    private String titulo;
    private String descripcion;
    private int imgAyuda;


    public AyudaList() {
    }

    public AyudaList(String titulo, String descripcion, int imgAyuda) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgAyuda = imgAyuda;
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

    public int getImgAyuda() {
        return imgAyuda;
    }

    public void setImgAyuda(int imgAyuda) {
        this.imgAyuda = imgAyuda;
    }
}
