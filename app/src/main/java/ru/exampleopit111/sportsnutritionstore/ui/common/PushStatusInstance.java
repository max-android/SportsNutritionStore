package ru.exampleopit111.sportsnutritionstore.ui.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created Максим on 08.07.2018.
 * Copyright © Max
 */
public class PushStatusInstance {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PushStatusInstance(Context context) {
        preferences = context.getSharedPreferences(Constants.PUSH_SWITCH_KEY, Context.MODE_PRIVATE);
    }

    public void setPushStatus(String key, boolean status) {
        createSpEditor();
        editor.putBoolean(key, status);
        editor.apply();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void deletePushStatus(String key) {
        createSpEditor();
        editor.remove(key);
        editor.apply();
    }

    private void createSpEditor() {
        editor = preferences.edit();
    }
}
