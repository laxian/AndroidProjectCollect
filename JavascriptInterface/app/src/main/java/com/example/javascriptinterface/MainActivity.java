package com.example.javascriptinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.mWebView);

        // 设置 webViewClient 类
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.loadUrl("javascript:function addJs() {\n" +
                        "    var script=document.createElement('script');\n" +
                        "    script.type='text/javascript';\n" +
                        "    script.src='./JSBridge.js';\n" +
                        "    console.log(script);\n" +
                        "    var body=document.getElementsByTagName('body')[0];\n" +
                        "    console.log(body);\n" +
                        "    body.appendChild(script);\n" +
                        "} addJs();");
            }
        });

        // 设置 webChromeClient 类
        mWebView.setWebChromeClient(new WebChromeClient());

        // 设置支持调用 JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮

        mWebView.loadUrl("file:///android_asset/deliver.html");

        JSBridge.register("JSBridge", NativeMethods.class);
        mWebView.addJavascriptInterface(new JSBridge(mWebView), "_jsbridge");
    }

    // 通过拦截 onKeyDown 事件实现网页回退
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
