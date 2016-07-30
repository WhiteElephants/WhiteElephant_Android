package rawe.gordon.com.business.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import rawe.gordon.com.business.application.SharedParameter;

/**
 * Created by gordon on 16/5/7.
 */
public class AnimatorUtil {
    public static Animator getToolbarDropDownAnimator(View view) {
        Animator translate = ObjectAnimator.ofFloat(view, "translationY", 0);
        translate.setDuration(300);
        return translate;
    }

    public static Animator getDropDownAnimator(View view) {
        Animator translate = ObjectAnimator.ofFloat(view, "translationY", 0);
        translate.setDuration(400);
        translate.setInterpolator(new OvershootInterpolator(.5F));
        return translate;
    }

    public static Animator getDropDownOutAnimator(View view) {
        Animator translate = ObjectAnimator.ofFloat(view, "translationY", SharedParameter.getInstance().getScreenHeight());
        translate.setDuration(300);
        return translate;
    }

    public static Animator getAlphaInAnimator(View view) {
        Animator animator = ObjectAnimator.ofFloat(view, "alpha", 1F);
        animator.setDuration(150);
        return animator;
    }

    public static Animator getAlphaOutAnimator(View view) {
        Animator animator = ObjectAnimator.ofFloat(view, "alpha", 0F);
        animator.setDuration(150);
        return animator;
    }

    public static void playLeftRightBlinkAnimation(View view) {
        AnimatorSet blink = new AnimatorSet();
        ObjectAnimator left = ObjectAnimator.ofFloat(view, "translationX", -10f);
        ObjectAnimator right = ObjectAnimator.ofFloat(view, "translationX", 30f);
        ObjectAnimator resume = ObjectAnimator.ofFloat(view, "translationX", 0f);
        blink.playSequentially(left, right, resume);
        blink.setDuration(300);
        blink.setInterpolator(new OvershootInterpolator(3f));
        blink.start();
    }
}