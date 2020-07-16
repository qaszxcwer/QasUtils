package personal.qasutils.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import personal.qasutils.R;
import personal.qasutils.view.FadeImageView;

/**
 * 作者：qaszxcwer
 * 日期：2020/7/16
 *
 * 渐变图片控件使用方法
 */
public class FadeImageUsageActivity extends Activity {
    FadeImageView fadeImageView;

    CustomTarget target = new CustomTarget<Drawable>() {
        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
        }

        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition transition) {
            fadeImageView.loadPic(resource);
        }

        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fadeimage_usage);
        initView();
    }

    private void initView() {
        Button btn1 = findViewById(R.id.btnImage1);
        Button btn2 = findViewById(R.id.btnImage2);
        Button btn3 = findViewById(R.id.btnImage3);
        fadeImageView = findViewById(R.id.fadeImg);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594918618244&di=26842575fda48b77e2284354033a560a&imgtype=0&src=http%3A%2F%2Fpics2.baidu.com%2Ffeed%2Fd62a6059252dd42a22cd9020cd728db1cbeab8c7.jpeg%3Ftoken%3D29a617e4326af94cd8b7d01d6bba7abe%26s%3D33A8F8001C5B1ADA1680BC020300E0C5");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594918841736&di=c945b74df7d539fa483940caa29f555a&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F04%2F10%2F91%2F995818a8f96837d.jpg");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(null);
            }
        });
    }

    private void loadImage(String url) {
        if (url == null) {
            fadeImageView.loadPic(null);
            return;
        }
        Glide.with(this).load(url).into(target);
    }
}
