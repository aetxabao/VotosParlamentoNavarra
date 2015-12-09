package com.pmdm.votosparlamentonavarra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {

    public Params pars;

    public EditText txt;
    public ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txt = (EditText) findViewById(R.id.TxtFilter);
        lv = (ListView) findViewById(R.id.List);

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
//                             Toast.makeText(getApplicationContext(),
//                                             "Municipio: " + codigo + "-" + nombre, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListActivity.this, DataActivity.class);
                startActivity(intent);
            }

        });
    }

}

