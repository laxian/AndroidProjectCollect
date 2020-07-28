package com.segway.launcher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private PackageManager manager;
    private List<Item> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        loadApps();
        loadListView();
    }

    private void loadApps() {
        manager = getPackageManager();
        apps = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities =  manager.queryIntentActivities(i, 0);
        for (ResolveInfo r : availableActivities) {
            Item app = new Item();
            app.label = r.activityInfo.packageName; // package name
            app.name = r.loadLabel(manager); // app name
            app.icon = r.loadIcon(manager); // app icon
            Log.d("PackageName", "loadApps: "+app.name + "::" + app.label);
            apps.add(app);
        }
        Collections.sort(apps);
    }

    private void loadListView() {
        RecyclerView list = findViewById(R.id.list);
//        list.setNestedScrollingEnabled(false);

        list.setLayoutManager(new GridLayoutManager(this, 6));
        AppAdapter adapter = new AppAdapter(this, apps);
        list.setAdapter(adapter);
        adapter.setListener((position, which) -> {
            Intent i = manager.getLaunchIntentForPackage(which.label.toString());
            startActivity(i);
        });
    }
}
