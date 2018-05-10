package com.example.androidsix.rvadimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by 老头儿和老婆儿 on 2018/5/10.
 */

public class AdImageView extends AppCompatImageView {
    private static final String TAG = "AdImageView";

    public AdImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int mDy;
    private int mMinDy;

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        mMinDy = getHeight();
    }

    /**
     * 设置画布移动的距离
     * @param dy
     */
    public void setDy(int dy) {
        if (getDrawable() == null) {
            return;
        }
        mDy = dy;
        //检查画布移动的距离是否超过了图片的高度
        if (mDy > getDrawable().getBounds().height() - mMinDy) {
            mDy = getDrawable().getBounds().height() - mMinDy;
        }
        invalidate();
    }

    public int getDy() {
        return mDy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, -getDy());// 移动画布，负数是向下移动。
        super.onDraw(canvas);
        canvas.restore();
    }
}
