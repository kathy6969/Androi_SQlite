package com.example.demosqlite.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite.DAO.CatDAO;
import com.example.demosqlite.DTO.CatDTO;
import com.example.demosqlite.R;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {
    Context context;
    ArrayList<CatDTO> list;
    CatDAO catDAO;

    public CatAdapter(Context context, ArrayList<CatDTO> list) {
        this.context = context;
        this.list = list;
        catDAO = new CatDAO(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CatDTO catDTO = list.get(position);

        // Hiển thị vị trí trong danh sách (bắt đầu từ 1) thay vì ID thật của DB.
        // Điều này tạo ra số thứ tự liền mạch trên giao diện mà không cần thay đổi ID trong DB.
        // ID thật từ catDTO.getId() vẫn được dùng cho các chức năng sửa/xóa.
        holder.tv_id.setText(String.valueOf(position + 1));
        holder.tv_name.setText(catDTO.getName());

        holder.img_edit.setOnClickListener(v -> showEditDialog(catDTO));
        holder.img_delete.setOnClickListener(v -> showDeleteDialog(catDTO));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_name;
        ImageView img_edit, img_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }

    private void showEditDialog(CatDTO catDTO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_category, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.et_category_name);
        etName.setText(catDTO.getName());

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = etName.getText().toString();
            catDTO.setName(name);

            if (catDAO.updateCat(catDTO)) {
                Toast.makeText(context, "Category updated successfully", Toast.LENGTH_SHORT).show();
                list.clear();
                list.addAll(catDAO.getList());
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Failed to update category", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void showDeleteDialog(CatDTO catDTO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Category");
        builder.setMessage("Are you sure you want to delete this category?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            if (catDAO.deleteCat(catDTO.getId())) {
                Toast.makeText(context, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                list.remove(catDTO);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Failed to delete category", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
