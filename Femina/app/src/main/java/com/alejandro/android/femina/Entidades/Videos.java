package com.alejandro.android.femina.Entidades;

public class Videos {

    private int id_video;
    private String titulo;
    private int idCategoria;
    private String url_video;

    public Videos() {
    }

    public Videos(int id_video, String titulo, int idCategoria, String url_video) {
        this.id_video = id_video;
        this.titulo = titulo;
        this.idCategoria = idCategoria;
        this.url_video = url_video;
    }


    public Videos( String titulo, int idCategoria, String url_video) {
        this.titulo = titulo;
        this.idCategoria= idCategoria;
        this.url_video = url_video;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
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

    public String getUrl_video() {
        return url_video;
    }

    public void setUrl_video(String url_video) {
        this.url_video = url_video;
    }



}
