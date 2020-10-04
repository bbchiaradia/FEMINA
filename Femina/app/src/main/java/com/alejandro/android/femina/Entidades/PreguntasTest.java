package com.alejandro.android.femina.Entidades;

public class PreguntasTest {

    private int id_pregunta;
    private Test id_test;
    private String texto_pregunta;

    public PreguntasTest() {
    }

    public PreguntasTest(int id_pregunta, Test id_test, String texto_pregunta) {
        this.id_pregunta = id_pregunta;
        this.id_test = id_test;
        this.texto_pregunta = texto_pregunta;
    }

    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public Test getId_test() {
        return id_test;
    }

    public void setId_test(Test id_test) {
        this.id_test = id_test;
    }

    public String getTexto_pregunta() {
        return texto_pregunta;
    }

    public void setTexto_pregunta(String texto_pregunta) {
        this.texto_pregunta = texto_pregunta;
    }
}
