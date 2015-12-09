package com.pmdm.votosparlamentonavarra;

import java.util.HashMap;
import java.util.Map;

public class Municipio extends Mun {

    Map<String, Integer> map = null;

    public Municipio(int codigo, String nombre) {
        super(codigo, nombre);
        map = new HashMap<String, Integer>();
    }

    public void put(String nombre, Integer valor) {
        map.put(nombre, valor);
    }

}