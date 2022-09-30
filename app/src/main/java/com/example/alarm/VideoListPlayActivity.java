package com.example.alarm;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.alarm.adapter.VideoAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.media.MediaMetadataRetriever.OPTION_PREVIOUS_SYNC;

/**
 * 本地视频截图
 */

public class VideoListPlayActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener,
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
    private LinearLayout ll_conyent;
    private TextView tvUpdataThread;
    private Surface s;
    private RecyclerView rvTest;
    List<String> list = new ArrayList<>();
    private VideoAdapter testAdapter;
    private int spanCount =6;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoscreenshot);
        ll_conyent =  (LinearLayout)findViewById(R.id.ll_conyent);
        tv = (TextureView) findViewById(R.id.textureView1);
        tv.setSurfaceTextureListener(this);
        tvUpdataThread = (TextView) findViewById(R.id.tv_updata_thread);
        rvTest = (RecyclerView) findViewById(R.id.recyclerView);
        getVideo(VideoListPlayActivity.this);
        testAdapter = new VideoAdapter(this, list);
        testAdapter.setRowSize(spanCount);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL);//瀑布流
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);//线性布局
        layoutManager = new GridLayoutManager(this,spanCount);//网格布局管理器
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvTest.setLayoutManager(layoutManager);
        rvTest.setAdapter(testAdapter);
        testAdapter.setOnItemClickListener(new VideoAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*if(mp != null){
                    mp.stop();
                    mp.release();
                }*/
                //点击条目变颜色
                testAdapter.setOnItem(position);
                try {
                    if(mp==null)
                    mp = new MediaPlayer();
                    //Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.fav_cn);
                    //String vidpath= "android.resource://" + getPackageName() + "/" + R.raw.fav_cn;
                    //"/storage/emulated/0/touch/program/PlayRes/8c3c3a9e88d7efedf1438e679f2dfafa";
                    //"/storage/emulated/0/touch/program/PlayRes/78c61272ae5c629076915c86d1ed9517";
                    //Uri uri = Uri.parse("http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear2/prog_index.m3u8");
                    //Uri uri = Uri.parse("rtmp://test.jyd.com.cn:1935/live/9525ec88edef9daa.stream");
                    Uri uri = Uri.parse(list.get(position));
                    mp.setDataSource(VideoListPlayActivity.this,uri);
                    //mp.setDataSource(MY_VIDEO);
                    mp.setSurface(s);
                    mp.prepare();

                    mp.setOnBufferingUpdateListener(VideoListPlayActivity.this);
                    mp.setOnCompletionListener(VideoListPlayActivity.this);
                    mp.setOnPreparedListener(VideoListPlayActivity.this);
                    mp.setOnVideoSizeChangedListener(VideoListPlayActivity.this);

                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mp.start();
                    mp.setLooping(true);
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
                testAdapter.notifyDataSetChanged();
            }
        });

        videoView = (VideoView) findViewById(R.id.vv_player);
        seekbar = (SeekBar) findViewById(R.id.sb_select);
        headImage = (ImageView) findViewById(R.id.iv_head);
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
         s = new Surface(surface);


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

    public void getVideo(Context context) {
        Log.e("=========","path = 00000");
        list.clear();
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();

        String[] projVideo = {MediaStore.Video.Thumbnails._ID
                , MediaStore.Video.Thumbnails.DATA
                , MediaStore.Video.Media.DURATION
                , MediaStore.Video.Media.SIZE
                , MediaStore.Video.Media.DISPLAY_NAME
                , MediaStore.Video.Media.DATE_MODIFIED};

        Cursor mCursor = mContentResolver.query(videoUri, projVideo,
                MediaStore.Video.Media.MIME_TYPE + " in(?, ?, ?, ?)",
                new String[]{"video/mp4", "video/3gp", "video/avi", "video/rmvb"},
                MediaStore.Video.Media.DATE_MODIFIED + " desc");

        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                // 获取视频的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                list.add(path);
                // 获取该视频的父路径名
                //String dirPath = new File(path).getParentFile().getAbsolutePath();

            }
            mCursor.close();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp!= null)
        mp.stop();
    }
}

