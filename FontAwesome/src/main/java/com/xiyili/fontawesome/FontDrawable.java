package com.xiyili.fontawesome;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.DisplayMetrics;

/**
 * Created by banxi on 14-5-20.
 *
 */
public class FontDrawable extends Drawable {
     private static final int DEFAULT_PAINT_FLAGS = Paint.ANTI_ALIAS_FLAG|Paint.LINEAR_TEXT_FLAG
             |Paint.SUBPIXEL_TEXT_FLAG;

    private  final  Character mChar;
    private int mOpticalSize;
    private int mFullassetSize;
    private int mType = ICON_TYPE_SMALL;
    private float mTargetDensity;
    public static final int ICON_TYPE_SMALL = 1;
    public static final int ICON_TYPE_ACTION_BAR = 2;
    public static final int ICON_TYPE_NOTIFICATION = 3;
    public static Typeface typeface = null;
    private Paint mBgPaint;

    private FontState mFontState = null;

    public FontDrawable(Resources res, Character ch) {
        mChar = ch;
        DisplayMetrics metrics = res.getDisplayMetrics();
        mTargetDensity = metrics.density;
        mFontState = new FontState(ch, ICON_TYPE_SMALL, mTargetDensity);
        if (typeface == null) {
               typeface = Typeface.createFromAsset(res.getAssets(), "FontAwesome.otf");
        }
        mFontState.mPaint.setTypeface(typeface);
        mFontState.mPaint.setTextAlign(Paint.Align.CENTER);
        updateSize();
        adjustFontSize();
//        setBackgroundColor(Color.RED);
    }



    private void updateSize() {
        int fullAssetDip = 16;
        int opticalDip = 12;
        switch (mType) {
            case ICON_TYPE_SMALL:
                fullAssetDip = 16;
                opticalDip = 12;
                break;
            case ICON_TYPE_ACTION_BAR:
                fullAssetDip = 32;
                opticalDip = 24;
                break;
            case ICON_TYPE_NOTIFICATION:
                fullAssetDip = 24;
                opticalDip = 22;
                break;
        }
        mOpticalSize = (int) (opticalDip* mTargetDensity +0.5);
        mFullassetSize = (int) (fullAssetDip * mTargetDensity + 0.5);

        invalidateSelf();
    }

    private void adjustFontSize() {
        float fontSize = 8;
        float width = 0;
        while (width < mOpticalSize) {
            mFontState.mPaint.setTextSize(fontSize);
            width = mFontState.mPaint.measureText(String.valueOf(mChar));
            fontSize++;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mChar == null) {
            return;
        }
        final TextPaint paint = mFontState.mPaint;
        float x = mFullassetSize*0.5f;
        float y = mFullassetSize * 0.5f - ((paint.descent() + paint.ascent()) * 0.5f);
        if (mBgPaint != null) {
            canvas.drawRect(0,0,mFullassetSize,mFullassetSize,mBgPaint);
        }
        canvas.drawText(String.valueOf(mChar),x,y,mFontState.mPaint);
    }

    public FontDrawable setType(int type) {
        mType = type;
        updateSize();
        adjustFontSize();
        return this;
    }

    public FontDrawable setColor(int color) {
        mFontState.mPaint.setColor(color);
        return this;
    }

    public FontDrawable setBackgroundColor(int color) {
        if (mBgPaint == null) {
            mBgPaint = new Paint();
        }
        mBgPaint.setColor(color);
        return this;
    }

    public int getColor() {
        return mFontState.mPaint.getColor();
    }

    @Override
    public boolean getPadding(Rect padding) {
        int px = mFullassetSize - mOpticalSize;
        if (px > 0) {
            padding.set(px, px, px, px);
            return true;
        }
        return false;
    }

    @Override
    public int getIntrinsicWidth() {
        return mFullassetSize;
    }

    @Override
    public int getIntrinsicHeight() {
        return mFullassetSize;
    }

    @Override
    public void setAlpha(int alpha) {
        int oldAlpha = mFontState.mPaint.getAlpha();
        if (oldAlpha != alpha) {
            mFontState.mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public int getAlpha() {
        return mFontState.mPaint.getAlpha();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mFontState.mPaint.setColorFilter(cf);
        invalidateSelf();
    }


    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }



    @Override
    public ConstantState getConstantState() {
        if (mFontState != null) {
            mFontState.mChangingConfiguration = getChangingConfigurations();
        }
        return mFontState;
    }

    final static class FontState extends ConstantState{
        Character mChar;
        int mType;
        float mTargetDensity;
        TextPaint mPaint = new TextPaint(DEFAULT_PAINT_FLAGS);
        int  mChangingConfiguration;



        @Override
        public Drawable newDrawable() {
            return new FontDrawable(this,null);
        }

        @Override
        public Drawable newDrawable(Resources res) {
            return new FontDrawable(res, mChar);
        }

        FontState(Character character, int type, float density) {
            mChar = character;
            mType = type;
            mTargetDensity = density;
        }

        FontState(FontState fontState) {
            mChangingConfiguration = fontState.getChangingConfigurations();
            mType = fontState.mType;
            mChar = fontState.mChar;
            mTargetDensity = fontState.mTargetDensity;
        }

        @Override
        public int getChangingConfigurations() {
            return mChangingConfiguration;
        }
    }

    private FontDrawable(FontState state,Resources res) {
        if (res != null) {
            mTargetDensity = res.getDisplayMetrics().density;
        }else{
            mTargetDensity = state.mTargetDensity;
        }
        mChar = state.mChar;
        setType(state.mType);
    }
}
