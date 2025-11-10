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

import com.example.demosqlite.Adapter.ProductAdapter;
import com.example.demosqlite.DAO.ProductDAO;
import com.example.demosqlite.DTO.ProductDTO;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    RecyclerView rv_product;
    ProductAdapter productAdapter;
    ProductDAO productDAO;
    ArrayList<ProductDTO> listProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        rv_product = findViewById(R.id.rv_product);
        productDAO = new ProductDAO(this);
        listProduct = productDAO.getList();

        productAdapter = new ProductAdapter(this, listProduct);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_product.setLayoutManager(linearLayoutManager);
        rv_product.setAdapter(productAdapter);

        Button btnAdd = findViewById(R.id.btn_add_product);
        btnAdd.setOnClickListener(v -> {
            showAddProductDialog();
        });
    }

    private void showAddProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_product, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.et_product_name);
        EditText etPrice = view.findViewById(R.id.et_product_price);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = etName.getText().toString();
            double price = Double.parseDouble(etPrice.getText().toString());

            ProductDTO newProduct = new ProductDTO();
            newProduct.setName(name);
            newProduct.setPrice(price);
            newProduct.setId_cat(1); // Assuming a default category for now

            if (productDAO.addProduct(newProduct) > 0) {
                Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
                listProduct.clear();
                listProduct.addAll(productDAO.getList());
                productAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }
}
