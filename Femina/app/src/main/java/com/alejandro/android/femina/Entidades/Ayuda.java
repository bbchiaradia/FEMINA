package com.alejandro.android.femina.Entidades;

public class Ayuda {

    private int id_ayuda;
    private String titulo;
    private Categorias id_categoria;
    private String descripcion;

    public Ayuda() {
    }

    public Ayuda(int id_ayuda, String titulo, Categorias id_categoria, String descripcion) {
        this.id_ayuda = id_ayuda;
        this.titulo = titulo;
        this.id_categoria = id_categoria;
        this.descripcion = descripcion;
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

    public Categorias getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Categorias id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
