package com.lmx.http;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
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
        Lmx.sendJsonRequest("/sdcard/Lmx/", "lmx.apk", url, new IDataListener<String>() {
            @Override
            public void onSuccess(String s) {

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
        Lmx.sendJsonRequest(url, image);
    }

    private void post() {
        String url = "http://thy.nginx.cqbornsoft.com:5601/app/verifyUser.htm";
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("userName", "18623092375");
        Lmx.sendJsonRequest(jsonObject, url, dataclass.class, new IDataListener<dataclass>() {
            @Override
            public void onSuccess(dataclass data) {


                text6.setText(data.toString());
            }

            @Override
            public void onFailure() {
                Log.e("data1", "onFailure");
            }
        });
    }

    private void get() {
        String url = "http://thy.nginx.cqbornsoft.com:5601/app/scenicList.htm";
        Lmx.sendJsonRequest(null, url, SienceData.class, new IDataListener<SienceData>() {
            @Override
            public void onSuccess(SienceData data) {

                text6.setText(data.toString());
            }

            @Override
            public void onFailure() {
                Log.e("data", "onFailure");
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

    private void upfile() {
        String url = "http://thy.nginx.cqbornsoft.com:5601/app/imagesUpload.htm";
        File file = new File("/sdcard/Lmx/image.png");
        Lmx.sendJsonRequest2(file, url, dataclass.class, new IDataListener<dataclass>() {
            @Override
            public void onSuccess(dataclass data) {

                text6.setText(data.toString());
            }

            @Override
            public void onFailure() {
                text6.setText("上传文件失败");
            }
        });
    }
}
