package com.github.learn.cart.ui.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.learn.cart.R;

/**
 * Created by warren on 2/21/16.
 */
public class AddToCartHelper {
    /**
     * @param v          移动对象
     * @param fromXDelta 开始位置X轴偏移
     * @param fromYDelta 开始位置Y轴偏移
     * @param fx         开始移动时 v 的x坐标
     * @param fy         开始移动时 v 的y坐标
     * @param mx         中点 v 的x坐标
     * @param my         中点 v 的y坐标
     * @param tx         移动结束时 v 的x坐标
     * @param ty         移动结束时 v 的y坐标
     */
    public static void startAnimation(final View v, int fromXDelta, int fromYDelta, int fx, int fy, int mx, int my, int tx, int ty, final AnimationListener listener) {
        AnimationSet set = new AnimationSet(false);
        TranslateAnimation translateAnimation1 = new TranslateAnimation(fromXDelta, mx - fx, fromYDelta, my - fy);
        translateAnimation1.setInterpolator(new DecelerateInterpolator());
        translateAnimation1.setRepeatCount(0);
        translateAnimation1.setFillAfter(false);
        set.addAnimation(translateAnimation1);

        TranslateAnimation translateAnimation2 = new TranslateAnimation(fromXDelta, tx - mx, fromYDelta, ty - my);
        translateAnimation2.setInterpolator(new AccelerateInterpolator());
        translateAnimation2.setRepeatCount(0);
        translateAnimation2.setFillAfter(false);
        set.addAnimation(translateAnimation2);

        set.setDuration(700);
        set.setFillAfter(false);
        //v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //v.setLayerType(View.LAYER_TYPE_NONE, null);
                v.setVisibility(View.GONE);
                if (listener != null) {
                    listener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    /**
     * @param v          移动对象
     * @param fromXDelta 开始位置X轴偏移
     * @param fromYDelta 开始位置Y轴偏移
     * @param fx         开始移动时 v 的x坐标
     * @param fy         开始移动时 v 的y坐标
     * @param mx         中点 v 的x坐标
     * @param my         中点 v 的y坐标
     * @param tx         移动结束时 v 的x坐标
     * @param ty         移动结束时 v 的y坐标
     */
    public static void startAnimationForTop(final View v, int fromXDelta, int fromYDelta, int fx, int fy, int mx, int my, int tx, int ty, final AnimationListener listener) {
        AnimationSet set = new AnimationSet(false);
        TranslateAnimation translateAnimation1 = new TranslateAnimation(fromXDelta, mx - fx, fromYDelta, my - fy);
        translateAnimation1.setInterpolator(new OvershootInterpolator());
        translateAnimation1.setRepeatCount(0);
        translateAnimation1.setFillAfter(false);
        set.addAnimation(translateAnimation1);

        TranslateAnimation translateAnimation2 = new TranslateAnimation(fromXDelta, tx - mx, fromYDelta, ty - my);
        translateAnimation2.setInterpolator(new OvershootInterpolator());
        translateAnimation2.setRepeatCount(0);
        translateAnimation2.setFillAfter(false);
        set.addAnimation(translateAnimation2);

        set.setDuration(700);
        set.setFillAfter(false);
        //v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //v.setLayerType(View.LAYER_TYPE_NONE, null);
                v.setVisibility(View.GONE);
                if (listener != null) {
                    listener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    /**
     * @param activity
     * @return
     */
    public static ViewGroup createAnimLayout(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * @param parent
     * @param view
     * @param location
     * @return
     */
    public static View addViewToAnimLayout(Context mContext, final ViewGroup parent, final View view, int[] location, boolean wrap_content) {
        if (view == null) return null;
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams params;
        if (wrap_content) {
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable == null) {
                final int wh = mContext.getResources().getDimensionPixelSize(R.dimen.db_goods_wh);
                params = new LinearLayout.LayoutParams(wh, wh);
            } else {
                params = new LinearLayout.LayoutParams(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
        } else {
            final int wh = mContext.getResources().getDimensionPixelSize(R.dimen.db_goods_wh);
            params = new LinearLayout.LayoutParams(wh, wh);
        }
        params.leftMargin = x;
        params.topMargin = y;
        view.setLayoutParams(params);
        return view;
    }

    public interface AnimationListener {
        /**
         * 处理动画结束后的逻辑，不要涉及动画相关的View
         */
        void onAnimationEnd();
    }
}
