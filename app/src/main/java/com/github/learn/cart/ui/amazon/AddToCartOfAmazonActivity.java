package com.github.learn.cart.ui.amazon;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.daimajia.androidanimations.library.YoYo;
import com.github.learn.cart.R;
import com.github.learn.cart.databinding.ActivityCartAmazonBinding;
import com.github.learn.cart.ui.base.BaseActivity;
import com.github.learn.cart.ui.helper.AddToCartHelper;
import com.github.learn.cart.ui.helper.CircleTextView;
import com.github.learn.cart.utils.ScreenUtils;
import com.nineoldandroids.animation.ObjectAnimator;


public class AddToCartOfAmazonActivity extends BaseActivity implements AmazonPresenter {
    private static final String TAG = "AddToCartOfAmazonActivity";
    private static final String GOODS_URL = "http://ww1.sinaimg.cn/small/7a8aed7bjw1f2zwrqkmwoj20f00lg0v7.jpg";
    private View mCartOfTop;
    private TextView mCartOfTopAmount;
    private ActivityCartAmazonBinding mBinding;

    @Override
    public void init(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart_amazon);

        mBinding.setGoodsUrl(GOODS_URL);
        mBinding.setPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tao_bao_menu, menu);
        final MenuItem item = menu.findItem(R.id.item_action_shopping_cart);
        item.setActionView(R.layout.amazon_cart_of_top);
        mCartOfTop = item.getActionView();
        mCartOfTopAmount = (TextView) item.getActionView().findViewById(R.id.tv_shopping_cart_top_amount);
        return true;
    }


    private int mAmount = 0;
    @Override
    public void onAddToCartOfTop() {
        final TextView animText = new TextView(this);
        animText.setTextColor(getResources().getColor(R.color.tao_bao_red));
        animText.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        animText.setText(getString(R.string.cart_of_amazon_amount, mAmount + 1));

        /* 终点 */
        final View cartView = mCartOfTop;
        int[] endXY = new int[2];
        cartView.getLocationInWindow(endXY);
        int tx = endXY[0] + cartView.getWidth() / 2 - (int)(CircleTextView.getTextHeight(animText.getPaint(), animText.getText().toString())/2 + 0.5f);
        int ty = endXY[1] + cartView.getHeight() / 2 - (int)(CircleTextView.getTextHeight(animText.getPaint(), animText.getText().toString())/2 + 0.5f);



        /* 起点 */
        int[] startXY = new int[2];
        startXY[0] = tx;
        startXY[1] = ty - 300;
        //mBinding.ivGoods.getLocationInWindow(startXY);
        //startXY[0] += mBinding.ivGoods.getWidth() / 2;
        //startXY[1] += mBtnAddToCart.getHeight() / 2;
        int fx = startXY[0];
        int fy = startXY[1];

        //Bitmap bm = ((BitmapDrawable) mGoodsView.getDrawable()).getBitmap();
        //animImg.setImageBitmap(Bitmap.createScaledBitmap(bm, 96, 96, false));

        ViewGroup anim_mask_layout = AddToCartHelper.createAnimLayout(this);
        anim_mask_layout.addView(animText);
        final View v = AddToCartHelper.addViewToAnimLayout(this, anim_mask_layout, animText, startXY, true);
        if (v == null) {
            return;
        }
        /* 终点 */
        //final View cartView = mCartOfTop;
        //int[] endXY = new int[2];
        //cartView.getLocationInWindow(endXY);
        //int tx = endXY[0] + cartView.getWidth() / 2 - (int)(CircleTextView.getTextWidth(animText.getPaint(), animText.getText().toString()) + 0.5f);
        //int ty = endXY[1] + cartView.getHeight() / 2 - (int)CircleTextView.getTextHeight(animText.getPaint(), animText.getText().toString() + 0.5f);

        /* 中点 */
        int mx = (tx + fx) / 2;
        int my = ScreenUtils.getScreenHeight(this) / 10;
        AddToCartHelper.startAnimationForAmazon(v, 0, 0, fx, fy, mx, my, tx, ty, new AddToCartHelper.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                mCartOfTopAmount.setText(getString(R.string.cart_of_amazon_amount, ++mAmount));
               //动画结束，做一下别的
                //YoYo.with(Techniques.Wave).duration(500).playOn(cartView);
            }
        });

        YoYo.with(new ScaleUpAnimator()).duration(1000).playOn(cartView);


    }


    public class ScaleUpAnimator extends BaseViewAnimator {

        @Override
        public void prepare(View target) {
            getAnimatorAgent().playTogether(
                    //ObjectAnimator.ofFloat(target,"alpha",0,1,1),
                    ObjectAnimator.ofFloat(target,"scaleX",1.3f,1.3f,1.3f,1.3f,1.3f,1.3f,1.3f,1.3f,1),
                    ObjectAnimator.ofFloat(target,"scaleY",1.3f,1.3f,1.3f,1.3f,1.3f,1.3f,1.3f,1.3f,1)
            );
        }
    }
}
