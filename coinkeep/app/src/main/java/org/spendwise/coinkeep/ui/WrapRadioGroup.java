package org.spendwise.coinkeep.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

public class WrapRadioGroup extends RadioGroup {

    private int mMaxWidth;

    public WrapRadioGroup(Context context) {
        super(context);
    }

    public WrapRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMaxWidth(int maxWidth) {
        mMaxWidth = maxWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = mMaxWidth > 0 ? mMaxWidth : getMeasuredWidth();
        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int totalHeight = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (lineWidth + childWidth > maxWidth) {
                totalHeight += lineHeight;
                lineWidth = 0;
                lineHeight = 0;
            }
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
        }

        totalHeight += lineHeight;
        setMeasuredDimension(maxWidth, totalHeight);
    }
}

