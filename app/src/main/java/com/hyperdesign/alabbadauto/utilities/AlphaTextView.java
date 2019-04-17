package com.hyperdesign.alabbadauto.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Hyper Design Hussien on 2/14/2018.
 */

@SuppressLint("AppCompatCustomView")
public class AlphaTextView extends TextView {

    public AlphaTextView(Context context) {
        super(context);
    }

    public AlphaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onSetAlpha(int alpha) {
        setTextColor(getTextColors().withAlpha(alpha));
        setHintTextColor(getHintTextColors().withAlpha(alpha));
        setLinkTextColor(getLinkTextColors().withAlpha(alpha));
        return true;
    }
}
