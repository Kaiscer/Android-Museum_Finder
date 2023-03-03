package com.example.proyectob_pmdm_t2_kaiscervasquez.recycleutil;

import static android.app.PendingIntent.getActivity;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectob_pmdm_t2_kaiscervasquez.R;
import com.example.proyectob_pmdm_t2_kaiscervasquez.retrofitutils.Graph;

import java.util.ArrayList;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MuseumVH>
        implements View.OnClickListener {

    private ArrayList<Graph> listMuseum;
    private View.OnClickListener listener;



    public ListAdapter(ArrayList<Graph> listMuseum, View.OnClickListener listener) {
        this.listMuseum = listMuseum;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MuseumVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_museum,parent, false);
        view.setOnClickListener(this);
        return new MuseumVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumVH holder, int position) {
        holder.bindMuseum(listMuseum.get(position));
    }

    @Override
    public int getItemCount() {
        return listMuseum.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public static class MuseumVH extends RecyclerView.ViewHolder{
        TextView tvNameMuseum;
        public MuseumVH(@NonNull View itemView) {
            super(itemView);
            tvNameMuseum = itemView.findViewById(R.id.tv_nameMuseum);

        }
        public void bindMuseum(Graph museum){
            tvNameMuseum.setText(museum.getTitle());
        }
    }
}
