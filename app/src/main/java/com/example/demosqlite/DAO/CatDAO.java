package com.example.demosqlite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.demosqlite.DTO.CatDTO;
import com.example.demosqlite.Dbhelper.MyDbhelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CatDAO {

    MyDbhelper dbHelper;
SQLiteDatabase db;
    static String TAG = "zzzzzzzzzzzzzz";

  public CatDAO(Context context) {
        dbHelper = new MyDbhelper(context);
        db = dbHelper.getWritableDatabase();


}
    public int addCat (CatDTO catDTO){
        ContentValues values = new ContentValues();
        values.put("name", catDTO.getName());
        return (int) db.insert("tb_cat", null, values);
}

//--cần viết thêm :hàm lấy danh sách,lấy bản ghi ,sửa ,xóa
public ArrayList<CatDTO> getAllCat(){
    ArrayList<CatDTO> list = new ArrayList<>();
    String sql = "SELECT * FROM tb_cat ";
    Cursor cursor = db.rawQuery(sql, null);
    if(cursor != null){
        // lấy được dữ liệu
        cursor.moveToFirst();
        // duyệt vòng lăp để lấy dữ liệu từng dòng cho vào list
        do {
            //thứ tự các cột: SELECT * FROM tb_cat câu lệnh sql sẽ lấy thứ tự từ trái sang phải
            //theo thư tự câu lệnh tạo bảng: CREATE TABLE tb_cat (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)
            // cột id trước, sau đó đến cột name ==> thứ tự: id là 0, name là 1

            int id = cursor.getInt( 0); // lấy thứ tự cột 0 là ID

            String name = cursor.getString(1); // lấy thứ tự cột 1 là name

            CatDTO catDTO = new CatDTO(); // tạo đối tượng dto để lưu dữ liệu

            catDTO.setId(id);
            catDTO.setName(name);

            list.add(catDTO); // thêm vào list

        }while (cursor.moveToNext());

        if(cursor != null){
            cursor.close();
        }

    }else {
        // không lấy được dữ liệu
        Log.d(TAG, "getAllCat: Không lấy được danh sách");
    }
    return list;
}
    public CatDTO getCatById(int id) {
        CatDTO catDTO = null;
        String [] params = {String.valueOf(id)}; // tạo mảng tham số cho truy vấn
        String sql = "SELECT * FROM tb_cat WHERE id = ?"; // dấu ? dùng để ghép dữ liêu tham số vào câu lệnh sql
        Cursor cursor = db.rawQuery(sql, params);


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            catDTO = new CatDTO();
            catDTO.setId(cursor.getInt(0));
            catDTO.setName(cursor.getString(1));
        }
        if (cursor != null) {
            cursor.close();
        }
        return catDTO;
    }
public boolean updateCat(CatDTO catDTO) {
    ContentValues values = new ContentValues();
    values.put("name", catDTO.getName());
    String[] params = {String.valueOf(catDTO.getId())};
    return db.update("tb_cat", values, "id = ?", params) > 0;

}
public boolean deleteCat(int id) {
    String[] params = {String.valueOf(id)};
    return db.delete("tb_cat", "id = ?", params) > 0;
}


}




















