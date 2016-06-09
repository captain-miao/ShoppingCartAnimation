package com.github.learn.cart.ui.taobao;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.learn.cart.R;
import com.github.learn.cart.databinding.ActivityCartTaoBaoBinding;
import com.github.learn.cart.ui.base.BaseActivity;
import com.github.learn.cart.ui.helper.AddToCartHelper;
import com.github.learn.cart.ui.helper.CircleTextView;
import com.github.learn.cart.utils.ScreenUtils;


public class AddToCartOfTaoBaoActivity extends BaseActivity implements TaoBaoPresenter {
    private static final String TAG = "AddToCartOfTaoBaoActivi";
    private static final String GOODS_URL = "http://ww1.sinaimg.cn/small/7a8aed7bjw1f2zwrqkmwoj20f00lg0v7.jpg";
    private View mCartOfTop;
    private CircleTextView mCartOfTopAmount;
    private ImageView mGoodsView;
    private ActivityCartTaoBaoBinding mBinding;

    @Override
    public void init(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart_tao_bao);

        mBinding.setGoodsUrl(GOODS_URL);
        mBinding.setPresenter(this);

        mGoodsView = mBinding.ivGoods;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tao_bao_menu, menu);
        final MenuItem item = menu.findItem(R.id.item_action_shopping_cart);
        item.setActionView(R.layout.taobao_cart_of_top);
        mCartOfTop = item.getActionView().findViewById(R.id.iv_shopping_cart_top);
        mCartOfTopAmount = (CircleTextView) item.getActionView().findViewById(R.id.tv_shopping_cart_top_amount);
        return true;
    }




    private void Add2CartAnim( ) {
        /* 起点 */
        int[] startXY = new int[2];
        mBinding.ivGoods.getLocationInWindow(startXY);
        startXY[0] += mBinding.ivGoods.getWidth() / 2;
        //startXY[1] += mBtnAddToCart.getHeight() / 2;
        int fx = startXY[0];
        int fy = startXY[1];
        final ImageView animImg = new ImageView(this);
        Bitmap bm = ((BitmapDrawable) mGoodsView.getDrawable()).getBitmap();
        animImg.setImageBitmap(Bitmap.createScaledBitmap(bm, 96, 96, false));

        ViewGroup anim_mask_layout = AddToCartHelper.createAnimLayout(this);
        anim_mask_layout.addView(animImg);
        final View v = AddToCartHelper.addViewToAnimLayout(this, anim_mask_layout, animImg, startXY, true);
        if (v == null) {
            return;
        }
        /* 终点 */
        final View cartView = mBinding.ivShoppingCart;
        int[] endXY = new int[2];
        cartView.getLocationInWindow(endXY);
        int tx = endXY[0] + cartView.getWidth() / 2 - 48;
        int ty = endXY[1] + cartView.getHeight() / 2;
        /* 中点 */
        int mx = (tx + fx) / 2;
        int my = ScreenUtils.getScreenHeight(this) / 10;
        AddToCartHelper.startAnimation(v, 0, 0, fx, fy, mx, my, tx, ty, new AddToCartHelper.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                mBinding.setAmount(++mAmount);
               //动画结束，做一下别的
                YoYo.with(Techniques.Wave)
                    .duration(500)
                    .playOn(cartView);
            }
        });
    }

    private int mAmount = 0;
    @Override
    public void onAddToCartOfTop() {
        /* 起点 */
        int[] startXY = new int[2];
        mBinding.ivGoods.getLocationInWindow(startXY);
        startXY[0] += mBinding.ivGoods.getWidth() / 2;
        //startXY[1] += mBtnAddToCart.getHeight() / 2;
        int fx = startXY[0];
        int fy = startXY[1];
        final ImageView animImg = new ImageView(this);
        Bitmap bm = ((BitmapDrawable) mGoodsView.getDrawable()).getBitmap();
        animImg.setImageBitmap(Bitmap.createScaledBitmap(bm, 96, 96, false));

        ViewGroup anim_mask_layout = AddToCartHelper.createAnimLayout(this);
        anim_mask_layout.addView(animImg);
        final View v = AddToCartHelper.addViewToAnimLayout(this, anim_mask_layout, animImg, startXY, true);
        if (v == null) {
            return;
        }
        /* 终点 */
        final View cartView = mCartOfTop;
        int[] endXY = new int[2];
        cartView.getLocationInWindow(endXY);
        int tx = endXY[0] + cartView.getWidth() / 2 - 48;
        int ty = endXY[1] + cartView.getHeight() / 2 - 48;
        /* 中点 */
        int mx = (tx + fx) / 2;
        int my = ScreenUtils.getScreenHeight(this) / 10;
        AddToCartHelper.startAnimationForTop(v, 0, 0, fx, fy, mx, my, tx, ty, new AddToCartHelper.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                mCartOfTopAmount.setText(getString(R.string.cart_of_tao_bao_amount, ++mAmount));
               //动画结束，做一下别的
                YoYo.with(Techniques.Wave)
                    .duration(500)
                    .playOn(cartView);
            }
        });

    }

    @Override
    public void onAddToCartOfBottom() {
        Add2CartAnim();
    }
}
