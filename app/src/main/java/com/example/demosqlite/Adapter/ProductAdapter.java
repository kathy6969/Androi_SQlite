package com.example.demosqlite.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite.DAO.ProductDAO;
import com.example.demosqlite.DTO.ProductDTO;
import com.example.demosqlite.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductDTO> list;
    ProductDAO productDAO;

    public ProductAdapter(Context context, ArrayList<ProductDTO> list) {
        this.context = context;
        this.list = list;
        productDAO = new ProductDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDTO productDTO = list.get(position);
        holder.tv_id.setText(String.valueOf(productDTO.getId()));
        holder.tv_name.setText(productDTO.getName());
        holder.tv_price.setText(String.valueOf(productDTO.getPrice()));

        holder.img_edit.setOnClickListener(v -> {
            showEditDialog(productDTO);
        });

        holder.img_delete.setOnClickListener(v -> {
            showDeleteDialog(productDTO);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_name, tv_price;
        ImageView img_edit, img_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id_product);
            tv_name = itemView.findViewById(R.id.tv_name_product);
            tv_price = itemView.findViewById(R.id.tv_price_product);
            img_edit = itemView.findViewById(R.id.img_edit_product);
            img_delete = itemView.findViewById(R.id.img_delete_product);
        }
    }

    private void showEditDialog(ProductDTO productDTO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_product, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.et_product_name);
        EditText etPrice = view.findViewById(R.id.et_product_price);

        etName.setText(productDTO.getName());
        etPrice.setText(String.valueOf(productDTO.getPrice()));

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = etName.getText().toString();
            double price = Double.parseDouble(etPrice.getText().toString());

            productDTO.setName(name);
            productDTO.setPrice(price);

            if (productDAO.updateProduct(productDTO) > 0) {
                Toast.makeText(context, "Product updated successfully", Toast.LENGTH_SHORT).show();
                list.clear();
                list.addAll(productDAO.getList());
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Failed to update product", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }

    private void showDeleteDialog(ProductDTO productDTO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Product");
        builder.setMessage("Are you sure you want to delete this product?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            if (productDAO.deleteProduct(productDTO.getId()) > 0) {
                Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                list.remove(productDTO);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Failed to delete product", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }
}
