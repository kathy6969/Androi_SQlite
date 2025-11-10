package com.example.demosqlite;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite.Adapter.CatAdapter;
import com.example.demosqlite.DAO.CatDAO;
import com.example.demosqlite.DTO.CatDTO;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView rv_cat;
    CatAdapter catAdapter;
    CatDAO catDAO;
    ArrayList<CatDTO> listCat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        rv_cat = findViewById(R.id.rv_cat);
        catDAO = new CatDAO(this);
        listCat = catDAO.getList();

        catAdapter = new CatAdapter(this, listCat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_cat.setLayoutManager(linearLayoutManager);
        rv_cat.setAdapter(catAdapter);

        Button btnAdd = findViewById(R.id.btn_add_cat);
        btnAdd.setOnClickListener(v -> showAddCategoryDialog());
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_category, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.et_category_name);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = etName.getText().toString();

            CatDTO newCat = new CatDTO();
            newCat.setName(name);

            if (catDAO.addCat(newCat) > 0) {
                Toast.makeText(this, "Category added successfully", Toast.LENGTH_SHORT).show();
                listCat.clear();
                listCat.addAll(catDAO.getList());
                catAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to add category", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
