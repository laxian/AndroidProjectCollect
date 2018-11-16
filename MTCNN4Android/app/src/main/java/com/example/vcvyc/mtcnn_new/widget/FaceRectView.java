package com.example.vcvyc.mtcnn_new.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.vcvyc.mtcnn_new.Box;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FaceRectView extends View {
    private static final String TAG = "FaceRectView";
    private CopyOnWriteArrayList<Box> faceRectList = new CopyOnWriteArrayList<>();

    public FaceRectView(Context context) {
        this(context, null);
    }

    public FaceRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if (faceRectList != null && faceRectList.size() > 0) {
            for (int i = 0; i < faceRectList.size(); i++) {
                drawFaceRect(canvas, faceRectList.get(i), Color.YELLOW, 5);
                drawFaceDot(canvas, faceRectList.get(i).landmark, Color.YELLOW);
            }
        }
    }

    private void drawFaceDot(Canvas canvas, Point[] landmark, int color) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStrokeWidth(2);

        for (int i=0;i<landmark.length;i++){
            int x=landmark[i].x;
            int y=landmark[i].y;
            //Log.i("Utils","[*] landmarkd "+x+ "  "+y);
            canvas.drawRect(new Rect(x-3,y-3,x+3,y+3), paint);
        }

    }

    private Rect convert2Rect(Box box) {
        Rect rect = new Rect(box.left(), box.top(), box.right(), box.bottom());
        return rect;
    }

    private void drawFaceRect(Canvas canvas, Box box, int color, int i) {
        Rect rect = convert2Rect(box);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(i);

        canvas.drawRect(rect, paint);
    }

    public void clearFaceInfo() {
        faceRectList.clear();
        postInvalidate();
    }

    public void addFaceInfo(Box faceInfo) {
        faceRectList.add(faceInfo);
        postInvalidate();
    }

    public void addFaceInfo(List<Box> faceInfoList) {
        faceRectList.clear();
        faceRectList.addAll(faceInfoList);
        postInvalidate();
    }
}