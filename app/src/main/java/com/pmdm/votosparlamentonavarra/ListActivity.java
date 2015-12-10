package com.pmdm.votosparlamentonavarra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class ListActivity extends AppCompatActivity {

    public Params pars;

    public EditText txt;
    public ListView lv;
    public RadioButton rb1979;
    public RadioButton rb2007;
    public RadioButton rb2011;
    public RadioButton rb2015;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txt = (EditText) findViewById(R.id.TxtFilter);
        lv = (ListView) findViewById(R.id.List);
        rb1979 = (RadioButton)findViewById(R.id.Rb1979);
        rb2007 = (RadioButton)findViewById(R.id.Rb2007);
        rb2011 = (RadioButton)findViewById(R.id.Rb2011);
        rb2015 = (RadioButton)findViewById(R.id.Rb2015);

        pars = Params.getInstance();

        txt.setText(pars.filtro);
        setListView();
    }

    public void filter(View v) {
        String str = txt.getText().toString();
        pars.filtrarListado(str);
        setListView();
    }

    public void setListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, pars.listado);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, final View view,

                                    int position, long id) {
                String nombre = (String) parent.getItemAtPosition(position);
                int codigo = pars.getListItemCode(nombre);
                pars.code = codigo;
                if (rb1979.isChecked()) pars.year = 1979;
                if (rb2007.isChecked()) pars.year = 2007;
                if (rb2011.isChecked()) pars.year = 2011;
                if (rb2015.isChecked()) pars.year = 2015;
                Intent intent = new Intent(ListActivity.this, DataActivity.class);
                startActivity(intent);
            }

        });
    }

}

