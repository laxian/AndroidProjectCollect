package com.example.vcvyc.mtcnn_new;
/*
  MTCNN For Android
  by cjf@xmu 20180625
 */

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class ImageActivity extends AppCompatActivity {
    String TAG="ImageActivity";
    ImageView imageView;
    Bitmap bitmap;
    private  Bitmap readFromAssets(String filename){
        Bitmap bitmap;
        AssetManager asm=getAssets();
        try {
            InputStream is=asm.open(filename);
            bitmap= BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            Log.e("MainActivity","[*]failed to open "+filename);
            e.printStackTrace();
            return null;
        }
        return Utils.copyBitmap(bitmap); //返回mutable的image
    }
    MTCNN mtcnn;
    public void processImage(){
        Bitmap bm= Utils.copyBitmap(bitmap);
        try {
            Vector<Box> boxes=mtcnn.detectFaces(bm,40);
            for (int i=0;i<boxes.size();i++){
                Utils.drawRect(bm,boxes.get(i).transform2Rect());
                Utils.drawPoints(bm,boxes.get(i).landmark);
            }
            imageView.setImageBitmap(bm);
        }catch (Exception e){
            Log.e(TAG,"[*]detect false:"+e);
        }
    }
    public void myMain(){
        imageView =(ImageView)findViewById(R.id.imageView);
        bitmap=readFromAssets("trump2.jpg");
        mtcnn=new MTCNN(getAssets());
        processImage();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, 0x1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(data==null)return;
        try {
//            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
            bitmap = compressBitmap(data.getData());
            processImage();
        }catch (Exception e){
            Log.d("MainActivity","[*]"+e);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap compressBitmap(Uri url) throws IOException {
        ContentResolver cr = this.getContentResolver();
        InputStream input = cr.openInputStream(url);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, options);
        int inSampleSize = ImageUtils.calculateInSampleSize(options, imageView.getWidth(), imageView.getHeight());
        input.close();

        input = cr.openInputStream(url);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize*2;
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        myMain();
    }
}
