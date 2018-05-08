package com.lmx.utils;


import android.content.Context;
import android.view.WindowManager;

import java.util.UUID;

public class DensityUtil {

    //获得全球唯一性的id
    public static String getId() {
        String id = UUID.randomUUID().toString();//生成的id942cd30b-16c8-449e-8dc5-028f38495bb5中间含有横杠，<span style="color: rgb(75, 75, 75); font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 20.7999992370605px;">用来生成数据库的主键id是很实用的。</span>
        id = id.replace("-", "");//替换掉中间的那个斜杠
        return id;
    }

    /**
     * 根据手机的分辨率从dp的单位转成为px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px的单位转成为dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值 保证文字大小不变
     *
     * @param pxValue
     * @param fontScale
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值 保证文字大小不变
     *
     * @param spValue
     * @param fontScale
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getWindowHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int Height = wm.getDefaultDisplay().getHeight();
        return Height;
    }
}
