package com.alejandro.android.femina.Entidades;

public class Videos {

    private int id_video;
    private String titulo;
    private Categorias id_categoria;
    private String url_video;

    public Videos() {
    }

    public Videos(int id_video, String titulo, Categorias id_categoria, String url_video) {
        this.id_video = id_video;
        this.titulo = titulo;
        this.id_categoria = id_categoria;
        this.url_video = url_video;
    }

    public int getId_video() {
        return id_video;
    }

    public void setId_video(int id_video) {
        this.id_video = id_video;
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

    public String getUrl_video() {
        return url_video;
    }

    public void setUrl_video(String url_video) {
        this.url_video = url_video;
    }
}
