package com.vedmitryapps.httptest.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vedmitryapps.httptest.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PictureActivity extends AppCompatActivity {

    private ImageView imageView;
    private DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        displayMetrics = getResources().getDisplayMetrics();

        imageView = (ImageView) findViewById(R.id.imageViewPicAct);

        Glide
                .with(this)
                .load(getIntent().getStringExtra("pic"))
                //.load("https://images.unsplash.com/photo-1505370914932-3d5df10b0d4e?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=073ba8833cd6e900f209f785a77a8aac")
                //.load(R.drawable.photo)
                //.override(displayMetrics.widthPixels, displayMetrics.heightPixels)
                //.centerCrop()
                //.fitCenter() // другой вариант
                .into(imageView);

//        File file = new File(getIntent().getStringExtra("pic"));

        URL newurl = null;
        try {
            newurl = new URL(getIntent().getStringExtra("pic"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
/*        Bitmap mIcon_val = getBitmapFromURL(getIntent().getStringExtra("pic"));

        System.out.println("scroll - " + mIcon_val.getWidth());
        System.out.println("scroll - " + mIcon_val.getHeight());*/
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}

