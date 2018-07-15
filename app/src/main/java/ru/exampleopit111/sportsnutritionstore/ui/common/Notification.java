package ru.exampleopit111.sportsnutritionstore.ui.common;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import ru.exampleopit111.sportsnutritionstore.R;


/**
 * Created by Максим on 19.06.2018.
 */

public class Notification {

    private View view;
    private Context context;

    public Notification(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.setDuration(Constants.TIME);
        setStyleSnackbar(snackbar);
        snackbar.show();
    }

    public void showMessageWithAction(String message, FuncVoid funcVoid) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        setStyleSnackbar(snackbar);
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.red));
        snackbar.setAction(context.getString(R.string.positive_action), v -> {
            funcVoid.action();
        }).show();
    }

    private void setStyleSnackbar(Snackbar snackbar) {
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbarBackgroundColor));
        TextView snackTextView = (TextView)
                snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        snackTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackTextView.setTextSize(context.getResources().getDimension(R.dimen.textSnackBar));
    }
}
