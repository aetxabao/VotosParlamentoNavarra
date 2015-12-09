package com.pmdm.votosparlamentonavarra;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.pmdm.votosparlamentonavarra.Ctes.*;

public class MainActivity extends AppCompatActivity {

    public TextView tvTitle;
    public TextView tvLoading;
    public ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.Title);
        tvLoading = (TextView) findViewById(R.id.Load);
        pb = (ProgressBar) findViewById(R.id.Progress);

        LoadData ld = new LoadData();
        ld.execute(1000);
    }

    public ArrayList<Municipio> readResRawJson(int ID, String MUNICIPIO, String CODIGO,
                                               String[] CONCEPTOS, String[] PARTIDOS) {
        ArrayList<Municipio> resultados = new ArrayList<Municipio>();
        try {
            String json = readRawId(ID);
            JSONArray jaRes = new JSONArray(json);
            int n = jaRes.length();
            for (int i = 0; i < n; i++) {
                JSONObject joMun = jaRes.getJSONObject(i);
                String nombre = joMun.getString(MUNICIPIO);
                int codigo = joMun.getInt(CODIGO);
                Municipio mun = new Municipio(codigo, nombre);
                for (String STR : CONCEPTOS) {
                    int val = joMun.getInt(STR);
                    if (val > 0)
                        mun.put(STR, val);
                }
                for (String STR : PARTIDOS) {
                    int val = joMun.getInt(STR);
                    if (val > 0)
                        mun.put(STR, val);
                }
                resultados.add(mun);
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
        return resultados;
    }

    public ArrayList<Mun> readMunsRawJson(int ID, String MUNICIPIO, String CODIGO) {
        ArrayList<Mun> muns = new ArrayList<Mun>();
        try {
            String json = readRawId(ID);
            JSONArray jaRes = new JSONArray(json);
            int n = jaRes.length();
            for (int i = 0; i < n; i++) {
                JSONObject joMun = jaRes.getJSONObject(i);
                int codigo = joMun.getInt(CODIGO);
                String nombre = joMun.getString(MUNICIPIO);
                Mun m = new Mun(codigo, nombre);
                muns.add(m);
            }
            //Ordenar
            Comparator<Mun> comparator = new Comparator<Mun>() {
                public int compare(Mun m1, Mun m2) {
                    return m1.maysintil.compareTo(m2.maysintil);
                }
            };
            Collections.sort(muns, comparator);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
        return muns;
    }

    public String readRawId(int RAWID) {
        if (Build.VERSION.SDK_INT <= 10) {
            return readRawId10(RAWID);
        } else {
            return readRawIdOK(RAWID);
        }
    }

    public String readRawId10(int RAWID) {
        String json = null;
        InputStream is = getResources().openRawResource(RAWID);
        StringBuilder sb = new StringBuilder("");
        char[] buffer = new char[1024];
        try {
            //http://stackoverflow.com/questions/7254962/json-parsing-problem
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                for (int i = 0; i < n; i++) {
                    if ((int) buffer[i] < 65000) {
                        sb.append(buffer[i]);
                    }
                }
            }
            json = sb.toString();
        } catch (Exception e) {
            Log.d("ERROR RD10", e.getMessage());
            return null;
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
        return json;
    }

    public String readRawIdOK(int RAWID) {
        String json = null;
        InputStream is = getResources().openRawResource(RAWID);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            Log.d("ERROR WR", e.getMessage());
            return null;
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
        json = writer.toString();
        return json;
    }

    class LoadData extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... ms) {
            Params pars = Params.getInstance();
            Date d1 = new Date();
            publishProgress(0);
            pars.res1979 = readResRawJson(RPPM1979, MUNICIPIO_1979, CODIGO_1979,
                    CONCEPTOS_1979, PARTIDOS_1979);
            publishProgress(20);
            pars.res2007 = readResRawJson(RPPM2007, MUNICIPIO_2000, CODIGO_2000,
                    CONCEPTOS_2000, PARTIDOS_2007);
            publishProgress(40);
            pars.res2011 = readResRawJson(RPPM2011, MUNICIPIO_2000, CODIGO_2000,
                    CONCEPTOS_2000, PARTIDOS_2011);
            publishProgress(60);
            pars.res2015 = readResRawJson(RPPM2015, MUNICIPIO_2015, CODIGO_2015,
                    CONCEPTOS_2015, PARTIDOS_2015);
            publishProgress(80);
            pars.muns = readMunsRawJson(RPPM2015, MUNICIPIO_2015, CODIGO_2015);
            publishProgress(100);
            pars.filtrarListado("");
            Date d2 = new Date();
            Integer I = (int) (d2.getTime() - d1.getTime());
//            try {
//               Thread.sleep(ms[0]);
//            } catch(Exception e) {}
            return I;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress[0]);
            pb.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            tvLoading.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.INVISIBLE);
            pb.setProgress(0);
            Toast.makeText(getApplicationContext(), "t=" + result + "ms", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }

    }

}