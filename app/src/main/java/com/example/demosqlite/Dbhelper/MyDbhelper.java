package com.example.demosqlite.Dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="my_database.db";
    private static final int DATABASE_VERSION=1;

    public MyDbhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
  String sqlCat = "CREATE TABLE tb_cat (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);";
        db.execSQL(sqlCat);


        String sqlProduct = "CREATE TABLE tb_product (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, price NUMBER NOT NULL DEFAULT 0, id_cat integer, CONSTRAINT fk_category FOREIGN KEY (id_cat) REFERENCES tb_cat (id))";
        db.execSQL(sqlProduct);}


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion< newVersion){
            db.execSQL("DROP TABLE IF EXISTS tb_cat");
            db.execSQL("DROP TABLE IF EXISTS tb_product");
        }

    }
}
