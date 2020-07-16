package personal.qasutils.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import personal.qasutils.R;
import personal.qasutils.utils.CastUtils;
import personal.qasutils.utils.ViewUtils;

/**
 * 作者：qaszxcwer
 * 日期：2020/7/14
 *
 * 实现图片淡入淡出替换效果的控件
 */
public class FadeImageView extends FrameLayout {
    /**
     * 老图
     */
    private View oldPic;

    /**
     * 新图
     */
    private View newPic;

    /**
     * 动画是否正在执行中
     */
    private boolean isAnimDoing;

    /**
     * 动画执行1秒
     */
    private int animTime = 1000;

    /**
     * 动画类型：淡入
     */
    private final int animTypeIn = 10;

    /**
     * 动画类型：淡出
     */
    private final int animTypeOut = 20;

    private float alphaMin = 0f;

    private float alphaMax = 1f;

    /**
     * 老图片淡出动画
     */
    private ValueAnimator oldPicAnim;

    /**
     * 新图片淡入动画
     */
    private ValueAnimator newPicAnim;

    private IAnimSetListener animatorListener = new IAnimSetListener() {
        @Override
        public void onFinish() {
            oldPic.setBackground(newPic.getBackground());
            oldPic.setAlpha(alphaMax);
            newPic.setBackground(null);
            newPic.setAlpha(alphaMin);
            isAnimDoing = false;
        }

        @Override
        public void onUpdate(int animType, float alpha) {
            if (animType == animTypeIn) {
                newPic.setAlpha(alpha);
            } else {
                oldPic.setAlpha(alpha);
            }
        }
    };

    public FadeImageView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public FadeImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FadeImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FadeImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fade_image_view, this);
        oldPic = ViewUtils.findViewById(getRootView(), R.id.imgOldPic);
        newPic = ViewUtils.findViewById(this, R.id.imgNewPic);
    }

    public void loadPic(Drawable newPicDrawable) {
        if (isAnimDoing) {
            // 动画进行中再次改变，保持总时间不变，直接换图
            oldPic.setBackground(newPic.getBackground());
            newPic.setBackground(newPicDrawable);
            return;
        }
        changePic(newPicDrawable);
    }

    private void changePic(Drawable newPicDrawable) {
        oldPicAnim = createAnim(animTypeOut);
        newPicAnim = createAnim(animTypeIn);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(oldPicAnim, newPicAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorListener.onFinish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        isAnimDoing = true;
        newPic.setBackground(newPicDrawable);
        animatorSet.start();
    }

    private ValueAnimator createAnim(final int type) {
        ValueAnimator animator;
        if (type == animTypeIn) {
            animator = ValueAnimator.ofFloat(alphaMin, alphaMax);
        } else {
            animator = ValueAnimator.ofFloat(alphaMax, alphaMin);
        }
        animator.setDuration(animTime);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorListener.onUpdate(type, CastUtils.cast(animation.getAnimatedValue(), Float.class));
            }
        });
        return animator;
    }

    private interface IAnimSetListener {
        void onFinish();

        void onUpdate(int animType, float alpha);
    }
}
