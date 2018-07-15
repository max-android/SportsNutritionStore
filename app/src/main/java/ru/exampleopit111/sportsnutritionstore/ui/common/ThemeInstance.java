package ru.exampleopit111.sportsnutritionstore.ui.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created Максим on 08.07.2018.
 * Copyright © Max
 */
public class ThemeInstance {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ThemeInstance(Context context) {
        preferences = context.getSharedPreferences(Constants.THEME_KEY, Context.MODE_PRIVATE);
    }

    public void setTheme(String key, String theme) {
        createSpEditor();
        editor.putString(key, theme);
        editor.apply();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void deleteTheme(String key) {
        createSpEditor();
        editor.remove(key);
        editor.apply();
    }

    private void createSpEditor() {
        editor = preferences.edit();
    }
}
