package test.zwx.com.dialogleaktest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

/**
 * 模拟Dialog内存泄漏
 */
public class ForthActivity extends AppCompatActivity {
    private static final String TAG = "ForthActivity";
    private Handler mMyHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth);

        findViewById(R.id.btn_test1).setOnClickListener(view -> {
            DialogFragment dialogFragment = new MyDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "dialog_fragment");
        });
    }
}