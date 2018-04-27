package com.lmx.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 配置相关信息缓存
 *
 * @author shallcheek
 * @version 1.0
 * @date 2014年11月25日
 */
public class PreferencesUtils {
    private static String preference = "setting";

    private final static String DigestData = "DigestData";

    public static void setStringPreferences(Context context, String key,
                                            String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPreference(Context context, String key,
                                             String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setIntPreference(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntPreference(Context context, String key,
                                       int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                preference, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void setBooleanPreference(Context context, String key,
                                            boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBooleanPreference(Context context, String key,
                                               boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                preference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

}
