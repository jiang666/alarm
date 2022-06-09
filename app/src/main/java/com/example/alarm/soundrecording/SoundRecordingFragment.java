package com.example.alarm.soundrecording;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cokus.wavelibrary.draw.WaveCanvas;
import com.cokus.wavelibrary.utils.SamplePlayer;
import com.cokus.wavelibrary.utils.SoundFile;
import com.cokus.wavelibrary.view.WaveSurfaceView;
import com.cokus.wavelibrary.view.WaveformView;
import com.example.alarm.R;
import com.example.alarm.adapter.RecordingAdapter;
import com.example.alarm.base.BaseFragment;
import com.example.alarm.bean.SingleTestBean;
import com.example.alarm.bean.UsbBean;
import com.example.alarm.constant.TestConstant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 固件测试程序 录音测试
 */

public class SoundRecordingFragment extends BaseFragment<SoundrecordingModel, SoundrecordingPresenter> implements SoundrecordingContract.View, View.OnClickListener, RecordingAdapter.OnCommonDialogAdapterListener {
    private static final String TAG = SoundRecordingFragment.class.getSimpleName();
    private Button bt_restart;
    private UsbBean mUsbBean;
    private TextView state;
    private SingleTestBean singleTest;
    private RecyclerView recyclerView;
    private HashMap<String, String> mData;
    private RecordingAdapter mAdapter;
    private Button bt_recordingprevious;
    private Button bt_recordingnext;
    private boolean isMove = false;

    private static final int FREQUENCY = 16000;// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    private static final int CHANNELCONGIFIGURATION = AudioFormat.CHANNEL_IN_MONO;// 设置单声道声道
    private static final int AUDIOENCODING = AudioFormat.ENCODING_PCM_16BIT;// 音频数据格式：每个样本16位
    public final static int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;// 音频获取源
    private int recBufSize;// 录音最小buffer大小
    private AudioRecord audioRecord;
    private WaveCanvas waveCanvas;
    private String mFileName = "test";//文件名
    private WaveSurfaceView wavesfv;
    File mFile;
    Thread mLoadSoundFileThread;
    SoundFile mSoundFile;
    boolean mLoadingKeepGoing;
    SamplePlayer mPlayer;
    private WaveformView waveview;
    private int mPlayStartMsec;
    private int mPlayEndMsec;
    private final int UPDATE_WAV = 100;

    @Override
    protected int getLayoutId() {
        return R.layout.sound_recording_fragmet;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        bt_recordingnext = (Button) layout.findViewById(R.id.bt_recordingnext);
        bt_recordingprevious = (Button) layout.findViewById(R.id.bt_recordingprevious);
        bt_restart = (Button) layout.findViewById(R.id.bt_restart);
        wavesfv = (WaveSurfaceView) layout.findViewById(R.id.wavesfv);
        waveview = (WaveformView) layout.findViewById(R.id.waveview);
        state = (TextView) layout.findViewById(R.id.state);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bt_recordingnext.setOnClickListener(this);
        bt_recordingprevious.setOnClickListener(this);
        bt_restart.setOnClickListener(this);
        bt_restart.setEnabled(false);
        bt_recordingnext.setEnabled(false);
        bt_recordingprevious.setEnabled(false);
        //mUsbBean = ((NewWelcomeActivity) getActivity()).getUsbBean();//取缓存;
        showUSBData(mUsbBean);
        startRecording();
    }

    private void startRecording() {
        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            public final void accept(Long code) {
                state.setText("正在录音");
                initAudio();
                startAudio();
            }
        });
        Observable.timer(6, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            public final void accept(Long code) {
                state.setText("正在解码");
                waveCanvas.Stop();
                waveCanvas = null;
                initWaveView();
            }
        });
    }

    private void initWaveView() {
        loadFromFile();
    }

    /**
     * 载入wav文件显示波形
     */
    private void loadFromFile() {
        try {
            Thread.sleep(300);//让文件写入完成后再载入波形 适当的休眠下
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mFile = new File(U.DATA_DIRECTORY + mFileName + ".wav");
        mLoadingKeepGoing = true;
        // Load the sound file in a background thread
        mLoadSoundFileThread = new Thread() {
            public void run() {
                try {
                    mSoundFile = SoundFile.create(mFile.getAbsolutePath(), null);
                    if (mSoundFile == null) {
                        return;
                    }
                    mPlayer = new SamplePlayer(mSoundFile);
                } catch (final Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (mLoadingKeepGoing) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            finishOpeningSoundFile();
                            wavesfv.setVisibility(View.INVISIBLE);
                            waveview.setVisibility(View.VISIBLE);
                            Observable.timer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                                public final void accept(Long code) {
                                    if (isMove) return;
                                    state.setText("正在播放录音");
                                    bt_recordingnext.setEnabled(true);
                                    bt_recordingprevious.setEnabled(true);
                                    onPlay(0);//播放 从头开始播放
                                }
                            });

                        }
                    };
                    getActivity().runOnUiThread(runnable);
                }
            }
        };
        mLoadSoundFileThread.start();
    }

    float mDensity;

    /**
     * waveview载入波形完成
     */
    private void finishOpeningSoundFile() {
        waveview.setSoundFile(mSoundFile);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;
        waveview.recomputeHeights(mDensity);
    }

    /**
     * 开始录音
     */
    private void startAudio() {
        waveCanvas = new WaveCanvas();
        waveCanvas.baseLine = wavesfv.getHeight() / 2;
        waveCanvas.Start(audioRecord, recBufSize, wavesfv, mFileName, U.DATA_DIRECTORY, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return true;
            }
        });
    }

    /**
     * 初始化录音  申请录音权限
     */
    public void initAudio() {
        recBufSize = AudioRecord.getMinBufferSize(FREQUENCY,
                CHANNELCONGIFIGURATION, AUDIOENCODING);// 录音组件
        audioRecord = new AudioRecord(AUDIO_SOURCE,// 指定音频来源，这里为麦克风
                FREQUENCY, // 16000HZ采样频率
                CHANNELCONGIFIGURATION,// 录制通道
                AUDIO_SOURCE,// 录制编码格式
                recBufSize);// 录制缓冲区大小 //先修改
        U.createDirectory();
    }

    /**
     * 播放音频，@param startPosition 开始播放的时间
     */
    private synchronized void onPlay(int startPosition) {
        if (mPlayer == null)
            return;
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            updateTime.removeMessages(UPDATE_WAV);
        }
        mPlayStartMsec = waveview.pixelsToMillisecs(startPosition);
        mPlayEndMsec = waveview.pixelsToMillisecsTotal();
        mPlayer.setOnCompletionListener(new SamplePlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                Log.e("--------", "播放完成");
                state.setText("录音播放完毕");
                bt_restart.setEnabled(true);
                waveview.setPlayback(-1);
                updateDisplay();
                updateTime.removeMessages(UPDATE_WAV);
                //FileUtil.deleteFile(new File(myRecAudioFile.getAbsolutePath()));
            }
        });
        mPlayer.seekTo(mPlayStartMsec);
        mPlayer.start();
        Observable.timer(6, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            public final void accept(Long code) {
                state.setText("录音播放完毕");
                bt_restart.setEnabled(true);
            }
        });
        Message msg = new Message();
        msg.what = UPDATE_WAV;
        updateTime.sendMessage(msg);
    }

    Handler updateTime = new Handler() {
        public void handleMessage(Message msg) {
            updateDisplay();
            updateTime.sendMessageDelayed(new Message(), 10);
        }

        ;
    };

    /**
     * 更新updateview 中的播放进度
     */
    private void updateDisplay() {
        int now = mPlayer.getCurrentPosition();// nullpointer
        int frames = waveview.millisecsToPixels(now);
        waveview.setPlayback(frames);//通过这个更新当前播放的位置
        if (now >= mPlayEndMsec) {
            waveview.setPlayFinish(1);
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.pause();
                updateTime.removeMessages(UPDATE_WAV);
            }
        } else {
            waveview.setPlayFinish(0);
        }
        waveview.invalidate();//刷新真个视图
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_recordingnext:
                if (singleTest.getParam().containsValue("U")) {
                    takeToast("请完善测试信息");
                } else {
                    if (waveCanvas != null) {
                        waveCanvas.Stop();
                        waveCanvas = null;
                    }
                    if (mPlayer != null) mPlayer.stop();
                    if (singleTest.getParam().containsValue("N")) {
                        mUsbBean.getUsb().get(mUsbBean.getIndex()).setFinish(TestConstant.FAIL);
                    } else {
                        mUsbBean.getUsb().get(mUsbBean.getIndex()).setFinish(TestConstant.FINISH);
                    }
                    mNext.nextTesting(mUsbBean);
                }
                break;
            case R.id.bt_recordingprevious:
                if (singleTest.getParam().containsValue("U")) {
                    takeToast("请完善测试信息");
                } else {
                    if (waveCanvas != null) {
                        waveCanvas.Stop();
                        waveCanvas = null;
                    }
                    if (mPlayer != null) mPlayer.stop();
                    if (singleTest.getParam().containsValue("N")) {
                        mUsbBean.getUsb().get(mUsbBean.getIndex()).setFinish(TestConstant.FAIL);
                    } else {
                        mUsbBean.getUsb().get(mUsbBean.getIndex()).setFinish(TestConstant.FINISH);
                    }
                    mNext.lastTesting(mUsbBean);
                }
                break;
            case R.id.bt_restart:
                wavesfv.setVisibility(View.VISIBLE);
                waveview.setVisibility(View.INVISIBLE);
                startRecording();
                bt_restart.setEnabled(false);
                break;
        }
    }

    @Override
    public void onStartLoad() {

    }

    @Override
    public void onStopLoad() {

    }

    @Override
    public void onError(String errorInfo) {

    }

    @Override
    public void showUSBData(UsbBean usbBean) {
        mUsbBean = usbBean;
        int mIndex = mUsbBean.getIndex();
        singleTest = mUsbBean.getUsb().get(mIndex);
        Map<String, String> param = singleTest.getParam();
        mData = new HashMap<>();
        for (Map.Entry<String, String> item : param.entrySet()) {
            if (item.getValue().equals("U") || item.getValue().equals("Y") || item.getValue().equals("N")) {
                mData.put(item.getKey(), item.getValue());
            }
        }
        mAdapter = new RecordingAdapter(mData, getContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnCommonDialogAdapterListener(this);
    }

    private void takeToast(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void paramCancel(String name) {
        singleTest.getParam().put(name, "N");
        mData.put(name, "N");
        mAdapter.notifyDataSetChanged();
        if (!singleTest.getParam().containsValue("U")) {
            if (singleTest.getParam().containsValue("N")) {
                mUsbBean.getUsb().get(mUsbBean.getIndex()).setFinish(TestConstant.FAIL);
            } else {
                mUsbBean.getUsb().get(mUsbBean.getIndex()).setFinish(TestConstant.FINISH);
            }
        }
    }

    @Override
    public void paramSure(String name) {
        singleTest.getParam().put(name, "Y");
        mData.put(name, "Y");
        mAdapter.notifyDataSetChanged();
        if (!singleTest.getParam().containsValue("U")) {
            if (singleTest.getParam().containsValue("N")) {
                mUsbBean.getUsb().get(mUsbBean.getIndex()).setFinish(TestConstant.FAIL);
            } else {
                mUsbBean.getUsb().get(mUsbBean.getIndex()).setFinish(TestConstant.FINISH);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isMove = true;
        if (waveCanvas != null) {
            waveCanvas.Stop();
            waveCanvas = null;
        }
        if (mPlayer != null) mPlayer.stop();

    }
}
