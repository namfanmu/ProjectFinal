package com.example.projectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle, tvTimeSong, tvTimeTotal;
    SeekBar seekBar;
    ImageView image;
    ImageButton btnback, btnplay, btnstop,btnnext;
    ArrayList<Song> arrSong;
    int position=0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Xuly();
        AddSong();
        animation= AnimationUtils.loadAnimation(this,R.anim.cd_rotate);
        KhoiTaoMedia();

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position>arrSong.size()-1)
                {
                    position=0;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMedia();
                mediaPlayer.start();
                btnplay.setImageResource(R.drawable.pause);
                setTimeTotal();
                updatetime();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position<0)
                {
                    position=arrSong.size()-1;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMedia();
                mediaPlayer.start();
                btnplay.setImageResource(R.drawable.pause);
                setTimeTotal();
                updatetime();
            }
        });



        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnplay.setImageResource(R.drawable.play);
                KhoiTaoMedia();
            }
        });


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    btnplay.setImageResource(R.drawable.play);
                }
                else
                {
                    mediaPlayer.start();
                    btnplay.setImageResource(R.drawable.pause);
                }
                setTimeTotal();
                updatetime();
                image.startAnimation(animation);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });
    }
    private void updatetime()
    {
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdangtime=new SimpleDateFormat("mm:ss");
                tvTimeSong.setText(dinhdangtime.format(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(this,500);
                seekBar.setProgress((mediaPlayer.getCurrentPosition()));
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position>arrSong.size()-1)
                        {
                            position=0;
                        }
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                        }
                        KhoiTaoMedia();
                        mediaPlayer.start();
                        btnplay.setImageResource(R.drawable.pause);
                        setTimeTotal();
                        updatetime();
                    }
                });
            }
        },100);
    }
    private void setTimeTotal()
    {
        SimpleDateFormat dinhdangtime=new SimpleDateFormat("mm:ss");
        tvTimeTotal.setText(dinhdangtime.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void KhoiTaoMedia() {
        mediaPlayer= MediaPlayer.create(MainActivity.this,arrSong.get(position).getFile());
        tvTitle.setText(arrSong.get(position).getTitle());
    }

    private void AddSong() {
        arrSong=new ArrayList<>();
        arrSong.add(new Song("Take me to your heart",R.raw.takemetoyourheart));
        arrSong.add(new Song("My love",R.raw.mylove));
        arrSong.add(new Song("Thunder",R.raw.thunder));
        arrSong.add(new Song("Blank space",R.raw.blankspace));
        arrSong.add(new Song("Girl like you",R.raw.girllikeyou));
        arrSong.add(new Song("See you again",R.raw.seeyouagain));
        arrSong.add(new Song("This is what you came for ",R.raw.thisiswhatyoucamefor));
        arrSong.add(new Song("What makes you beautiful",R.raw.whatmakesyoubeautiful));

    }

    private void Xuly(){
        tvTitle=(TextView) findViewById(R.id.tvTitle);
        tvTimeSong=(TextView) findViewById(R.id.tvTimeSong);
        tvTimeTotal=(TextView) findViewById(R.id.tvTimeTotal);
        seekBar=(SeekBar) findViewById(R.id.seekBar);
        btnback=(ImageButton) findViewById(R.id.btnBack);
        btnplay=(ImageButton) findViewById(R.id.btnPlay);
        btnstop=(ImageButton) findViewById(R.id.btnStop);
        btnnext=(ImageButton) findViewById(R.id.btnNext);
        image=(ImageView) findViewById(R.id.imageViewCD);
    }
}
