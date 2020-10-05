package com.alejandro.android.femina.Entidades;

public class Usuarios {

    private int id_usuario;
    private String usuario;
    private String contrasena;
    private String nombre;
    private String apellido;
    private char sexo;
    private String telefono;
    private boolean es_admin;

    public Usuarios() {
    }

    public Usuarios(int id_usuario, String usuario, String contrasena, String nombre, String apellido, char sexo, String telefono, boolean es_admin) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.telefono = telefono;
        this.es_admin = es_admin;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEs_admin() {
        return es_admin;
    }

    public void setEs_admin(boolean es_admin) {
        this.es_admin = es_admin;
    }

    public String validarDatosPerfil(){

        String mensaje_devuelto = "si";

        if(this.contrasena.length()< 6 || this.contrasena.length() > 8 )
            mensaje_devuelto = "La contraseña debe contar con un mínimo y máximo de 6 y 8 caracteres respectivamente";

        if(this.usuario.length()< 3 || this.usuario.length() > 8 )
            mensaje_devuelto = "Tu usuario debe contar con un mínimo y máximo de 3 y 8 caracteres respectivamente";

        if(this.nombre.length()< 3 || this.nombre.length() > 25 )
            mensaje_devuelto = "Tu nombre debe contar con un mínimo y máximo de 3 y 25 caracteres respectivamente";

        if(this.apellido.length()< 3 || this.apellido.length() > 25 )
            mensaje_devuelto = "Tu apellido debe contar con un mínimo y máximo de 3 y 25 caracteres respectivamente";

        if(this.telefono.length()< 10 || this.telefono.length() > 15 )
            mensaje_devuelto = "Tu telefono debe contar con un mínimo y máximo de 10 y 15 caracteres respectivamente";

        return mensaje_devuelto;

    }

}
