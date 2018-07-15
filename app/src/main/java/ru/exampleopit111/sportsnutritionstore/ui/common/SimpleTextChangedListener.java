package ru.exampleopit111.sportsnutritionstore.ui.common;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class SimpleTextChangedListener implements TextWatcher {

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {

    }
}
