package com.example.demosqlite.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.demosqlite.DTO.CatDTO;
import com.example.demosqlite.R;


import java.util.ArrayList;


public class CatAdapter
        extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {
    Context context;
    ArrayList<CatDTO> list;
    public CatAdapter(Context context, ArrayList<CatDTO> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //lấy thông tin của 1 dòng trong list
        CatDTO catDTO = list.get(position);
        // vắn vào holder
        holder.tv_id.setText(String.valueOf(catDTO.getId()));
        holder.tv_name.setText(catDTO.getName());
        // nút sửa, nút xoá
        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code xử lý khi click vào nút sửa
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code xử lý khi click vào nút xoá
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_name;
        ImageView img_edit, img_delete; // khai báo biến để ánh xạ view 1 dòng
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }
}
