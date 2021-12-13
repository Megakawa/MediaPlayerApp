package com.example.zingmp3fake.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zingmp3fake.Model.Music;
import com.example.zingmp3fake.Model.Title;
import com.example.zingmp3fake.R;
import com.example.zingmp3fake.ViewModel.PopularAdapter;
import com.example.zingmp3fake.ViewModel.RankingAdapter;
import com.example.zingmp3fake.ViewModel.SingerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mainFragment extends Fragment {
    private RecyclerView rvListPopular;
    private RecyclerView rvListSinger;
    private RecyclerView rvListRanking;

    private DatabaseReference databaseReference;

    private ArrayList<Title> ListSinger;
    private SingerAdapter singerAdapter;

    private ArrayList<Title> ListPopular;
    private PopularAdapter popularAdapter;

    private ArrayList<Title> ListRanking;
    private RankingAdapter rankingAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance("https://music-media-23f37-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        rvListPopular = view.findViewById(R.id.rv_list_item_popular);
        rvListPopular.setLayoutManager(layoutManager);
        ListPopular = new ArrayList<Title>();
        popularAdapter = new PopularAdapter(ListPopular);
        rvListPopular.setAdapter(popularAdapter);
        ListPopular.clear();
        getAllPopular();

        rvListSinger = view.findViewById(R.id.rv_list_item_singer);
        rvListSinger.setLayoutManager(layoutManager1);
        ListSinger = new ArrayList<Title>();
        singerAdapter = new SingerAdapter(ListSinger);
        rvListSinger.setAdapter(singerAdapter);
        ListSinger.clear();
        getAllSinger();

        rvListRanking = view.findViewById(R.id.rv_list_item_ranking);
        rvListRanking.setLayoutManager(layoutManager2);
        ListRanking = new ArrayList<Title>();
        rankingAdapter = new RankingAdapter(ListRanking);
        rvListRanking.setAdapter(rankingAdapter);
        ListRanking.clear();
        getAllRanking();

    }
    public void getAllSinger(){

        databaseReference.child("ListTitle").child("ListSinger").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListSinger.clear();
                for(DataSnapshot item: snapshot.getChildren()){
                    Title data = item.getValue(Title.class);
                    ListSinger.add(data);
                    singerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getAllPopular(){

        databaseReference.child("ListTitle").child("ListPopular").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListPopular.clear();
                for(DataSnapshot item: snapshot.getChildren()){
                    Title data = item.getValue(Title.class);
                    ListPopular.add(data);
                    popularAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getAllRanking(){

        databaseReference.child("ListTitle").child("ListRanking").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListRanking.clear();
                for(DataSnapshot item: snapshot.getChildren()){
                    Title data = item.getValue(Title.class);
                    ListRanking.add(data);
                    rankingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}