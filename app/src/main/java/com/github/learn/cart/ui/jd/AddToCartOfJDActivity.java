package com.github.learn.cart.ui.jd;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.daimajia.androidanimations.library.YoYo;
import com.github.learn.cart.R;
import com.github.learn.cart.databinding.ActivityCartJdBinding;
import com.github.learn.cart.ui.base.BaseActivity;
import com.github.learn.cart.ui.helper.AddToCartHelper;
import com.github.learn.cart.utils.ScreenUtils;
import com.nineoldandroids.animation.ObjectAnimator;


public class AddToCartOfJDActivity extends BaseActivity implements JDPresenter {
    private static final String TAG = "AddToCartOfTaoBaoActivi";
    private static final String GOODS_URL = "http://ww1.sinaimg.cn/small/7a8aed7bjw1f2zwrqkmwoj20f00lg0v7.jpg";
    private Button mBtnAddToCartOfBottom;
    private ActivityCartJdBinding mBinding;
    private ImageView mGoodsView;
    @Override
    public void init(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart_jd);

        mBinding.setGoodsUrl(GOODS_URL);
        mBinding.setPresenter(this);

        mBtnAddToCartOfBottom = mBinding.btnAddShoppingCartBottom;
        mGoodsView = mBinding.ivGoods;
    }



    private void Add2CartAnim( ) {
        /* 起点 */
        int[] startXY = new int[2];
        mBtnAddToCartOfBottom.getLocationInWindow(startXY);
        startXY[0] += mBtnAddToCartOfBottom.getWidth() / 2;
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
        AddToCartHelper.startAnimationForJd(v, 0, 0, fx, fy, mx, my, tx, ty, new AddToCartHelper.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                mBinding.setAmount(++mAmount);
               //动画结束，做一下别的
                YoYo.with(new ScaleUpAnimator())
                    .duration(500)
                    .playOn(mBinding.tvShoppingCartAmount);
            }
        });
    }

    private int mAmount = 0;

    @Override
    public void onAddToCartOfBottom() {
        Add2CartAnim();
    }




    public class ScaleUpAnimator extends BaseViewAnimator {
        @Override
        public void prepare(View target) {
            //ViewGroup parent = (ViewGroup)target.getParent();
            //int distance = parent.getHeight() - target.getTop();
            getAnimatorAgent().playTogether(
                    //ObjectAnimator.ofFloat(target,"alpha",0,1,1),
                    ObjectAnimator.ofFloat(target,"scaleX",0.8f,1f,1.4f,1.2f,1),
                    ObjectAnimator.ofFloat(target,"scaleY",0.8f,1f,1.4f,1.2f,1)
                    //ObjectAnimator.ofFloat(target,"translationY",distance,-60,0)
            );
        }
    }


    /*

    Bitmap bitmap;
    if (mImageView.getDrawable() instanceof BitmapDrawable) {
        bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
    } else {
        Drawable d = mImageView.getDrawable();
        bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        d.draw(canvas);
    }



     */
}
