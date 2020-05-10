package com.alfred0ga.top;

import android.view.View;

public interface OnItemClickListener {
    //void onItemClick(Artista artista);
    //void onItemClick(Artista artista, View view);
    //void onItemClick(Artista artista, View imgPhoto, View tvNote);
    //void onItemClick(Artista artista, View imgPhoto, View tvNote, View tvOrder, View tvName);
    void onItemClick(Artista artista, View imgPhoto, View tvName);
    void onLongItemClick(Artista artista);
}
