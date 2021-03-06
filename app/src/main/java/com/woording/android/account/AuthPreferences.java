/*
 * Woording for Android is a project by PhiliPdB.
 *
 * Copyright (c) 2016.
 */

package com.woording.android.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class AuthPreferences {

    private static final String PREFS_NAME = "auth";
    private static final String KEY_ACCOUNT_NAME = "account_name";
    private static final String KEY_AUTH_TOKEN = "auth_token";

    private final SharedPreferences preferences;

    public AuthPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Nullable
    public String getAccountName() {
        return preferences.getString(KEY_ACCOUNT_NAME, null);
    }

    @Nullable
    public String getAuthToken() {
        return preferences.getString(KEY_AUTH_TOKEN, null);
    }

    public void setUsername(String accountName) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ACCOUNT_NAME, accountName);
        editor.apply();
    }

    public void setAuthToken(String authToken) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_AUTH_TOKEN, authToken);
        editor.apply();
    }
}
