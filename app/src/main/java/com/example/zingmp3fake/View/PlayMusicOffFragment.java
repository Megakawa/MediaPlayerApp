package com.example.zingmp3fake.View;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zingmp3fake.Model.Music_Off;
import com.example.zingmp3fake.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayMusicOffFragment extends Fragment
{
    private CircularImageView circularImageView;
    private TextView tvMusicName;
    private TextView tvAuthor;
    private TextView tvNameSong;
    private ImageView ivLike;
    private TextView tvMusicTime;
    private TextView tvMusicTimeTotal;
    private SeekBar sbMusic;
    private ImageButton ibStart;
    private ImageButton ibRepeat;
    private ImageButton ibNext;
    private ImageButton ibPre;
    private ImageButton ibBack;
    private ImageButton ibShuffle;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;
    private MenuItem miBack;
    private MenuItem miText;
    private ArrayList<Music_Off> musics;
    private Integer position;
    private final boolean[] clicked = {false};
    private final boolean[] clicked_shuffle = {false};
    private final boolean[] clicked_loop = {false};

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            musics = (ArrayList<Music_Off>) getArguments().getSerializable("Musics");
            position = (Integer) getArguments().getSerializable("Position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_play_music_off, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        circularImageView = view.findViewById(R.id.cv_disk);
        tvMusicName = view.findViewById(R.id.tv_music_name);
        tvAuthor = view.findViewById(R.id.tv_author_name);
        ivLike = view.findViewById(R.id.iv_like);
        tvMusicTime = view.findViewById(R.id.tv_time_song);
        tvNameSong = view.findViewById(R.id.tv_name_song);
        tvMusicTimeTotal = view.findViewById(R.id.tv_time_music_total);
        sbMusic = view.findViewById(R.id.sb_music);
        ibStart = view.findViewById(R.id.ib_start);
        ibRepeat = view.findViewById(R.id.ib_loop);
        ibNext = view.findViewById(R.id.ib_next);
        ibPre = view.findViewById(R.id.ib_pre);
        ibBack = view.findViewById(R.id.ib_back);
        ibShuffle = view.findViewById(R.id.ib_shuffle);
        mediaPlayer = new MediaPlayer();

        initMusic(position);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        handler = new Handler();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                sbMusic.setMax(mediaPlayer.getDuration());
                NumberFormat numberFormat = new DecimalFormat("00");
                int timeSec = mediaPlayer.getDuration()/1000;
                int minutes = (timeSec / 60);
                timeSec = timeSec - minutes * 60;
                int secs = timeSec;
                tvMusicTimeTotal.setText(numberFormat.format(minutes)+":"+numberFormat.format(secs));

                mp.start();

                ibStart.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                if(clicked[0])
                {
                    ivLike.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                if(clicked_shuffle[0])
                {
                    ibShuffle.setImageResource(R.drawable.ic_baseline_shuffle_24_clicked);
                }
                if(clicked_loop[0])
                {
                    ibRepeat.setImageResource(R.drawable.ic_baseline_repeat_24_check);
                }

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {

                        if (clicked_shuffle[0])
                        {
                            final int random = new Random().nextInt(musics.size());
                            initMusic(random);
                        }
                        else
                        {
                            if(clicked_loop[0])
                            {
                                initMusic(position);
                            }
                            else
                            {
                                if(position < musics.size() - 1){
                                    position++;
                                }
                                else {
                                    position = 0;
                                }
                                initMusic(position);
                            }
                        }
                    }
                });
                playCircle();
            }
        });

        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked[0]){
                    clicked[0] =false;
                    ivLike.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
                else{
                    clicked[0] =true;
                    ivLike.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
            }
        });
        ibShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked_shuffle[0]){
                    clicked_shuffle[0] =false;
                    ibShuffle.setImageResource(R.drawable.ic_baseline_shuffle_24);
                }
                else{
                    clicked_shuffle[0] =true;
                    ibShuffle.setImageResource(R.drawable.ic_baseline_shuffle_24_clicked);
                    clicked_loop[0] = false;
                    ibRepeat.setImageResource(R.drawable.ic_baseline_repeat_24);
                }
            }
        });
        ibRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isLooping()){
                    clicked_loop[0] = false;
                    ibRepeat.setImageResource(R.drawable.ic_baseline_repeat_24);
                }
                else{
                    clicked_loop[0] = true;
                    ibRepeat.setImageResource(R.drawable.ic_baseline_repeat_24_check);
                    clicked_shuffle[0] = false;
                    ibShuffle.setImageResource(R.drawable.ic_baseline_shuffle_24);
                }
            }
        });
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked_shuffle[0])
                {
                    final int random = new Random().nextInt(musics.size());
                    initMusic(random);
                }
                else
                {
                    if(position < musics.size() - 1)
                    {
                        position++;
                    }
                    else
                    {
                        position = 0;
                    }
                    initMusic(position);
                }
            }
        });
        ibPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked_shuffle[0])
                {
                    final int random = new Random().nextInt(musics.size());
                    initMusic(random);
                }
                else
                {
                    if(position == 0){
                        position = musics.size() - 1;
                    }
                    else{
                        position--;
                    }
                    initMusic(position);
                }
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ibStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playCircle();
                    ibStart.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }
                else{
                    mediaPlayer.start();
                    playCircle();
                    ibStart.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                }
            }
        });

    }

    public void playCircle(){
        sbMusic.setProgress(mediaPlayer.getCurrentPosition());

        if (!mediaPlayer.isPlaying()){
            ibStart.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        NumberFormat numberFormat = new DecimalFormat("00");
        int timeSec = mediaPlayer.getCurrentPosition()/1000;
        int minutes = (timeSec / 60);
        timeSec = timeSec - minutes * 60;
        int secs = timeSec;
        tvMusicTime.setText(numberFormat.format(minutes)+":"+numberFormat.format(secs));

        if(mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCircle();
                }
            };
            handler.postDelayed(runnable,100);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }

    public void initMusic(int position){
        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musics.get(position).getPath());
            mediaPlayer.prepareAsync();
            circularImageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}