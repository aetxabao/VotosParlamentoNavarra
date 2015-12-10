package com.pmdm.votosparlamentonavarra;

import java.util.HashMap;
import java.util.Map;

public class Municipio extends Mun {

    Map<String, Integer> map = null;

    public Municipio(int codigo, String nombre) {
        super(codigo, nombre);
        map = new HashMap<String, Integer>();
    }

    public Integer get(String nombre){
        if (map.containsKey(nombre)){
            return map.get(nombre);
        }else{
            return 0;
        }
    }

    public void put(String nombre, Integer valor) {
        map.put(nombre, valor);
    }

    public int getVotosOk(int ano){
        if (ano==1979) return get("A_CANDID");
        else if (ano==2015) return get("Votos_Validos");
        else return (get("Válidos") - get("Blanco"));
    }

    public int getVotosBlanco(int ano){
        if (ano==1979) return get("BLANCOS");
        else if (ano==2015) return get("Votos_blancos");
        else return get("Blanco");
    }

    public int getVotosNulo(int ano){
        if (ano==1979) return get("NULOS");
        else if (ano==2015) return get("Votos_nulos");
        else return get("Nulos");
    }

    public int getAbstencion(int ano){
        if (ano==1979) return get("ABSTENCION");
        else if (ano==2015) return get("Abstencion");
        else return get("Abstención");
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{codigo:"+codigo+",nombreofi:'"+nombreofi+"',maysintil:'"+maysintil+"',map:{");
        for(String key:map.keySet()){
            sb.append("'"+key+"':"+map.get(key)+",");
        }
        String str = sb.toString();
        str = str.substring(0,str.length()-1) + "}}";
        return str;
    }

}