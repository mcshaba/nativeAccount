package com.example.zexplore.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.example.zexplore.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import io.reactivex.annotations.Nullable;

public class NotificationDrawable extends Drawable {

    private Paint mNotificationPaint;
    private Paint mNotificationPaint1;
    private Paint mTextPaint;
    private Rect mTxtRect = new Rect();

    private String mCount = "";
    private boolean mWillDraw;

    public NotificationDrawable(Context context) {
        float mTextSize = context.getResources().getDimension(R.dimen.notification_text_size);

        mNotificationPaint = new Paint();
        mNotificationPaint.setColor(Color.RED);
        mNotificationPaint.setAntiAlias(true);
        mNotificationPaint.setStyle(Paint.Style.FILL);
        mNotificationPaint1 = new Paint();
        mNotificationPaint1.setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorPrimaryDark));
        mNotificationPaint1.setAntiAlias(true);
        mNotificationPaint1.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(Typeface.DEFAULT);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (!mWillDraw) {
            return;
        }
        Rect bounds = getBounds();
        float width = bounds.right - bounds.left;
        float height = bounds.bottom - bounds.top;

        // Position the badge in the top-right quadrant of the icon.

        /*Using Math.max rather than Math.min */

        float radius = ((Math.max(width, height) / 2)) / 2;
        float centerX = (width - radius - 1) +5;
        float centerY = radius -5;
        if(mCount.length() <= 2){
            // Draw badge circle.
            canvas.drawCircle(centerX, centerY, (int)(radius+7.5), mNotificationPaint1);
            canvas.drawCircle(centerX, centerY, (int)(radius+5.5), mNotificationPaint);
        }
        else{
            canvas.drawCircle(centerX, centerY, (int)(radius+8.5), mNotificationPaint1);
            canvas.drawCircle(centerX, centerY, (int)(radius+6.5), mNotificationPaint);
            //canvas.drawRoundRect(radius, radius, radius, radius, 10, 10, mBadgePaint);
        }

        // Draw badge count text inside the circle.
        mTextPaint.getTextBounds(mCount, 0, mCount.length(), mTxtRect);
        float textHeight = mTxtRect.bottom - mTxtRect.top;
        float textY = centerY + (textHeight / 2f);
        if(mCount.length() > 2)
            canvas.drawText("99+", centerX, textY, mTextPaint);
        else
            canvas.drawText(mCount, centerX, textY, mTextPaint);
    }

    public void setCount(String count) {
        mCount = count;

        // Only draw a badge if there are notifications.
        mWillDraw = !count.equalsIgnoreCase("0");
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
