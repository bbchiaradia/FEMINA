package com.alejandro.android.femina.Entidades;

public class Test {

    private int id_test;
    private String nombre_test;

    public Test() {
    }

    public Test(int id_test, String nombre_test) {
        this.id_test = id_test;
        this.nombre_test = nombre_test;
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    public String getNombre_test() {
        return nombre_test;
    }

    public void setNombre_test(String nombre_test) {
        this.nombre_test = nombre_test;
    }
}