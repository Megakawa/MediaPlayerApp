package com.example.zingmp3fake.ViewModel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import com.example.zingmp3fake.Model.Title;
import com.example.zingmp3fake.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private ArrayList<Title> listTitles;

    public RankingAdapter(ArrayList<Title> listTitles) {
        this.listTitles = listTitles;
    }

    @NonNull
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_title_square, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.ViewHolder holder, int position) {
        holder.name.setText(listTitles.get(position).getName());

        Picasso.get().load(listTitles.get(position).getImage()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navBuilder.setEnterAnim(R.anim.slide_in).setExitAnim(R.anim.fadeout).setPopEnterAnim(R.anim.fade_in).setPopExitAnim(R.anim.slide_out);
                Bundle bundle = new Bundle();
                bundle.putSerializable("linkStorage",listTitles.get(position).getLinkStorage());
                Navigation.findNavController(v).navigate(R.id.listMusicFragment,bundle,navBuilder.build());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTitles.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_name_title_square);
            imageView = view.findViewById(R.id.iv_image);
        }
    }

}
