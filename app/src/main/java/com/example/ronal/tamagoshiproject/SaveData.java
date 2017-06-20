package com.example.ronal.tamagoshiproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public final class SaveData
{
    private static final String PREFS_NAME = "MyPrefsFile";

    public static void SaveData(String key, int value, Context ctx)
    {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);

        editor.apply();
    }

    public static int GetData(String key, Context ctx)
    {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(key, 100);
    }

}
