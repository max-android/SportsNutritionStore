package ru.exampleopit111.sportsnutritionstore.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created Максим on 06.06.2018.
 * Copyright © Max
 */
public abstract class BaseFragment extends Fragment {
    @LayoutRes
    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    protected void showSnackMessage(@NonNull final String text) {
        if (getView() != null) {
            Snackbar.make(getView(), text, Snackbar.LENGTH_SHORT).show();
        }
    }
}
