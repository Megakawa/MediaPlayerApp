package com.example.zingmp3fake.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zingmp3fake.Model.Music_Off;
import com.example.zingmp3fake.R;
import com.example.zingmp3fake.ViewModel.Music_Adapter_Off;

import java.io.File;
import java.util.ArrayList;

public class ListMusicOffFragment extends Fragment
    {
        private RecyclerView rv;
        private ArrayList<Music_Off> musics;
        private Music_Adapter_Off music_adapter_off;
        String str = "";

        @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_music_off, container, false);
    }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            rv = view.findViewById(R.id.rv_music_off);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            musics = new ArrayList<Music_Off>();
            music_adapter_off = new Music_Adapter_Off(musics,getActivity());
            rv.setAdapter(music_adapter_off);


//            File root = new File("/storage/0403-0201");
            File root = Environment.getExternalStorageDirectory();
            String path = root.getAbsolutePath();
            File[] list = root.listFiles();
            Log.d("DEBUG1",path);
            for(File f : list )
            {
                str = f.getName();
                Log.d("DEBUG1",str);


                if(str.equals("Music"))
                {
                    File[] sublist = f.listFiles();
                    for(File f1 : sublist)
                    {
                        str = f1.getName();
                        File temp = new File(path+"/Music/"+ str +"/" + str +".mp3");
                        if(temp.exists())
                        {
                            Music_Off m = new Music_Off(path+"/Music/"+ str +"/" + str +".mp3",str);
                            musics.add(m);
                            music_adapter_off.notifyDataSetChanged();
                        }
                    }
                    break;
                }
            }
        }

    }