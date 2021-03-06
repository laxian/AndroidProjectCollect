package com.zhouweixian.instadownloader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bachors.prefixinput.EditText;
import com.google.gson.Gson;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.zhouweixian.instadownloader.adapters.InstaAdapter;
import com.zhouweixian.instadownloader.api.SourceApi;
import com.zhouweixian.instadownloader.models.SourceModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

/**
 * @author Ican Bachors
 * @version 1.1
 * Source: https://github.com/bachors/Insta-Downloader
 */

public class MainActivity extends RxAppCompatActivity {

    private static final String TAG = "Ins->";
    private EditText input;
    private final String intaLink = "https://www.instagram.com/p/";
    private final String urlPattern = "https://www.instagram.com(?:/.*?)?/p/(.*)(?:/.*)?";
    //    private InstaDownloader insta;
    private Toolbar mToolbar;
    private EditText mInput;
    private RecyclerView mInstadown;
    private ImageView mImage;
    private ImageView mRepost;
    private ImageView mDownload;
    private CardView mCardView;
    private InstaAdapter adapter;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // marshmallow
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }

        // config
//        insta = new InstaDownloader(this);
//        insta.setAccessToken("11952654181.1677ed0.c278f4ed47824748b6a36647305f7bd3");
//        insta.setDir("/InstaDownloader");

        // input url
        input = findViewById(R.id.input);
        input.setPrefix(intaLink);
        input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return tryLoadImageAndVideo();
                }
                return false;
            }
        });

        // clipboard listener
        Intent svc = new Intent(MainActivity.this, InstaClipBoard.class);
        startService(svc);
        autoInputFromClipboard();
    }

    private void autoInputFromClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        assert clipboard != null;
        if ((clipboard.hasPrimaryClip())) {
            if ((clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                final ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                String paste = item.getText().toString();
                Pattern pattern = Pattern.compile(urlPattern);
                Matcher matcher = pattern.matcher(paste);
                boolean matches = matcher.matches();
                String id = "";
                if (matches) {
                    id = matcher.group(1);
                }
                if (!id.isEmpty()) {
                    input.setText(intaLink + id);
//                    insta.get(item.getText().toString().trim());
                }
            }
        }
    }

    private boolean tryLoadImageAndVideo() {
        String url2 = input.getText().toString().trim();
        String url = input.getText().toString().trim();
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(url);
        boolean matches = matcher.matches();
        String id = "";
        if (matches) {
            id = matcher.group(1);
        }
        if (!id.isEmpty()) {

            HttpManager manager = HttpManager.getInstance();
            SourceApi sourceApi = new SourceApi(new HttpOnNextListener<SourceModel>() {
                @Override
                public void onNext(SourceModel model) {
                    Log.d("XXXX", model.get__typename());
                    handleResponse(model);
                }

                @Override
                public void onCacheNext(String string) {
                    super.onCacheNext(string);
                    SourceModel sourceModel = new Gson().fromJson(string, SourceModel.class);
                    handleResponse(sourceModel);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                }
            }, MainActivity.this);
            sourceApi.setUrl(input.getText().toString());
            manager.doHttpDeal(sourceApi);
        } else
            Toast.makeText(getApplicationContext(), "URL not valid.", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        tryLoadImageAndVideo();
        showFloat();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showFloat() {
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        mButton = new Button(getApplicationContext());
        mButton.setText("Hello");
        mButton.setBackgroundColor(Color.CYAN);

        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        mLayoutParams.format = PixelFormat.TRANSPARENT;
        mLayoutParams.width = 500;
        mLayoutParams.height = 200;

        mWindowManager.addView(mButton, mLayoutParams);

        mButton.setOnTouchListener(new View.OnTouchListener() {
            int lastX = 0, lastY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = rawX;
                        lastY = rawY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaX = rawX - lastX;
                        int deltaY = rawY - lastY;
                        mLayoutParams.x += deltaX;
                        mLayoutParams.y += deltaY;
                        Log.d(TAG, "showFloat: dx: " + deltaX + "dy: " + deltaY + " - " + mLayoutParams);
                        mWindowManager.updateViewLayout(mButton, mLayoutParams);
                        lastX = rawX;
                        lastY = rawY;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void handleResponse(SourceModel model) {
        if (!model.isIs_video()) {
            List<SourceModel.ImagesBean> images = model.getImages();
            if (images != null && images.size() > 0) {
                adapter.addAll(images);
            }
        } else {
            SourceModel.VideoBean video = model.getVideo();
            SourceModel.ImagesBean imagesBean = new SourceModel.ImagesBean();
            imagesBean.setIs_video(true);
            imagesBean.setVideo_url(video.getVideo_url());
            imagesBean.setThumbnail_src(video.getThumbnail_src());
            imagesBean.setVideo_duration(video.getVideo_duration());
            imagesBean.setShortcode(video.getShortcode());
            imagesBean.set__typename(video.get__typename());
            adapter.addAll(Collections.singletonList(imagesBean));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem actionViewItem = menu.findItem(R.id.instagram);
        // Retrieve the action-view from menu
        View v = MenuItemCompat.getActionView(actionViewItem);
        // Find the button within action-view
        Button x = v.findViewById(R.id.btn_instagram);
        // Handle button click here
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                if (launchIntent != null) {
                    try {
                        startActivity(launchIntent);
                    } catch (ActivityNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mInput = findViewById(R.id.input);
        mInstadown = findViewById(R.id.instadown);
        mImage = findViewById(R.id.image);
        mRepost = findViewById(R.id.repost);
        mDownload = findViewById(R.id.download);
        mCardView = findViewById(R.id.card_view);
        LinearLayoutManager inLayout = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mInstadown.setLayoutManager(inLayout);
        mInstadown.setItemAnimator(new DefaultItemAnimator());
        adapter = new InstaAdapter(this);
        adapter.setDir("InsDownloader");
        mInstadown.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        autoInputFromClipboard();
    }
}
