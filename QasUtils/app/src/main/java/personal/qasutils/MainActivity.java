package personal.qasutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import personal.qasutils.ui.FadeImageUsageActivity;
import personal.qasutils.ui.GetPicColorViewUsage;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button btnFadeImage = findViewById(R.id.btnFadeImage);
        btnFadeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FadeImageUsageActivity.class);
                startActivity(intent);
            }
        });
        Button btnGetPicColor = findViewById(R.id.btnGetPicColor);
        btnGetPicColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetPicColorViewUsage.class);
                startActivity(intent);
            }
        });
    }
}
