package com.example.myfuckingpc.gigshub1.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SavedToken {
    static final String PREF_USER_INFO= "";
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserInfo(Context ctx, String userInfo)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();

        editor.putString(PREF_USER_INFO, userInfo);
        editor.commit();
    }

    public static String getUserInfo(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_INFO, "");

    }
}
