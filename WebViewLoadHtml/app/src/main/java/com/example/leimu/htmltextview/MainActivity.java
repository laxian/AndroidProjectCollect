package com.example.leimu.htmltextview;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window globalWindow = getWindow();
            if (globalWindow!=null) {//FLAG_TRANSLUCENT_NAVIGATION // FLAG_TRANSLUCENT_STATUS
                globalWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }

        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.webview);
        configWebViewSetting();

//        loadHTMLContent();
        mWebview.loadUrl("file:///android_asset/License/deliver.html");

    }


    /**
     * 加载assert中的HTML
     * <p>
     * 项目中不允许出现绝对路径  因此 不能直接使用 load("file:///android_asset/xxx.html")
     * mWebview.loadUrl("file:///android_asset/License/privacy.html");
     */

    private void loadHTMLContent() {
        String content = getHTMLContent();
        if (TextUtils.isEmpty(content)) {
            Log.i(TAG, "content = null");
            return;
        }
        mWebview.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
    }

    private String getHTMLContent() {
        String content = "";
        try {
            AssetManager am = getAssets();
            InputStream in = am.open("License/deliver.html");
            content = IOUtils.toString(in,"UTF-8");
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
        return content;
    }


    /**
     * 配置webview
     * webview 存在漏洞  因为不需要js交互,文件加载,路径加载  所以设为false  避免被攻击.
     */
    private void configWebViewSetting() {
	    WebView.setWebContentsDebuggingEnabled(true);
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        mWebview.setScrollContainer(false);
        mWebview.setVerticalScrollBarEnabled(false);
        mWebview.setHorizontalScrollBarEnabled(false);
        mWebview.addJavascriptInterface(new JsInterface(), "android");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        settings.setAllowFileAccess(true);

        settings.setAllowFileAccessFromFileURLs(true);
        //设置一下背景  防止和原布局中的背景色不协调
        mWebview.setBackgroundColor(0);
        mWebview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                v.scrollTo(0, 0);
            }
        });

    }

    class JsInterface {
        @JavascriptInterface
        public void setWH() {
            int w = mWebview.getWidth();
            int h = mWebview.getHeight();
            mWebview.setPadding(0,0,0,0);
            Log.d("ZWX", "w : " + w);
            Log.d("ZWX", "h : " + h);
            final String func = "javascript: setWH(" + w + "," + h + ")";
            Log.d("ZWX", "f : " + func);
            mWebview.post(new Runnable() {
                @Override
                public void run() {
                    mWebview.loadUrl(func);
                }
            });
        }
    }
}
