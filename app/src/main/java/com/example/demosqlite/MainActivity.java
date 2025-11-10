package com.example.demosqlite;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite.Adapter.CatAdapter;
import com.example.demosqlite.DAO.CatDAO;
import com.example.demosqlite.DTO.CatDTO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
CatDAO catDAO;
CatDTO catDTO;
    static String TAG = "zzzzzzzzzzzzzz";

    RecyclerView rc_cat;
    ArrayList<CatDTO> list;
    CatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        rc_cat = findViewById(R.id.rc_cat);
        ;
        catDAO = new CatDAO(this);
        catDTO = new CatDTO();


//        catDAO = new CatDAO(this);
//        catDTO = new CatDTO();
//        catDTO.setName("Máy Tính");
//        int kq =catDAO.addCat(catDTO);
//        Log.d("kq",kq+"");
//        if(kq >0 ){
//            Log.d(TAG, "onCreate: Thành công");
//        }else {
//            Log.d(TAG, "onCreate: Thất bại");
//
//
//        }
//    }

        list =catDAO.getAllCat();
        adapter = new CatAdapter(this,list);
        rc_cat.setAdapter(adapter);




    }
}