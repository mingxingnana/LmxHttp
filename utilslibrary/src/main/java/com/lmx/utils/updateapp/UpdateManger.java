package com.lmx.utils.updateapp;

/**
 * Created by Administrator on 2016/6/24.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;

import com.lmx.utils.permission.LmxPermission;
import com.lmx.utils.toast.T;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UpdateManger {
    private Context mContext;
    private String apkUrl;
    //    private static final String savePath = "/sdcard/Lmx/";// 保存apk的文件夹
//    private static final String saveFileName = savePath
//            + "hoteluser.apk";
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;// 当前进度
    private Thread downLoadThread; // 下载线程
    private boolean interceptFlag = false;// 用户取消下载
    private ProgressDialog dialog;
    private boolean isForceUpdate = false;
    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    dialog.setProgress(progress);
                    break;
                case DOWN_OVER:
                    dialog.dismiss();
                    installApk();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected static UpdateManger instance;
    protected AlertDialog.Builder builder;

    protected String savePath;
    protected String filename;
    protected String saveFileName;

    public UpdateManger() {

    }

    public static UpdateManger with() {
        if (instance == null) {
            synchronized (UpdateManger.class) {
                if (instance == null) {
                    instance = new UpdateManger();
                }
            }
        }
        return instance;
    }

    public UpdateManger setApkPath(String savePath, String filename) {
        this.filename = filename;
        this.savePath = savePath;
        saveFileName = savePath + filename;
        return this;
    }

    public UpdateManger createDialog(Context context) {
        mContext = context;
        interceptFlag = false;
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        return this;
    }

    public UpdateManger setTitile(String title) {
        builder.setTitle(title);
        return this;
    }

    public UpdateManger setMessage(String title) {
        builder.setMessage(title);
        return this;
    }

    public UpdateManger setForceUpdate(boolean isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
        return this;
    }

    public UpdateManger setUrl(String url) {
        this.apkUrl = url;
        return this;
    }

    public void show() {
        if (isForceUpdate) {
            builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    hasPermission();
                }
            });
        } else {
            builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    hasPermission();
                }
            });
            builder.setNegativeButton("取消", null);
        }
        builder.create().show();
    }

    protected void hasPermission() {
        if (LmxPermission.hasPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            update();
        } else {
            LmxPermission.with((Activity) mContext).permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .requestCode(100).send();
        }

    }


    protected void update() {

        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            T.showShort(mContext, "sdcard不存在，请先插入sdcard");
            if (isForceUpdate)
                ((Activity) mContext).finish();
            return;
        }
//        apkUrl = "http://dldir1.qq.com/weixin/android/weixin661android1220_1.apk";
        dialog = new ProgressDialog(mContext);
        dialog.setTitle("正在更新...");
        dialog.setCancelable(false);// 设置点击空白处也不能关闭该对话框
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        if (!isForceUpdate)
            dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // 这里添加点击后的逻辑
                            interceptFlag = true;
                            dialog.dismiss();
                        }
                    });
        dialog.show();
        downloadApk();
    }


    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    protected void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(mContext, getPackageName() + ".fileprovider", apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                    "application/vnd.android.package-archive");// File.toString()会返回路径信息
        }
        mContext.startActivity(intent);
        if (isForceUpdate)
            ((Activity) mContext).finish();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            URL url;
            try {
                url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream ins = conn.getInputStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream outStream = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = ins.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 下载进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    outStream.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消停止下载
                outStream.close();
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    // 获取versionName
    protected String getPackageName() {
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return pi.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        List<String> grantedList = new ArrayList<>();
        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                grantedList.add(permissions[i]);
            else
                deniedList.add(permissions[i]);
        }
        if (deniedList.isEmpty()) {
            update();
        } else {
            if (isForceUpdate) {
                ((Activity) mContext).finish();
            }
        }


    }

}

