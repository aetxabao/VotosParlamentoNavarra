package com.pmdm.votosparlamentonavarra;

import java.util.Locale;

public class Mun {

    private static final String[] TILDES = new String[]{
            "Á", "É", "Í", "Ó", "Ú", "Ü", "Ñ"};
    private static final String[] NOTILDES = new String[]{
            "A", "E", "I", "O", "U", "U", "N"};

    int codigo = 0;
    String nombreofi = "";
    String maysintil = "";

    public Mun(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombreofi = nombre;
        this.maysintil = mayusculasSinTildes(nombre);
    }

    public static final String mayusculasSinTildes(String nombre) {
        String str = nombre.toUpperCase(new Locale("es", "ES"));
        int n = TILDES.length;
        for (int i = 0; i < n; i++)
            str = str.replaceAll(TILDES[i], NOTILDES[i]);
        return str;
    }

    public Mun clone() {
        Mun m = new Mun(this.codigo, new String(this.nombreofi));
        return m;
    }
}
