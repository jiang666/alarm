package com.example.alarm;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.alarm.widget.FileReaderView;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

import java.io.File;

/**
 * PPT播放
 */
public class PPTPlayActivity extends AppCompatActivity {

    private FileReaderView documentReaderView;
    private WebView qqWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("========","QbSdk是否加载成功 "+ QbSdk.isTbsCoreInited());;
        setContentView(R.layout.activity_ppt);
        documentReaderView = (FileReaderView)findViewById(R.id.documentReaderView);
        documentReaderView.show(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"test.pptx");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (documentReaderView != null) {
            documentReaderView.stop();
        }
    }
}

