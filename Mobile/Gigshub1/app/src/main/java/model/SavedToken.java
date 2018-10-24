package model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SavedToken {
    static final String PREF_USER_TOKEN= "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserToken(Context ctx, String userInfo)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_TOKEN, userInfo);
        editor.commit();
    }

    public static String getUserToken(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_TOKEN, "");

    }


}
