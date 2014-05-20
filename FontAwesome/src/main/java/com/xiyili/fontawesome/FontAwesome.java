package com.xiyili.fontawesome;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by banxi on 14-5-20.
 *
 */
public class FontAwesome {
    private Resources mRes;
    public FontAwesome(Resources res) {
        mRes =  res;
    }
    public  FontDrawable bold() {
        return new FontDrawable(mRes, '\uf032');
    }

    public  FontDrawable italic() {
        return new FontDrawable(mRes, '\uf033');
    }

    public  FontDrawable textHeight() {
        return new FontDrawable(mRes, '\uf034');
    }
}
