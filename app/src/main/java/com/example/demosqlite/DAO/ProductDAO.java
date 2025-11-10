package com.example.demosqlite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demosqlite.DTO.ProductDTO;
import com.example.demosqlite.Dbhelper.MyDbhelper;

import java.util.ArrayList;

public class ProductDAO {
    MyDbhelper dbHelper;
    SQLiteDatabase db;

    public ProductDAO(Context context) {
        dbHelper = new MyDbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addProduct(ProductDTO productDTO) {
        ContentValues values = new ContentValues();
        values.put("name", productDTO.getName());
        values.put("price", productDTO.getPrice());
        values.put("id_cat", productDTO.getId_cat());
        return db.insert("tb_product", null, values);
    }

    public ArrayList<ProductDTO> getList() {
        ArrayList<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_product";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(cursor.getInt(0));
                productDTO.setName(cursor.getString(1));
                productDTO.setPrice(cursor.getDouble(2));
                productDTO.setId_cat(cursor.getInt(3));
                list.add(productDTO);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public int updateProduct(ProductDTO productDTO) {
        ContentValues values = new ContentValues();
        values.put("name", productDTO.getName());
        values.put("price", productDTO.getPrice());
        values.put("id_cat", productDTO.getId_cat());
        String[] params = {String.valueOf(productDTO.getId())};
        return db.update("tb_product", values, "id = ?", params);
    }

    public int deleteProduct(int id) {
        String[] params = {String.valueOf(id)};
        return db.delete("tb_product", "id = ?", params);
    }
}
