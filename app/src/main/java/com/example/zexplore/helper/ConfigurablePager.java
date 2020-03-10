package com.example.zexplore.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */
public class ConfigurablePager extends ViewPager {

    private final AtomicBoolean touchesAllowed = new AtomicBoolean();

    public ConfigurablePager(@NonNull Context context) {
        super(context);
    }

    public ConfigurablePager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }
    private boolean touchesAllowed() {
        return touchesAllowed.get();
    }

    public void enableTouches() {
        touchesAllowed.set(true);
    }

    public void disableTouches() {
        touchesAllowed.set(false);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (touchesAllowed()) {
            return super.onInterceptTouchEvent(ev);
        } else {
            if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
                // ignore move action
            } else {
                if (super.onInterceptTouchEvent(ev)) {
                    super.onTouchEvent(ev);
                }
            }
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (touchesAllowed()) {
            return super.onTouchEvent(ev);
        } else {
            return MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev);
        }
    }
}