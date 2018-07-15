package ru.exampleopit111.sportsnutritionstore.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exampleopit111.sportsnutritionstore.R;


/**
 * Created Максим on 03.07.2018.
 * Copyright © Max
 */
public class CustomDialog {
    private @NonNull
    final Context context;
    private @NonNull
    final String title;
    private @NonNull
    final String message;
    private @NonNull
    final String positiveTitle;
    private @NonNull
    final String negativeTitle;
    private final boolean dismiss;
    private @NonNull
    final MessageDialogFragment.OnPositiveButtonListener positiveListener;
    private @NonNull
    final MessageDialogFragment.OnNegativeButtonListener negativeListener;

    private CustomDialog(DialogBuilder builder) {
        this.context = builder.context;
        this.title = builder.title;
        this.message = builder.message;
        this.positiveTitle = builder.positiveTitle;
        this.negativeTitle = builder.negativeTitle;
        this.dismiss = builder.dismiss;
        this.positiveListener = builder.positiveListener;
        this.negativeListener = builder.negativeListener;
    }

    public static class MessageDialogFragment extends DialogFragment {
        private static final String ARG_TITLE = "dialog_title";
        private static final String ARG_MESSAGE = "dialog_message";
        private static final String ARG_POSITIVE = "positive";
        private static final String ARG_NEGATIVE = "negative";
        private static final String ARG_DISMISS = "dismiss";

        @BindView(R.id.messageTextView)
        TextView messageTextView;
        @BindView(R.id.positiveTextView)
        TextView positiveTextView;
        @BindView(R.id.negativeTextView)
        TextView negativeTextView;
        @Nullable
        public MessageDialogFragment.OnPositiveButtonListener positiveListener;
        @Nullable
        public MessageDialogFragment.OnNegativeButtonListener negativeListener;

        public static MessageDialogFragment newInstance(
                @NonNull final String title,
                @NonNull final String message,
                @NonNull final String positiveTitle,
                @NonNull final String negativeTitle,
                final boolean dismiss,
                @Nullable final MessageDialogFragment.OnPositiveButtonListener positiveListener,
                @Nullable final MessageDialogFragment.OnNegativeButtonListener negativeListener
        ) {
            final MessageDialogFragment dialog = new MessageDialogFragment();
            dialog.setCancelable(false);
            final Bundle args = new Bundle();
            args.putString(ARG_TITLE, title);
            args.putString(ARG_MESSAGE, message);
            args.putString(ARG_POSITIVE, positiveTitle);
            args.putString(ARG_NEGATIVE, negativeTitle);
            args.putBoolean(ARG_DISMISS, dismiss);
            dialog.setArguments(args);
            dialog.positiveListener = positiveListener;
            dialog.negativeListener = negativeListener;
            return dialog;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            final View contentView = inflater.inflate(R.layout.dialog_message, null, false);
            ButterKnife.bind(this, contentView);

            messageTextView.setText(getArguments().getString(ARG_MESSAGE));
            final TextView titleTextView = (TextView) inflater.inflate(R.layout.view_dialog_title, null, false);
            titleTextView.setText(getArguments().getString(ARG_TITLE));

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.BaseDialogTheme));
            builder.setView(contentView);

            if (getArguments().getString(ARG_POSITIVE) != null) {
                positiveTextView.setVisibility(View.VISIBLE);
                positiveTextView.setText(getArguments().getString(ARG_POSITIVE));
                positiveTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.dialogMessagePositiveTextColor));
                positiveTextView.setOnClickListener(v -> {
                    positiveListener.onPositiveAction();
                    if (getArguments().getBoolean(ARG_DISMISS)) {
                        dismiss();
                    }
                });
            }

            if (getArguments().getString(ARG_NEGATIVE) != null) {
                negativeTextView.setVisibility(View.VISIBLE);
                negativeTextView.setText(getArguments().getString(ARG_NEGATIVE));
                negativeTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.dialogMessageNegativeTextColor));
                negativeTextView.setOnClickListener(v -> {
                    negativeListener.onNegativeAction();
                    dismiss();
                });
            }

            builder.setCustomTitle(titleTextView);
            return builder.create();
        }

        public void setOnChangedPositiveListener(@Nullable final MessageDialogFragment.OnPositiveButtonListener positiveListener) {
            this.positiveListener = positiveListener;
        }

        public void setOnChangedNegativeListener(@Nullable final MessageDialogFragment.OnNegativeButtonListener negativeListener) {
            this.negativeListener = negativeListener;
        }

        public interface OnPositiveButtonListener {
            void onPositiveAction();
        }

        public interface OnNegativeButtonListener {
            void onNegativeAction();
        }
    }

    public static class DialogBuilder {
        private Context context;
        private String title;
        private String message;
        private String positiveTitle;
        private String negativeTitle;
        private boolean dismiss;
        private MessageDialogFragment.OnPositiveButtonListener positiveListener;
        private MessageDialogFragment.OnNegativeButtonListener negativeListener;
        private MessageDialogFragment dialog;

        @Nullable
        public final DialogBuilder from(Context context) {
            this.context = context;
            return this;
        }

        @Nullable
        public final DialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        @Nullable
        public final DialogBuilder title(int titleId) {
            this.title = context.getString(titleId);
            return this;
        }

        @Nullable
        public final DialogBuilder message(String message) {
            this.message = message;
            return this;
        }

        @Nullable
        public final DialogBuilder message(int messageId) {
            this.message = context.getString(messageId);
            return this;
        }

        @Nullable
        public final DialogBuilder positiveButton(String positiveTitle, MessageDialogFragment.OnPositiveButtonListener positiveListener, boolean dismiss) {
            this.positiveTitle = positiveTitle;
            this.positiveListener = positiveListener;
            this.dismiss = dismiss;
            return this;
        }

        @Nullable
        public final DialogBuilder negativeButton(String negativeTitle, MessageDialogFragment.OnNegativeButtonListener negativeListener) {
            this.negativeTitle = negativeTitle;
            this.negativeListener = negativeListener;
            return this;
        }

        @Nullable
        public final DialogBuilder create() {
            dialog = MessageDialogFragment.newInstance(
                    title,
                    message,
                    positiveTitle,
                    negativeTitle,
                    dismiss,
                    positiveListener,
                    negativeListener);
            return this;
        }

        @Nullable
        public final CustomDialog show(FragmentManager fragmentManager, String tag) {
            dialog.show(fragmentManager, tag);
            return new CustomDialog(this);
        }
    }
}

