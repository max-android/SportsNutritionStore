package ru.exampleopit111.sportsnutritionstore.model.data_holder;

import android.content.Context;
import android.content.SharedPreferences;

import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;

/**
 * Created Максим on 20.06.2018.
 * Copyright © Max
 */
public class UserDataHolder {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public UserDataHolder(Context context) {
        preferences = context.getSharedPreferences(Constants.USER_DATA_HOLDER, Context.MODE_PRIVATE);
    }

    public void setDataUser(String key, String data) {
        createSpEditor();
        editor.putString(key, data);
        editor.apply();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void deleteDataUser(String key) {
        createSpEditor();
        editor.remove(key);
        editor.apply();
    }

    private void createSpEditor() {
        editor = preferences.edit();
    }
}
