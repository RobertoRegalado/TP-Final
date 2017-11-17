package com.example.roberto.tp_final;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView listaCiudades;
    private SwipeRefreshLayout sflLista;
    private CityAdapter adapter;
    private CitySQLiteHelper citySQLiteHelper;
    private SQLiteDatabase db;
    private ArrayList<City> Ciudades;

    //Manejo de Menues
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSalir:
                Toast.makeText(MainActivity.this, "Saliendo", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.mnuGuardar:
                Intent pantalla = new Intent(MainActivity.this, Alta_Ciudad_SQlite.class);
                startActivity(pantalla);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Lee Base de datos y carga Listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaCiudades = (ListView) findViewById(R.id.listaCiudades);
        sflLista = (SwipeRefreshLayout) findViewById(R.id.sflLista);
        Ciudades = new ArrayList<City>();


        //abrimos db en modo escritura
        citySQLiteHelper = new CitySQLiteHelper(this, "dbCiudades", null, 1);
        db = citySQLiteHelper.getWritableDatabase();


        //definimos adaptador del listview
        adapter = new CityAdapter(Ciudades);
        listaCiudades.setAdapter(adapter);


        listaCiudades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City item = (City) listaCiudades.getItemAtPosition(position);
                int Id = item.getId();
                abrirPantalla(Id);
            }
        });
        sflLista.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sflLista.setRefreshing(false);
            }
        });
        update();
    }
        private List<City> obtenerCiudades(){
            cargarLista();
            //seleccionamos todos los registros
            Cursor cursor = db.rawQuery("SELECT * FROM ciudades",null);
            List<City> lista = new ArrayList<>();

            if(cursor.moveToFirst()){
                //iteramos todos los registros del cursor
                //llenamos el array con registros
                while (cursor.isAfterLast()==false){
                    //recorremos hasta llegar al ultimo registro
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String ciudad = cursor.getString(cursor.getColumnIndex("ciudad"));
                    lista.add(new City(id,ciudad));
                    cursor.moveToNext();
                }
            }
            return lista;
        }

    private void abrirPantalla(int Id) {
        Intent pantalla = new Intent(this, DetalleActivity.class);
        pantalla.putExtra("ID", Id);
        startActivity(pantalla);
    }


    private void update(){
        //borramos todos los elementos
        Ciudades.clear();
        //cargamos todos los registros
        Ciudades.addAll(obtenerCiudades());
        //notificamos al adaptador
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        update();
        //Refresh your stuff here
    }


    private void cargarLista(){

        Ciudades.add(new City(524901,"Moscu"));
        Ciudades.add(new City(3835994,"Santa Rosa"));
        Ciudades.add(new City(3164603,"Venecia"));
        Ciudades.add(new City(5188351,"Egipto"));
        Ciudades.add(new City(6251999,"Canada"));
        Ciudades.add(new City(2643743,"Londres"));
        Ciudades.add(new City(524901,"Moscu"));
        Ciudades.add(new City(3834310,"Toay"));


    }

}
