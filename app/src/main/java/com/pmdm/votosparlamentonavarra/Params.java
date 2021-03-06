package com.pmdm.votosparlamentonavarra;

import java.util.ArrayList;
import java.util.List;

public class Params {

    public List<Municipio> res1979 = null;
    public List<Municipio> res2007 = null;
    public List<Municipio> res2011 = null;
    public List<Municipio> res2015 = null;
    public List<Mun> muns = null;
    public String filtro = "";
    public List<String> listado = null;
    public int code = -1;

    //singleton
    private static final Params INSTANCE = new Params();

    private Params() {
    }

    public static Params getInstance() {
        return INSTANCE;
    }

    public void filtrarListado(String filtro) {
        this.filtro = filtro.trim();
        listado = new ArrayList<String>();
        int n = muns.size();
        for (int i = 0; i < n; i++) {
            Mun m = muns.get(i);
            if ((this.filtro.length() == 0) || (this.filtro.equals("*"))) {
                listado.add(m.nombreofi);
            } else {
                if (m.maysintil.contains(Mun.mayusculasSinTildes(this.filtro))) {
                    listado.add(m.nombreofi);
                }
            }
        }
    }

    public int getListItemCode(String nombreofi) {
        int code = -1;
        for (Mun m : this.muns) {
            if (nombreofi.equals(m.nombreofi)) {
                code = m.codigo;
                break;
            }
        }
        return code;
    }

    public Mun getMunFromCode(int code) {
        for (Mun m : this.muns) {
            if (m.codigo == code) {
                return m.clone();
            }
        }
        return null;
    }

}
