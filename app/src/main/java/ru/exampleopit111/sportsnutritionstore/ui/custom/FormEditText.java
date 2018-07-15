package ru.exampleopit111.sportsnutritionstore.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.Observable;
import ru.exampleopit111.sportsnutritionstore.R;
import ru.exampleopit111.sportsnutritionstore.ui.common.SimpleTextChangedListener;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.PhoneNumberUnderscoreSlotsParser;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class FormEditText extends AppCompatEditText {
    private static final int PHONE_INPUT_TYPE = 0;
    private static final int CODE_INPUT_TYPE = 1;
    private static final int CARD_INPUT_TYPE = 2;


    private static final String PHONE_PATTERN = "+7 ___ ___ __ __";
    private static final String CODE_PATTERN = "___";
    private static final String CARD_PATTERN = "________________";


    private final Slot[] PHONE_SLOTS = new PhoneNumberUnderscoreSlotsParser().parseSlots(PHONE_PATTERN);
    private final Slot[] CODE_SLOTS = new UnderscoreDigitSlotsParser().parseSlots(CODE_PATTERN);
    private final Slot[] CARD_SLOTS = new UnderscoreDigitSlotsParser().parseSlots(CARD_PATTERN);


    private int type;
    private CharSequence hint;

    private BehaviorRelay<Boolean> validationRelay = BehaviorRelay.create();
    private BehaviorRelay<Boolean> errorStateRelay = BehaviorRelay.create();

    private FormatWatcher formatWatcher;

    public FormEditText(final Context context) {
        super(context);
        init();
    }

    public FormEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FormEditText);
        try {
            if (!array.hasValue(R.styleable.FormEditText_cetType)) {
                throw new RuntimeException("No attribute passed for parameter cetType");
            }
            this.type = array.getInt(R.styleable.FormEditText_cetType, 0);
            this.hint = getHint();
        } finally {
            array.recycle();
        }

        init();
    }

    private void init() {
        validationRelay.accept(false);
        setErrorEnabled(false);

        if (!isEnabled()) {
            setHint("");
        }

        final Slot[] slots;
        final int maskedLength;
        switch (type) {
            case PHONE_INPUT_TYPE:
                setHint("");
                append(hint);
                slots = PHONE_SLOTS;
                maskedLength = PHONE_PATTERN.length();
                break;
            case CODE_INPUT_TYPE:
                slots = CODE_SLOTS;
                maskedLength = CODE_PATTERN.length();
                break;

            case CARD_INPUT_TYPE:
                slots = CARD_SLOTS;
                maskedLength = CARD_PATTERN.length();
                break;

            default:
                throw new RuntimeException("Type undefined: " + type);
        }

        formatWatcher = new MaskFormatWatcher(MaskImpl.createTerminated(slots));
        formatWatcher.installOn(this);

        this.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (actionId == EditorInfo.IME_ACTION_DONE && getText().toString().length() == maskedLength) {
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                    errorStateRelay.accept(getText().toString().length() < maskedLength
                            || getText().toString().isEmpty());

                    if (errorStateRelay.getValue()) {
                        enableError();
                    }
                    handled = true;
                }
                return handled;
            }
        });

        this.addTextChangedListener(new SimpleTextChangedListener() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validationRelay.accept(getText().toString().length() == maskedLength);

                if (validationRelay.getValue()) {
                    setErrorEnabled(false);
                    final Drawable background = ContextCompat.getDrawable(getContext(),
                            R.drawable.bg_form_edit_text);
                    setBackground(background);
                }

//                if (errorStateRelay.getValue()) {
//                    enableError();
//
//                }
            }
        });
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        setHint(enabled ? hint : "");
    }

    public Observable<Boolean> observeValid() {
        return validationRelay;
    }

    public Observable<Boolean> observeErrorState() {
        return errorStateRelay;
    }

    public String getUnformattedText() {
        return formatWatcher.getMask().toUnformattedString();
    }

    public void enableError() {
        setErrorEnabled(true);
        final Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.bg_form_edit_text_error);
        setBackground(background);
    }

    private void setErrorEnabled(final boolean enabled) {
        errorStateRelay.accept(enabled);
    }
}
