package com.example.alarm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.alarm.widget.FileReaderView;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * PPT播放
 */
public class PPTPlayActivity extends AppCompatActivity {

    private FileReaderView documentReaderView;
    private WebView qqWebView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copy();
        Log.e("========","QbSdk是否加载成功 "+ QbSdk.isTbsCoreInited());;
        setContentView(R.layout.activity_ppt);
        webView = (WebView) findViewById(R.id.webView);
        initViews();
        //webView.loadUrl("http://cloud.bunnysky.net:12001/bt2");
        webView.loadUrl("https://www.berryshop.nl/");
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
    private void initViews() {
        //设置WebView支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        //从assets目录下加载html
        //webView.loadUrl("file:///android_asset/web.html");
        //自定义WebView的背景颜色,设置为透明色
        webView.setBackgroundColor(Color.TRANSPARENT);
        //设置背景图片
        //webView.setBackgroundResource(R.drawable.ic_launcher_background);
        WebViewClient mWebViewClient = new WebViewClient();
        webView.setWebViewClient(mWebViewClient);
        MWebChromeClient mWebChromeClient = new MWebChromeClient(this);
        webView.setWebChromeClient(mWebChromeClient);
        //添加js调用android(java)的方法接口
        MJavascriptInterface mJavascriptInterface = new MJavascriptInterface(this);
        webView.addJavascriptInterface(mJavascriptInterface,"web");
        webView.addJavascriptInterface(mJavascriptInterface,"jump");
    }

    public class MJavascriptInterface {
        private Context mContext;

        public MJavascriptInterface(Context context) {
            mContext = context;
        }

        /**
         * JS调用Android(Java)无参数的方法
         */
        @JavascriptInterface
        public void jsCallWebView() {
            Toast.makeText(mContext, "JS Call Java!",
                    Toast.LENGTH_SHORT).show();
        }

        /**
         * JS调用Android(Java)含参数的方法
         * @param param
         */
        @JavascriptInterface
        public void jsCallWebView(String param) {
            Toast.makeText(mContext, "JS Call Java!" + param,
                    Toast.LENGTH_SHORT).show();
        }
    }
    public class MWebChromeClient extends WebChromeClient {
        private Context mContext;

        public MWebChromeClient(Context context) {
            mContext = context;
        }

        /**
         * JS调用Android(Java)无参数的方法
         */
        @JavascriptInterface
        public void jsCallWebView() {
            Toast.makeText(mContext, "JjjjjjjjjjjS Call Java!",
                    Toast.LENGTH_SHORT).show();
        }


        /**
         * JS调用Android(Java)含参数的方法
         * @param param
         */
        @JavascriptInterface
        public void jsCallWebView(String param) {
            Toast.makeText(mContext, "JjjjjjjjjjjS Call Java!" + param,
                    Toast.LENGTH_SHORT).show();
        }
    }
    public class MWebViewClient extends WebViewClient {
        private static final String TAG = "MWebViewClient";
        private WebView mWebView;
        private Context mContext;

        public MWebViewClient(WebView webView, Context context) {
            mWebView = webView;
            mContext = context;
        }

        /**
         * 在点击请求的是链接是才会调用，
         * 重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
            mWebView.loadUrl(url);
            // 记得消耗掉这个事件。给不知道的朋友再解释一下，Android中返回True的意思就是到此为止,
            // 事件就会不会冒泡传递了，我们称之为消耗掉
            return true;
        }

        /**
         * 页面开始加载时调用的方法
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e(TAG, "onPageStarted: " );
            super.onPageStarted(view, url, favicon);
        }

        /**
         * 页面加载完成调用的方法
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e(TAG, "onPageFinished: " );
            super.onPageFinished(view, url);
        }

        /**
         * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
         */
        @Override
        public void onLoadResource(WebView view, String url) {
//      Toast.makeText(context, "WebViewClient.onLoadResource", Toast.LENGTH_SHORT).show();
            Log.e("WebActivity", "onLoadResource");
            super.onLoadResource(view, url);
        }

        /**
         * 重写此方法可以让WebView支持https请求
         * @param view
         * @param handler
         * @param error
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
    }
}

