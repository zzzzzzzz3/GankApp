package com.xlf.nrl;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;


public abstract class NrlUtils {

    public static float dipToPx(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }
}
