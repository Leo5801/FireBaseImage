package com.example.firebaseimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MalumotAdapter extends RecyclerView.Adapter<MalumotAdapter.MalumotViewHolder> {

    Context context;
    ArrayList<Malumot> malumotArrayList;

    public MalumotAdapter(Context context, ArrayList<Malumot> malumotArrayList) {
        this.context = context;
        this.malumotArrayList = malumotArrayList;
    }

    @NonNull
    @Override
    public MalumotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.malumot_layout,parent,false);
        return new MalumotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MalumotViewHolder holder, int position) {
        holder.textView.setText(malumotArrayList.get(position).getImagename());
        Picasso.get().load(malumotArrayList.get(position).getImagelink()).centerCrop().fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return malumotArrayList.size();
    }

    class MalumotViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MalumotViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview2);
            textView=itemView.findViewById(R.id.textview2);
        }
    }
}
