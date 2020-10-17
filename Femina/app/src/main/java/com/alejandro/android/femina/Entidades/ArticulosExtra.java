package com.alejandro.android.femina.Entidades;

import android.graphics.Bitmap;

public class ArticulosExtra {

    private Articulos articulo;
    private Bitmap imagen_articulo;

    public ArticulosExtra() {
    }

    public ArticulosExtra(Articulos articulo, Bitmap imagen_articulo) {
        this.articulo = articulo;
        this.imagen_articulo = imagen_articulo;
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
    }

    public Bitmap getImagen_articulo() {
        return imagen_articulo;
    }

    public void setImagen_articulo(Bitmap imagen_articulo) {
        this.imagen_articulo = imagen_articulo;
    }
}
