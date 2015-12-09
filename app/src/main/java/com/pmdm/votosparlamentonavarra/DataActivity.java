package com.pmdm.votosparlamentonavarra;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {

    public Params pars;
    public TextView lbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        lbl = (TextView) findViewById(R.id.LblData);
        pars = Params.getInstance();

        Mun m = pars.getMunFromCode(pars.code);
        lbl.setText(m.codigo + " - " + m.nombreofi);
    }

}