package com.alejandro.android.femina.Entidades;

import java.sql.Blob;
import java.sql.Date;

public class Articulos {

    private int id_articulo;
    private String titulo;
    private Categorias id_categoria;
    private String descripcion;
    private Blob imagen;
    private java.sql.Date fecha_carga;
    private int vistas;

    public Articulos() {
    }

    public Articulos(int id_articulo, String titulo, Categorias id_categoria, String descripcion, Blob imagen, Date fecha_carga, int vistas) {
        this.id_articulo = id_articulo;
        this.titulo = titulo;
        this.id_categoria = id_categoria;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fecha_carga = fecha_carga;
        this.vistas = vistas;
    }

    public int getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(int id_articulo) {
        this.id_articulo = id_articulo;
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

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public Date getFecha_carga() {
        return fecha_carga;
    }

    public void setFecha_carga(Date fecha_carga) {
        this.fecha_carga = fecha_carga;
    }

    public int getVistas() {
        return vistas;
    }

    public void setVistas(int vistas) {
        this.vistas = vistas;
    }
}
