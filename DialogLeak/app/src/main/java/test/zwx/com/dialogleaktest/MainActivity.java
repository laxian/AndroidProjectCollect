package test.zwx.com.dialogleaktest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 重现步骤：
 * 运行app，在MainActivity页面点击按钮打开FourActivity，点击发送消息，再返回到MainActivity。
 * 再继续同样操作，如此反复来回切换两个Activity，即可重现内存泄漏现象，LeakCanary会提示并记录。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test1).setOnClickListener(v -> {
            MainActivity.this.startActivity(new Intent(MainActivity.this, SecondActivity.class));
        });
        findViewById(R.id.btn_test2).setOnClickListener(v -> {
            MainActivity.this.startActivity(new Intent(MainActivity.this, ThirdActivity.class));
        });
        findViewById(R.id.btn_test3).setOnClickListener(v -> {
            MainActivity.this.startActivity(new Intent(MainActivity.this, ForthActivity.class));
        });
        findViewById(R.id.btn_test4).setOnClickListener(v -> {
            MainActivity.this.startActivity(new Intent(MainActivity.this, FifthActivity.class));
        });
    }

}