package com.vocesdelolimpo.triogb.Breakout;

public class Usuario {

    String alias, edad, email, imagen, name, pais, password;
    int breakscore, gatoscore, ordenscore;

    public Usuario(){

    }

    public Usuario(String alias, String edad, String email, String imagen, String name, String pais, String password, int breakscore, int gatoscore, int ordenscore) {

        this.alias = alias;
        this.edad = edad;
        this.email = email;
        this.imagen = imagen;
        this.name = name;
        this.pais = pais;
        this.password = password;
        this.breakscore = breakscore;
        this.gatoscore = gatoscore;
        this.ordenscore = ordenscore;
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

    public String getName() {
        return name;
    }

    public String getPais() {
        return pais;
    }

    public String getPassword() {
        return password;
    }

    public int getBreakscore() {
        return breakscore;
    }

    public int getGatoscore() {
        return gatoscore;
    }

    public int getOrdenscore() {
        return ordenscore;
    }
}
