package com.example.zingmp3fake.View;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.zingmp3fake.Model.Music;
import com.example.zingmp3fake.Model.Source;
import com.example.zingmp3fake.R;
import com.example.zingmp3fake.ViewModel.MusicAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ListMusicFragment extends Fragment {
    private RecyclerView rv;

    private ArrayList<Music> musics;
    private MusicAdapter musicAdapter;

    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = (String) getArguments().getSerializable("linkStorage");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rv_music);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        musics = new ArrayList<Music>();
        musicAdapter = new MusicAdapter(musics,getActivity());
        rv.setAdapter(musicAdapter);
        fetchAudioUrlFromFirebase();

    }
    private void fetchAudioUrlFromFirebase() {
        final FirebaseStorage storage = FirebaseStorage.getInstance("gs://music-media-23f37.appspot.com/");
        StorageReference storageRef = storage.getReference();

        final long ONE_MEGABYTE = 1024 * 1024 * 100;

        storageRef.child(url).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference fileRef :listResult.getItems()){
                    Source source = new Source();
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            source.setUrl(url);
                        }
                    });
                    fileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            try {
                                File tempMp3 = File.createTempFile("temp", ".mp3");
                                tempMp3.deleteOnExit();
                                FileOutputStream fos = new FileOutputStream(tempMp3);
                                fos.write(bytes);
                                fos.close();
                                FileInputStream fis = new FileInputStream(tempMp3);
                                source.setBytes(fis);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            musics.add(new Music(source));
                            musicAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

    }
}