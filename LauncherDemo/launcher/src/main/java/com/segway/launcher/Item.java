package com.segway.launcher;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

public class Item implements Comparable<Item>{
    CharSequence label; //package name
    CharSequence name; //app name
    Drawable icon; //app icon

    @Override
    public int compareTo(@NonNull Item o) {
        return name.toString().compareTo(o.name.toString());
    }
}
