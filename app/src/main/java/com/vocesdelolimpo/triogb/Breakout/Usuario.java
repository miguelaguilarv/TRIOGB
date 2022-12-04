package com.vocesdelolimpo.triogb.Breakout;

public class Usuario {

    String name, alias, edad, email, imagen, pais;
    int breakscore;

    public Usuario(){

    }

    public Usuario(String name, String alias, String edad, String email, String imagen, String pais, int breakscore) {
        this.name = name;
        this.alias = alias;
        this.edad = edad;
        this.email = email;
        this.imagen = imagen;
        this.pais = pais;
        this.breakscore = breakscore;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getEdad() {
        return edad;
    }

    public String getEmail() {
        return email;
    }

    public String getImagen() {
        return imagen;
    }

    public String getPais() {
        return pais;
    }

    public int getBreakscore() {
        return breakscore;
    }



}
