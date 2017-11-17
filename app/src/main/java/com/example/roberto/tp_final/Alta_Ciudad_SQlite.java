package com.example.roberto.tp_final;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Alta_Ciudad_SQlite extends AppCompatActivity {

    private TextView txtID;
    private TextView txtNombre;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_ciudades);

        txtID = (TextView) findViewById(R.id.txtID);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlmacenaCiudad();
                Volver();
            }
        });

    }

    //Manejo de Menues
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alta_ciudad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuVolver:
                Volver();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //guarda datos de Ciudad en la Base
    public void AlmacenaCiudad() {
        CitySQLiteHelper ciudadSQLiteHelper = new CitySQLiteHelper(this, "dbCiudades", null, 1);
        SQLiteDatabase db = ciudadSQLiteHelper.getWritableDatabase();

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("id", Integer.parseInt(txtID.getText().toString()));
        nuevoRegistro.put("ciudad", txtNombre.getText().toString());

        db.insert("ciudades", null, nuevoRegistro);

        txtID.setText("");
        txtNombre.setText("");
        db.close();
    }

    public void Volver(){
        Intent pantalla = new Intent(this, MainActivity.class);
        pantalla.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pantalla);
    }

}

