package com.example.vcvyc.mtcnn_new;
/*
  MTCNN For Android
  by cjf@xmu 20180625
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button image;
    private Button camrea;
    private Button video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        button = findViewById(R.id.image);
    }

    private void initView() {
        image = (Button) findViewById(R.id.image);
        camrea = (Button) findViewById(R.id.camera);
        video = (Button) findViewById(R.id.video);

        image.setOnClickListener(this);
        camrea.setOnClickListener(this);
        video.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                startActivity(new Intent(MainActivity.this, ImageActivity.class));
                break;
            case R.id.camera:
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
            case R.id.video:

                break;
        }
    }
}
