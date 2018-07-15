package ru.exampleopit111.sportsnutritionstore.ui.settings;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.ui.common.Constants;
import ru.exampleopit111.sportsnutritionstore.ui.common.SetFunc;

/**
 * Created Максим on 08.07.2018.
 * Copyright © Max
 */
public class ChangeThemeDialog {
    @BindView(R.id.darkThemeCheckBox)
    CheckBox darkThemeCheckBox;
    @BindView(R.id.ligtThemeCheckBox)
    CheckBox ligtThemeCheckBox;

    private Context context;
    private String theme_status;

    public ChangeThemeDialog(Context context) {
        this.context = context;
    }

    public void showDialog(SetFunc<String> func, String theme) {
        View view = View.inflate(context, R.layout.dialog_theme, null);
        AlertDialog.Builder establishBuilder =
                new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.BaseDialogTheme));
        establishBuilder
                .setTitle(context.getString(R.string.select_app_theme))
                .setView(view)
                .setCancelable(false);
        TextView titleTextView = (TextView) LayoutInflater.from(context)
                .inflate(R.layout.view_dialog_title, null, false);
        titleTextView.setText(context.getString(R.string.select_app_theme));
        establishBuilder.setNegativeButton(context.getString(R.string.abolish), (dialog, which) -> {
            dialog.cancel();
        });

        establishBuilder.setPositiveButton(context.getString(R.string.ok_action), (dialog, which) -> {
            func.transferResult(theme_status);
        });
        establishBuilder.setCustomTitle(titleTextView);
        AlertDialog alert = establishBuilder.create();
        alert.show();
        initComponents(theme, view);
    }

    private void initComponents(String theme, View view) {
        ButterKnife.bind(this, view);

        switch (theme) {
            case Constants.DARK_THEME:
                theme_status = Constants.DARK_THEME;
                darkThemeCheckBox.setChecked(true);
                break;
            case Constants.LIGHT_THEME:
                theme_status = Constants.LIGHT_THEME;
                ligtThemeCheckBox.setChecked(true);
                break;
        }

        darkThemeCheckBox.setOnCheckedChangeListener((viewBtn, isChecked) -> {
                    if (isChecked) {
                        theme_status = Constants.DARK_THEME;
                        ligtThemeCheckBox.setChecked(false);
                    }
                }
        );

        ligtThemeCheckBox.setOnCheckedChangeListener((viewBtn, isChecked) -> {
                    if (isChecked) {
                        theme_status = Constants.LIGHT_THEME;
                        darkThemeCheckBox.setChecked(false);
                    }
                }
        );

    }
}
