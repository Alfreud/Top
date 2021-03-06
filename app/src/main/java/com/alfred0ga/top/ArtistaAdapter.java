package com.alfred0ga.top;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ViewHolder> {
    private List<Artista> artistas;
    private Context context;
    private OnItemClickListener listener;

    public ArtistaAdapter(List<Artista> artistas, OnItemClickListener listener) {
        this.artistas = artistas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent,
                false);
        this.context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Artista artista = artistas.get(position);

        //holder.setListener(artista, listener);
        //holder.setListener(artista, holder.imgPhoto,listener);
        //holder.setListener(artista, holder.imgPhoto, holder.tvNotes, holder.tvOrden, holder.tvNombre, listener);
        holder.setListener(artista, holder.imgPhoto, holder.tvNombre, listener);

        holder.tvNombre.setText(artista.getNombreCompleto());
        holder.tvNotes.setText(artista.getNotas());
        holder.tvOrden.setText(String.valueOf(artista.getOrden()));

        if(artista.getFotoUrl() != null){
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.centerCrop();
            options.placeholder(R.drawable.ic_sentiment_satisfied);

            Glide.with(context)
                    .load(artista.getFotoUrl())
                    .apply(options)
                    .into(holder.imgPhoto);
        }else{
            holder.imgPhoto.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_account_box));
        }
    }

    @Override
    public int getItemCount() {
        return this.artistas.size();
    }

    public void add(Artista artista){
        if(!artistas.contains(artista)){
            artistas.add(artista);
            notifyDataSetChanged();
        }
    }

    public void setList(List<Artista> artistasFromDB) {
        this.artistas = artistasFromDB;
        notifyDataSetChanged();
    }

    public void remove(Artista artista) {
        if (artistas.contains(artista)){
            artistas.remove(artista);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPhoto)
        CircleImageView imgPhoto;
        @BindView(R.id.tvNombre)
        AppCompatTextView tvNombre;
        @BindView(R.id.tvNotes)
        AppCompatTextView tvNotes;
        @BindView(R.id.tvOrden)
        AppCompatTextView tvOrden;
        @BindView(R.id.containerMain)
        ConstraintLayout containerMain;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setListener(final Artista artista, View imgPhoto,View tvName,
                         final OnItemClickListener listener){
            containerMain.setOnClickListener(v -> //listener.onItemClick(artista));
                    listener.onItemClick(artista, imgPhoto, tvName));

            containerMain.setOnLongClickListener(v -> {
                listener.onLongItemClick(artista);
                return true;
            });
        }
    }
}
