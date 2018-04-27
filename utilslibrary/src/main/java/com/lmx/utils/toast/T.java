package com.lmx.utils.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmx.utils.R;


//Toast统一管理类
public class T {

    private static TextView toastRoot;
    private static Toast toast;
    private static LinearLayout toastLayout;

    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;


    public static void showShort(Context context, String text) {
        if (null == toastRoot) {
            toastLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.toast, null);
            toastRoot = (TextView) toastLayout.findViewById(R.id.texttoast);
        }
        if (null == toast) {
            toast = new Toast(context);
            toast.setView(toastLayout);
//            toast.setGravity(Gravity.TOP, 0, DensityUtil.dip2px(context, 55));
            toast.setGravity(Gravity.TOP, 0, 0);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toastRoot.setText(text);
        toast.show();
    }

    public static void showLong(Context context, String text) {
        if (null == toastRoot) {
            toastLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.toast, null);
            toastRoot = (TextView) toastLayout.findViewById(R.id.texttoast);
        }
        if (null == toast) {
            toast = new Toast(context);
            toast.setView(toastLayout);
//            toast.setGravity(Gravity.TOP, 0, DensityUtil.dip2px(context, 55));
            toast.setGravity(Gravity.TOP, 0, 0);
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toastRoot.setText(text);
        toast.show();
    }

}
