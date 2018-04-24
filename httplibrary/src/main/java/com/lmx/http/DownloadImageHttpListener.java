package com.lmx.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Administrator on 2018\4\8 0008.
 * <p>
 * //下载图片
 */

public class DownloadImageHttpListener implements IHttpListener {

    protected Handler handler = new Handler(Looper.getMainLooper());

    protected ImageView imageView;
    protected int errorid = 0;

    public DownloadImageHttpListener(ImageView imageView) {
        this.imageView = imageView;
    }

    public DownloadImageHttpListener placeholder(int id) {
        if (id != 0) {
            Drawable drawable = imageView.getContext().getResources().getDrawable(id);
            imageView.setImageDrawable(drawable);
        }
        return this;
    }

    public DownloadImageHttpListener error(int id) {
        errorid = id;
        return this;
    }

    @Override
    public void onSuccess(InputStream inputStream) {

        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });

    }

    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (errorid != 0) {
                    Drawable drawable = imageView.getContext().getResources().getDrawable(errorid);
                    imageView.setImageDrawable(drawable);
                }
            }
        });
    }
}
