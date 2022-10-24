package com.example.alarm;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.alarm.widget.FileReaderView;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * PPT播放
 */
public class PPTPlayActivity extends AppCompatActivity {

    private FileReaderView documentReaderView;
    private WebView qqWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copy();
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
    private void copy() {
        // 开始复制
        String path = "file" + File.separator;
        copyAssetsFileToAppFiles(path + "test.docx", getFilePath(0));
        copyAssetsFileToAppFiles(path + "test.txt", getFilePath(1));
        copyAssetsFileToAppFiles(path + "test.xlsx", getFilePath(2));
        copyAssetsFileToAppFiles(path + "test.pptx", getFilePath(3));//QQ浏览器
        copyAssetsFileToAppFiles(path + "test.pdf", getFilePath(4));
    }


    /**
     * 从assets目录中复制某文件内容
     *
     * @param assetFileName assets目录下的文件
     * @param newFileName   复制到/data/data/package_name/files/目录下文件名
     */
    private void copyAssetsFileToAppFiles(String assetFileName, String newFileName) {
        Log.e("========",assetFileName + "  " + newFileName);
        InputStream is = null;
        FileOutputStream fos = null;
        int buffsize = 1024;

        try {

            is = this.getAssets().open(assetFileName);
            fos = new FileOutputStream(newFileName);
            //fos = this.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount = 0;
            byte[] buffer = new byte[buffsize];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getFilePath(int position) {
        String path = null;
        switch (position) {
            case 0:
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.docx";
                break;

            case 1:
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"test.txt";
                break;

            case 2:
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"test.xlsx";
                break;

            case 3:
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"test.pptx";
                break;

            case 4:
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"test.pdf";
                break;

            default:
                break;
        }
        return path;
    }
}

