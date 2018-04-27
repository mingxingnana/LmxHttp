package com.lmx.utils.hook;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;


import com.lmx.utils.PreferencesUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by Bolex on 2017/6/20.
 */

public class AMSHookUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;
    private static String sPackageName;
    private static String sHostClazzName;
    private static String[] loginClass;
    private static String loginKey;
    private static String loginActivity;
    private static IntentLietener lietener;

    /**
     * * 这里我们通过反射获取到AMS的代理本地代理对象
     * Hook以后动态串改Intent为已注册的来躲避检测
     *
     * @param context 上下文
     */
    public static void hookStartActivity(Context context, String[] loginclass, String loginkey, String loginactivity,
                                         IntentLietener intentLietener) {
        if (context == null || sContext != null) {
            return;
        }
        loginClass = loginclass;
        loginKey = loginkey;
        loginActivity = loginactivity;
        lietener = intentLietener;
        try {
            Context applicationContext = context.getApplicationContext();
            sContext = applicationContext;
            PackageManager manager = applicationContext.getPackageManager();
            sPackageName = applicationContext.getPackageName();
            PackageInfo packageInfo = manager.getPackageInfo(sPackageName, PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activities = packageInfo.activities;
            if (activities == null || activities.length == 0) {
                return;
            }
            ActivityInfo activityInfo = activities[0];
            sHostClazzName = activityInfo.name;

            Class<?> amnClazz;
            Field defaultField;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {//8.0获取的方式不同
                amnClazz = Class.forName("android.app.ActivityManager");
                defaultField = amnClazz.getDeclaredField("IActivityManagerSingleton");
            } else {
                amnClazz = Class.forName("android.app.ActivityManagerNative");
                defaultField = amnClazz.getDeclaredField("gDefault");
            }
            defaultField.setAccessible(true);
            Object gDefaultObj = defaultField.get(null); //所有静态对象的反射可以通过传null获取。如果是实列必须传实例
            Class<?> singletonClazz = Class.forName("android.util.Singleton");
            Field amsField = singletonClazz.getDeclaredField("mInstance");
            amsField.setAccessible(true);
            Object amsObj = amsField.get(gDefaultObj);

            amsObj = Proxy.newProxyInstance(context.getClass().getClassLoader(),
                    amsObj.getClass().getInterfaces(),
                    new HookInvocationHandler(amsObj, sPackageName, sHostClazzName, lietener));
            amsField.set(gDefaultObj, amsObj);
            hookLaunchActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * hook ActivityThread  handle 在这里我们需要替换我们未被注册的Activity Intent
     *
     * @throws Exception
     */
    private static void hookLaunchActivity() throws Exception {
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Field sCurrentActivityThreadField = activityThreadClazz.getDeclaredField("sCurrentActivityThread");
        sCurrentActivityThreadField.setAccessible(true);
        Object sCurrentActivityThreadObj = sCurrentActivityThreadField.get(null);
        Field mHField = activityThreadClazz.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH = (Handler) mHField.get(sCurrentActivityThreadObj);
        Field callBackField = Handler.class.getDeclaredField("mCallback");
        callBackField.setAccessible(true);
        callBackField.set(mH, new ActivityThreadHandlerCallBack());
    }

    private static class ActivityThreadHandlerCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            int LAUNCH_ACTIVITY = 0;
            try {
                Class<?> clazz = Class.forName("android.app.ActivityThread$H");
                Field field = clazz.getField("LAUNCH_ACTIVITY");
                LAUNCH_ACTIVITY = field.getInt(null);
            } catch (Exception e) {
            }
            if (msg.what == LAUNCH_ACTIVITY) {
                handleLaunchActivity(msg);
            }
            return false;
        }
    }

    private static void handleLaunchActivity(Message msg) {
        try {
            Object obj = msg.obj;
            Field intentField = obj.getClass().getDeclaredField("intent");
            intentField.setAccessible(true);
            Intent proxyIntent = (Intent) intentField.get(obj);

            //拿到之前真实要被启动的Intent 然后把Intent换掉
            Intent originallyIntent = proxyIntent.getParcelableExtra("originallyIntent");
            if (originallyIntent == null) {
                return;
            }

            String isLogin = PreferencesUtils.getStringPreference(sContext, loginKey, "");
            if (TextUtils.isEmpty(isLogin) && loginClass != null && Arrays.asList(loginClass).contains(originallyIntent.getComponent().getClassName())) {
                proxyIntent.setComponent(new ComponentName(sContext, loginActivity));
            } else {
                //将传递到下一个activity的key赋值给现在的activity
                Bundle bundle = originallyIntent.getExtras();
                if (bundle != null) {
                    proxyIntent.putExtras(bundle);
                }
                proxyIntent.setComponent(originallyIntent.getComponent());
            }

//            //todo:兼容AppCompatActivity
            Class<?> forName = Class.forName("android.app.ActivityThread");
            Field field = forName.getDeclaredField("sCurrentActivityThread");
            field.setAccessible(true);
            Object activityThread = field.get(null);
            Method getPackageManager = activityThread.getClass().getDeclaredMethod("getPackageManager");
            Object iPackageManager = getPackageManager.invoke(activityThread);
            PackageManagerHandler handler = new PackageManagerHandler(iPackageManager);
            Class<?> iPackageManagerIntercept = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iPackageManagerIntercept}, handler);
            // 获取 sPackageManager 属性
            Field iPackageManagerField = activityThread.getClass().getDeclaredField("sPackageManager");
            iPackageManagerField.setAccessible(true);
            iPackageManagerField.set(activityThread, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class PackageManagerHandler implements InvocationHandler {
        private Object mActivityManagerObject;

        PackageManagerHandler(Object mActivityManagerObject) {
            this.mActivityManagerObject = mActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("getActivityInfo")) {
                ComponentName componentName = new ComponentName(sPackageName, sHostClazzName);
                args[0] = componentName;
            }
            return method.invoke(mActivityManagerObject, args);
        }
    }

}
