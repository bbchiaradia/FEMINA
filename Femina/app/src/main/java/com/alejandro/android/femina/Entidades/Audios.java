package com.alejandro.android.femina.Entidades;

public class Audios {

    private int id_audio;
    private String titulo;
    private Categorias id_categoria;
    private String url_audio;

    public Audios() {
    }

    public Audios(int id_audio, String titulo, Categorias id_categoria, String url_audio) {
        this.id_audio = id_audio;
        this.titulo = titulo;
        this.id_categoria = id_categoria;
        this.url_audio = url_audio;
    }

    public Audios( String titulo, Categorias id_categoria, String url_audio) {
        this.titulo = titulo;
        this.id_categoria = id_categoria;
        this.url_audio = url_audio;
    }

    public int getId_audio() {
        return id_audio;
    }

    public void setId_audio(int id_audio) {
        this.id_audio = id_audio;
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

    public String getUrl_audio() {
        return url_audio;
    }

    public void setUrl_audio(String url_audio) {
        this.url_audio = url_audio;
    }
}
