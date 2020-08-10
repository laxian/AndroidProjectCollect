package test.zwx.com.dialogleaktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FifthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ListFragment.newInstance(), "list").commit();
    }
}
