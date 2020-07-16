package personal.qasutils.view;

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
        this.setBackground(newPicDrawable);
    }
}
