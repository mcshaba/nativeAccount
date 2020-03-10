package com.example.zexplore.helper;

import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */

public class Animation {


    /** Helper function to strike-through button text when a module is complete **/

    public static void animateStrikeThrough1(final MaterialButton button) {

        final int ANIM_DURATION = 1000;              //duration of animation in millis
        final int length = button.getText().length();
        new CountDownTimer(ANIM_DURATION, ANIM_DURATION/length) {
            Spannable span = new SpannableString(button.getText());
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

            @Override
            public void onTick(long millisUntilFinished) {
                //calculate end position of strikethrough in textview
                int endPosition = (int) (((millisUntilFinished-ANIM_DURATION)*-1)/(ANIM_DURATION/length));
                endPosition = endPosition > length ?
                        length : endPosition;
                span.setSpan(strikethroughSpan, 0, endPosition,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                button.setText(span);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }


    public static void animateRemoveStrike(final MaterialButton button) {

        final int ANIM_DURATION = 1000;              //duration of animation in millis
        final int length = button.getText().length();
        new CountDownTimer(ANIM_DURATION, ANIM_DURATION/length) {
            Spannable span = new SpannableString(button.getText());
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

            @Override
            public void onTick(long millisUntilFinished) {
                //calculate end position of strikethrough in textview
                int endPosition = (int) (((millisUntilFinished-ANIM_DURATION)*-1)/(ANIM_DURATION/length));
                endPosition = endPosition > length ?
                        length : endPosition;
                span.setSpan(strikethroughSpan, 0, endPosition,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                button.setText(span);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

}
