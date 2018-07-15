package ru.exampleopit111.sportsnutritionstore.model.system;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * Created Максим on 05.06.2018.
 * Copyright © Max
 */
public class ResourceManager {
    private Context context;

    public ResourceManager(@NonNull Context context) {
        this.context = context;
    }

    public String getString(@StringRes int id) {
        return context.getString(id);
    }

    public Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(context, id);
    }

    public int getColor(int id) {
        return ContextCompat.getColor(context, id);
    }

    public String[] getStringArray(int id) {
        return context.getResources().getStringArray(id);
    }

    public Context getContext() {
        return context;
    }
}
