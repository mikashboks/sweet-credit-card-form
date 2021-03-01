package com.braintreepayments.cardform.view;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.util.AttributeSet;

import com.braintreepayments.cardform.R;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * Input for mobile number. Validated for presence only due to the wide variation of mobile number formats worldwide.
 */
public class PhoneNumberEditText extends ErrorEditText {

    public PhoneNumberEditText(Context context) {
        super(context);
        init();
    }

    public PhoneNumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhoneNumberEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }
    }

    /**
     * @return the unformatted mobile number entered by the user
     */
    public String getPhoneNumber() {
        return getText().toString();
    }

    @Override
    public boolean isValid() {
        return isOptional() || getText().toString().length() >= 8;
    }

    public boolean isPhoneNumberValid(String countryCode) {
        try {
            // we except country code so setting defaultregion to null
            PhoneNumberUtil instance = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber numberProto = PhoneNumberUtil.getInstance().parse(countryCode + getText().toString(), null);
            return instance.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            return false;
        }
    }

    @Override
    public String getErrorMessage() {
        if (getText().toString().isEmpty()) {
            return getContext().getString(R.string.bt_mobile_number_required);
        } else {
            return getContext().getString(R.string.bt_enter_valid_phone_number);
        }
    }
}
