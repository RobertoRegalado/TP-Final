package com.example.roberto.tp_final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CitySQLiteHelper extends SQLiteOpenHelper {

    //crea la tabla ciudades
    String sqlCrear = "CREATE TABLE ciudades(auto_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ciudad TEXT, id INTEGER)";

    public CitySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCrear);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //se elimina version anterior de tabla
        db.execSQL("DROP TABLE IF EXISTS ciudades");
        //se crea nueva version de tabla
        db.execSQL(sqlCrear);
    }

}
