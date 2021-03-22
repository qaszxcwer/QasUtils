package personal.qasutils.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import personal.i.qasutilslibrary.view.GetPicColorView;
import personal.qasutils.R;

/**
 * 图片取色用例
 *
 * 作者：qaszxcwer
 * 日期：2021/3/22
 */
public class GetPicColorViewUsage extends Activity implements GetPicColorView.ICrossPosChange {
    private RelativeLayout relativeLayout;

    private ImageView imageView;

    private Bitmap bitmap;

    private GetPicColorView getPicColorView;

    private Activity activity;

    private CustomTarget target = new CustomTarget<Drawable>() {
        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            imageView.setImageDrawable(resource);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
            getPicColorView = new GetPicColorView(activity);
            getPicColorView.setMaxSize(bitmap.getWidth(), bitmap.getHeight());
            int widthAndHeight = activity.getResources().getDimensionPixelSize(R.dimen.dp100);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(widthAndHeight, widthAndHeight);
            getPicColorView.setLayoutParams(params);
            getPicColorView.setOnPosChangeListener(GetPicColorViewUsage.this);
            relativeLayout.addView(getPicColorView);
        }

        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpiccolor_usage);
        activity = this;
        initView();
        usage();
    }

    private void initView() {
        relativeLayout = findViewById(R.id.getPicColor_rel);
        imageView = findViewById(R.id.getPicColor_img);
    }

    private void usage() {
        // 正式商用时还要注意图片缩放的问题，这张图是1024*680的，和图片实际显示尺寸并不相同，取色会有一些偏差
        Glide.with(this)
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic5.nipic.com%2F20100225%2F1399111_094253001130_2.jpg&refer=http%3A%2F%2Fpic5.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1619017272&t=13b1b49ef16b1c6e0239e258b4f8cf30")
                .into(target);
    }

    @Override
    public void onPosChanged(int centerX, int centerY) {
        getPicColorView.drawColorRaing(bitmap.getPixel(centerX, centerY));
    }
}
