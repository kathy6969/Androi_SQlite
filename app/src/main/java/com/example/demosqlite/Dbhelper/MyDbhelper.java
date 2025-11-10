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

        // Insert sample data for categories
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Category 1')");
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Category 2')");
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Category 3')");

        String sqlProduct = "CREATE TABLE tb_product (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, price NUMBER NOT NULL DEFAULT 0, id_cat integer, CONSTRAINT fk_category FOREIGN KEY (id_cat) REFERENCES tb_cat (id))";
        db.execSQL(sqlProduct);

        // Insert sample data for products
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('Product 1', 100, 1)");
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('Product 2', 200, 1)");
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('Product 3', 300, 2)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion< newVersion){
            db.execSQL("DROP TABLE IF EXISTS tb_product");
            db.execSQL("DROP TABLE IF EXISTS tb_cat");
            onCreate(db);
        }

    }
}
