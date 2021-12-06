package com.example.alarm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jianglei on 2018/2/4.
 */

public class JavaCallJSActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    @BindView(R.id.wv_java_call_js)
    WebView mWvJavaCallJs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_call_js);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        //设置WebView支持JS
        mWvJavaCallJs.getSettings().setJavaScriptEnabled(true);
        //从assets目录下加载html
        mWvJavaCallJs.loadUrl("file:///android_asset/web.html");
        //自定义WebView的背景颜色,设置为透明色
        mWvJavaCallJs.setBackgroundColor(Color.TRANSPARENT);
        //设置背景图片
        mWvJavaCallJs.setBackgroundResource(R.drawable.ic_launcher_background);
        MWebViewClient mWebViewClient = new MWebViewClient(mWvJavaCallJs,this);
        mWvJavaCallJs.setWebViewClient(mWebViewClient);
        MWebChromeClient mWebChromeClient = new MWebChromeClient(this);
        mWvJavaCallJs.setWebChromeClient(mWebChromeClient);
        //添加js调用android(java)的方法接口
        MJavascriptInterface mJavascriptInterface = new MJavascriptInterface(this);
        mWvJavaCallJs.addJavascriptInterface(mJavascriptInterface,"web");
        mWvJavaCallJs.addJavascriptInterface(mJavascriptInterface,"jump");


    }


    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                mWvJavaCallJs.loadUrl("javascript:javacalljs()"); // 无参数调用
                break;
            case R.id.btn2:
                mWvJavaCallJs.loadUrl("javascript:javacalljsparam(" + "'-----从java代码传递参数到了JS代码'" + ")"); // 有参数调用
                break;
        }
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
