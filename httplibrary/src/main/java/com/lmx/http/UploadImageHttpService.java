package com.lmx.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class UploadImageHttpService implements IHttpService {
    private String url;
    private byte[] requestData;
    private IHttpListener iHttpListener;
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000;//超时时间
    private static final String CHARSET = "utf-8";//设置编码
    File file;

    public UploadImageHttpService(File file) {
        this.file = file;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void execute() {
        httpUrlconn();
    }


    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        iHttpListener = httpListener;
    }


    private void httpUrlconn() {
        String BOUNDARY = UUID.randomUUID().toString();//边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";//内容类型
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);//允许输入流
            conn.setDoOutput(true);//允许输出流
            conn.setUseCaches(false);//不允许使用缓存
            conn.setRequestMethod("POST");//请求方式
            conn.setRequestProperty("Charset", CHARSET);//设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            conn.connect();

            if (file != null) {
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
                dos.writeBytes("Content-Disposition: form-data; " + "name=\"inputName\";filename=\"" + file.getName() + "\"" + LINE_END);
                dos.writeBytes(LINE_END);

                FileInputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                int res = conn.getResponseCode();
                if (res == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    iHttpListener.onSuccess(input);
                } else {
                    iHttpListener.onFailure();
                }
            } else {
                iHttpListener.onFailure();
            }

        } catch (IOException e) {
            e.printStackTrace();
            iHttpListener.onFailure();
        }
    }

    private static String getMIMEType(File file) {
        String fileName = file.getName();
        if (fileName.endsWith("png") || fileName.endsWith("PNG")) {
            return "image/png";
        } else {
            return "image/jpg";
        }
    }
}
