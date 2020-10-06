package com.alejandro.android.femina.Entidades;

import android.widget.ListView;

public class EntidadIcono {
    private int imgIcono;
    private String titulo;

    public EntidadIcono(int imgIcono, String titulo){
        this.imgIcono = imgIcono;
        this.titulo = titulo;
    }

    public  int getImgIcono(){return  imgIcono; }
    public String getTitulo(){return  titulo; }
    private ListView lvIconos;

}
