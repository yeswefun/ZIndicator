package com.z.zindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/*
    文字指示器
 */
public class Vtext extends AppCompatTextView {

    private final Rect mTextBounds;
    private final Paint mFontPaint;
    private final Paint mActiveFontPaint;
    private final RectF mClipRect;
    private float mCurPos = 0f;
    // true for left-to-right, false for right-to-left
    private boolean mDirection = true;

    public Vtext(Context context) {
        this(context, null);
    }

    public Vtext(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Vtext(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Vtext);
        int fontColor = typedArray.getColor(R.styleable.Vtext_fontColor, getTextColors().getDefaultColor());
        int activeFontColor = typedArray.getColor(R.styleable.Vtext_activeFontColor, getTextColors().getDefaultColor());
        typedArray.recycle();

        mFontPaint = getPaintByColor(fontColor);
        mActiveFontPaint = getPaintByColor(activeFontColor);
        mTextBounds = new Rect();
        mClipRect = new RectF();
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setTextSize(getTextSize());
        paint.setDither(true);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int boundary = (int) (getWidth() * mCurPos);
        if (mDirection) {// left to right
            drawText(canvas, mActiveFontPaint, 0, boundary);
            drawText(canvas, mFontPaint, boundary, getWidth());
        } else {//right to left
            drawText(canvas, mFontPaint, 0, getWidth() - boundary);
            drawText(canvas, mActiveFontPaint, getWidth() - boundary, getWidth());
        }
    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {

        String text = getText().toString();
        mFontPaint.getTextBounds(text, 0, text.length(), mTextBounds);
        int dx = (getWidth() - mTextBounds.width()) / 2;

        Paint.FontMetricsInt fontMetricsInt = mFontPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;

        canvas.save();
        mClipRect.set(start, 0, end, getHeight());
        canvas.clipRect(mClipRect);
        canvas.drawText(text, dx, baseline, paint);
        canvas.restore();
    }

    public void setCurPos(float mCurPos) {
        this.mCurPos = mCurPos;
        invalidate();
    }

    public void setDirection(boolean mDirection) {
        this.mDirection = mDirection;
    }

    public void setActiveFontColor(int color) {
        mActiveFontPaint.setColor(color);
    }

/*
    @Override
    protected void onDraw(Canvas canvas) {

        String text = getText().toString();
        mCurPos = (int) (getWidth() * 0.5);

        canvas.save();
        mFontPaint.getTextBounds(text, 0, text.length(), mTextBounds);
        int dx = (getWidth() - mTextBounds.width()) / 2;

        Paint.FontMetricsInt fontMetricsInt = mFontPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;

        mClipRect.set(0, 0, mCurPos, getHeight());
        canvas.clipRect(mClipRect);
        canvas.drawText(text, dx, baseline, mFontPaint);
        canvas.restore();

        canvas.save();
        mClipRect.set(mCurPos, 0, getWidth(), getHeight());
        canvas.clipRect(mClipRect);
        canvas.drawText(text, dx, baseline, mActiveFontPaint);
        canvas.restore();
    }
*/
}
