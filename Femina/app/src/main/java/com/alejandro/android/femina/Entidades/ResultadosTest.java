package com.alejandro.android.femina.Entidades;

public class ResultadosTest {

    private int id_resultado;
    private Usuarios id_usuario;
    private Test id_test;
    private boolean sufre_violencia;

    public ResultadosTest() {
    }

    public ResultadosTest(int id_resultado, Usuarios id_usuario, Test id_test, boolean sufre_violencia) {
        this.id_resultado = id_resultado;
        this.id_usuario = id_usuario;
        this.id_test = id_test;
        this.sufre_violencia = sufre_violencia;
    }

    public int getId_resultado() {
        return id_resultado;
    }

    public void setId_resultado(int id_resultado) {
        this.id_resultado = id_resultado;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Test getId_test() {
        return id_test;
    }

    public void setId_test(Test id_test) {
        this.id_test = id_test;
    }

    public boolean isSufre_violencia() {
        return sufre_violencia;
    }

    public void setSufre_violencia(boolean sufre_violencia) {
        this.sufre_violencia = sufre_violencia;
    }
}
