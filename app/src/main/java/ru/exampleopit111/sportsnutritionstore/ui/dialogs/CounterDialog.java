package ru.exampleopit111.sportsnutritionstore.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.R;


/**
 * @author Max on 06.06.2018.
 */
public class CounterDialog extends DialogFragment {
    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_INITIAL_VALUE = "arg_initial_value";
    private static final String ARG_MIN_VALUE = "arg_min_value";
    private static final String ARG_MAX_VALUE = "arg_max_value";
    public static final String TAG = "counter_dialog";

    @BindView(R.id.valueTextView)
    TextView valueTextView;
    @BindView(R.id.minusButton)
    ImageButton minusImageButton;
    @BindView(R.id.plusButton)
    ImageButton plusImageButton;

    private int value;
    private int minValue;
    private int maxValue;

    @Nullable
    private OnValueChangedListener onValueChangedListener;

    public static CounterDialog newInstance(
            @NonNull final String title,
            final int initialValue,
            final int minValue,
            final int maxValue,
            @Nullable final OnValueChangedListener onValueChangedListener
    ) {
        final CounterDialog dialog = new CounterDialog();
        final Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_INITIAL_VALUE, initialValue);
        args.putInt(ARG_MIN_VALUE, minValue);
        args.putInt(ARG_MAX_VALUE, maxValue);
        dialog.setArguments(args);
        dialog.onValueChangedListener = onValueChangedListener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        minValue = getArguments().getInt(ARG_MIN_VALUE);
        maxValue = getArguments().getInt(ARG_MAX_VALUE);

        if (minValue >= maxValue)
            throw new RuntimeException("Min value greater or equals than max value");

        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View contentView = inflater.inflate(R.layout.dialog_counter, null, false);
        ButterKnife.bind(this, contentView);

        final TextView titleTextView = (TextView) inflater.inflate(R.layout.view_dialog_title, null, false);
        titleTextView.setText(getArguments().getString(ARG_TITLE));

        //Если первы запуск, то начальное значение, если уже сохранялось состояние, то сохраненное
        if (savedInstanceState == null) {
            setValue(getArguments().getInt(ARG_INITIAL_VALUE));
        } else {
            setValue(savedInstanceState.getInt(ARG_INITIAL_VALUE));
        }

        minusImageButton.setOnClickListener(v -> setValue(value - 1));
        plusImageButton.setOnClickListener(v -> setValue(value + 1));

        return new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.BaseDialogTheme))
                .setView(contentView)
                .setNegativeButton(getString(R.string.close), null)
                .setCustomTitle(titleTextView)
                .create();
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        //сохраняем состояние для поворота
        outState.putInt(ARG_INITIAL_VALUE, value);
        super.onSaveInstanceState(outState);
    }

    private void setValue(final int value) {
        if (value < minValue || value > maxValue) {
            throw new RuntimeException("value must be in range [" + minValue + ".." + maxValue + "]");
        }
        this.value = value;
        valueTextView.setText(String.valueOf(value));

        minusImageButton.setVisibility(value == minValue ? View.GONE : View.VISIBLE);
        plusImageButton.setVisibility(value == maxValue ? View.GONE : View.VISIBLE);

        if (onValueChangedListener != null) onValueChangedListener.onValueChanged(value);
    }

    public void setOnValueChangedListener(@Nullable final OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    public interface OnValueChangedListener {
        void onValueChanged(final int value);
    }
}
