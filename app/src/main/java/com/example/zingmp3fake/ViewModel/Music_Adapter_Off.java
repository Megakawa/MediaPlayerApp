package com.example.zingmp3fake.ViewModel;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zingmp3fake.Model.Music_Off;
import com.example.zingmp3fake.R;

import java.util.ArrayList;

public class Music_Adapter_Off extends RecyclerView.Adapter<Music_Adapter_Off.ViewHolder>
{

    private ArrayList<Music_Off> musics;
    private Activity context;

    public Music_Adapter_Off(ArrayList<Music_Off> musics, Activity activity) {
        this.musics = musics;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music_off, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.tvMusicName.setText(musics.get(position).getName());

        holder.tvMusicName.setOnClickListener(new View.OnClickListener() {
            Music_Off music = musics.get(position);

            @Override
            public void onClick(View v) {
                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.fadeout).setExitAnim(R.anim.fadein).setPopEnterAnim(R.anim.fadeout).setPopExitAnim(R.anim.fadein);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Musics",musics);
                bundle.putSerializable("Position",position);
                Navigation.findNavController(v).navigate(R.id.playMusicOffFragment,bundle,navBuilder.build());
            }
        });

    }

    @Override
    public int getItemCount() {
        return musics.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivMusic;
        public TextView tvMusicName;
        public TextView tvAuthorName;

        public ViewHolder(View view) {
            super(view);
            ivMusic = view.findViewById(R.id.iv_music);
            tvMusicName = view.findViewById(R.id.tv_music_name);
            tvAuthorName = view.findViewById(R.id.tv_author_name);
        }
    }

}
