package com.lmx.http;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lmx.httpagent.HttpCallback;
import com.lmx.httpagent.HttpHelper;
import com.lmx.utils.hook.AMSHookUtil;
import com.lmx.utils.hook.IntentLietener;
import com.lmx.utils.loadingdailog.MProgressDialog;
import com.lmx.utils.permission.PermissionListener;
import com.lmx.utils.updateapp.UpdateManger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TGA = MainActivity.class.getClass().getSimpleName();
    ImageView image;
    TextView text6;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        image = findViewById(R.id.image);
        text6 = findViewById(R.id.text6);

    }


    private void downloadfile() {
        String url = "http://image244.nginx.cqbornsoft.com:5601/uploadfile/images/2018-03/20/110_1965962487.apk";

        HttpHelper.obtion().downloadFile("/sdcard/Lmx/", "lmx.apk", url, new HttpCallback<String>() {
            @Override
            public void onSucess(String s) {
                text6.setText("下载文件成功,保存路径:" + s);
            }

            @Override
            public void onFailure() {
                Log.e("ssss", "onFailure");
            }
        });
    }

    private void downloadimage() {
        String url = "http://image244.nginx.cqbornsoft.com:5601/uploadfile/images/2018-02/08/110_927437889.jpg";
        HttpHelper.obtion().downloadImage(url, image);
    }

    private void post() {
        String url = "http://thy.nginx.cqbornsoft.com:5601/app/verifyUser.htm";
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("userName", "18623092375");
        HttpHelper.obtion().post(url, jsonObject, new HttpCallback<String>() {
            @Override
            public void onSucess(String s) {
                text6.setText(s);
            }

            @Override
            public void onFailure() {
                Log.e("data1", "onFailure");
            }
        });

    }

    private void get() {
        String url = "http://thy.nginx.cqbornsoft.com:5601/app/scenicList.htm";
        HttpHelper.obtion().post(url, null, new HttpCallback<String>() {
            @Override
            public void onSucess(String s) {
                text6.setText(s.toString());
            }

            @Override
            public void onFailure() {
                Log.e("data", "onFailure");
            }
        });
    }

    private void upfile() {

        String url = "http://thy.nginx.cqbornsoft.com:5601/app/imagesUpload.htm";
        File file = new File("/sdcard/Lmx/image.png");
        HttpHelper.obtion().uploadFile(file, url, new HttpCallback<String>() {
            @Override
            public void onSucess(String s) {

                text6.setText(s.toString());
            }

            @Override
            public void onFailure() {
                text6.setText("上传文件失败");
            }
        });
    }

    public void get(View view) {
        get();
    }

    public void post(View view) {
        post();
    }


    public void downimage(View view) {
        downloadimage();
    }

    public void downfile(View view) {
        downloadfile();
    }

    public void upfile(View view) {
        upfile();
    }

}
