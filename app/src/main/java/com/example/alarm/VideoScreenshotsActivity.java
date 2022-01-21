package com.example.alarm;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.media.MediaMetadataRetriever.OPTION_PREVIOUS_SYNC;

/**
 * 本地视频截图
 */

public class VideoScreenshotsActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener {
    private MediaPlayer mp;
    private TextureView tv;
    public static String MY_VIDEO = "/storage/emulated/0/touch/program/PlayRes/0f0d2d2ef892c2c93f5bda533c1d9c49";
    public static String TAG = "TextureViewActivity";
    private VideoView videoView;
    private SeekBar seekbar;
    private ImageView headImage;
    private int totalTime;
    private boolean isTouch = false;
    private int currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoscreenshot);

        tv = (TextureView) findViewById(R.id.textureView1);
        tv.setSurfaceTextureListener(this);

        videoView = (VideoView) findViewById(R.id.vv_player);
        seekbar = (SeekBar) findViewById(R.id.sb_select);
        headImage = (ImageView) findViewById(R.id.iv_head);
        //initData();//无效
    }

    public void getBitmap(TextureView vv) {
        String mPath = Environment.getExternalStorageDirectory().toString()
                + "/Pictures/666666.png";
        Toast.makeText(getApplicationContext(), "Capturing Screenshot: " + mPath, Toast.LENGTH_SHORT).show();

        Bitmap bm = vv.getBitmap();
        headImage.setImageBitmap(bm);
        if (bm == null)
            Log.e(TAG, "bitmap is null");

        OutputStream fout = null;
        File imageFile = new File(mPath);

        try {
            fout = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fout);
            fout.flush();
            fout.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException");
            e.printStackTrace();
        }
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface s = new Surface(surface);

        try {
            mp = new MediaPlayer();
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.fav_cn);
            //String vidpath= "android.resource://" + getPackageName() + "/" + R.raw.fav_cn;
            mp.setDataSource(VideoScreenshotsActivity.this,uri);
            //mp.setDataSource(MY_VIDEO);
            mp.setSurface(s);
            mp.prepare();

            mp.setOnBufferingUpdateListener(this);
            mp.setOnCompletionListener(this);
            mp.setOnPreparedListener(this);
            mp.setOnVideoSizeChangedListener(this);

            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();

            Button b = (Button) findViewById(R.id.textureViewButton);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    VideoScreenshotsActivity.this.getBitmap(tv);
                }
            });
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

  
    protected void initData() {
        //String mp4Path = Environment.getExternalStorageDirectory().getPath() + "/qwwz.mp4";
        final MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(MY_VIDEO);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                totalTime = videoView.getDuration();//毫秒
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(isTouch){
                    currentTime = (int)(((float) progress / 100) * totalTime);
                    videoView.seekTo(currentTime);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouch = true;
                Log.e("========","onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("========","onStopTrackingTouch");
                isTouch = false;
                //获取第一帧图像的bitmap对象 单位是微秒
                Bitmap bitmap = mmr.getFrameAtTime((long) (currentTime * 1000), OPTION_PREVIOUS_SYNC);
                headImage.setImageBitmap(bitmap);
            }
        });
        videoView.setVideoPath(MY_VIDEO);
        videoView.start();
    }
}

