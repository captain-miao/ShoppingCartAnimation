package com.github.learn.cart.ui.simple;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.learn.cart.R;
import com.github.learn.cart.databinding.ActivityCartSimpleBinding;
import com.github.learn.cart.ui.base.BaseActivity;
import com.github.learn.cart.ui.helper.AddToCartHelper;
import com.github.learn.cart.ui.helper.CircleTextView;
import com.github.learn.cart.utils.ScreenUtils;


public class AddToCartOfSimpleActivity extends BaseActivity implements SimplePresenter {
    private static final String TAG = "AddToCartOfSimpleActivity";
    private static final String GOODS_URL = "http://ww1.sinaimg.cn/small/7a8aed7bjw1f2zwrqkmwoj20f00lg0v7.jpg";
    private TextView mCartOfTopAmount;
    private ActivityCartSimpleBinding mBinding;
    private Button mBtnAddToCartOfBottom;

    @Override
    public void init(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart_simple);

        mBinding.setGoodsUrl(GOODS_URL);
        mBinding.setPresenter(this);
        mBtnAddToCartOfBottom = mBinding.btnAddShoppingCartBottom;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tao_bao_menu, menu);
        final MenuItem item = menu.findItem(R.id.item_action_shopping_cart);
        item.setActionView(R.layout.simple_cart_of_top);
        mCartOfTopAmount = (TextView) item.getActionView().findViewById(R.id.tv_shopping_cart_top_amount);
        return true;
    }


    private int mAmount = 0;
    @Override
    public void onAddToCartOfTop() {
        LinearLayout animText = new LinearLayout(this);

        animText.setGravity(Gravity.CENTER);
        final CircleTextView text = new CircleTextView(this);
        text.setTextColor(getResources().getColor(android.R.color.white));
        text.setBackgroundResource(R.drawable.homepage_shape_red_filled_circle);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        text.setGravity(Gravity.CENTER);
        text.setText(getString(R.string.cart_of_amazon_amount, mAmount + 1));
        animText.addView(text, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));



        /* 终点 */
        final View cartView = mCartOfTopAmount;
        int[] endXY = new int[2];
        cartView.getLocationInWindow(endXY);
        int tx = endXY[0];
        int ty = endXY[1];



        /* 起点 */
        int[] startXY = new int[2];
        mBtnAddToCartOfBottom.getLocationInWindow(startXY);
        startXY[0] += mBtnAddToCartOfBottom.getWidth() / 2;
        //startXY[0] = tx;
        //startXY[1] = ty - 300;

        int fx = startXY[0];
        int fy = startXY[1];


        ViewGroup anim_mask_layout = AddToCartHelper.createAnimLayout(this);
        anim_mask_layout.addView(animText);
        final View v = AddToCartHelper.addViewToAnimLayout(this, anim_mask_layout, animText, startXY, true);
        if (v == null) {
            return;
        }

        /* 中点 */
        int mx = (tx + fx);
        int my = ScreenUtils.getScreenHeight(this) / 10;
        AddToCartHelper.startAnimationForTop(v, 0, 0, fx, fy, mx, my, tx, ty, new AddToCartHelper.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                mCartOfTopAmount.setText(getString(R.string.cart_of_amazon_amount, ++mAmount));
               //动画结束，做一下别的
                //YoYo.with(Techniques.Wave).duration(500).playOn(cartView);
            }
        });

    }
}
