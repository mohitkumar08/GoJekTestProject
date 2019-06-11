package com.bit.gojektestproject.util;

import android.content.Context;
import android.graphics.Typeface;

public class AppTypeFace {
    private static AppTypeFace instance;
    private Typeface mRobotoBlackTypeface;
    private Typeface mRobotoRegularTypeface;
    private Typeface mRobotoThinTypeface;private Context context;

    private AppTypeFace(Context context) {
        this.context = context;
    }

    public static AppTypeFace getInstance(Context context) {
        if (instance == null) {
            instance = new AppTypeFace(context);
        }
        return instance;
    }

    public Typeface getRobotoBlackTypeface() {
        if (mRobotoBlackTypeface == null)
            mRobotoBlackTypeface = Typeface.createFromAsset(
                    context.getAssets(), "fonts/Roboto-Black.ttf");
        return mRobotoBlackTypeface;
    }


    public Typeface getRobotoRegularTypeface() {
        if (mRobotoRegularTypeface == null)
            mRobotoRegularTypeface = Typeface.createFromAsset(
                    context.getAssets(), "fonts/Roboto-Regular.ttf");
        return mRobotoRegularTypeface;
    }

    public Typeface getRobotoThinTypeFace() {
        if (mRobotoThinTypeface == null)
            mRobotoThinTypeface = Typeface.createFromAsset(context.getAssets(),
                    "fonts/Roboto-Thin.ttf");
        return mRobotoThinTypeface;
    }

}
