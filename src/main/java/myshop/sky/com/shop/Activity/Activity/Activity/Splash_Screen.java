package myshop.sky.com.shop.Activity.Activity.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wang.avi.AVLoadingIndicatorView;

import myshop.sky.com.shop.Activity.Activity.HomeActivity;
import myshop.sky.com.shop.R;

public class Splash_Screen extends AppCompatActivity {
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        avi = findViewById(R.id.avi);
        startAnim();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopAnim();
                startActivity(new Intent(Splash_Screen.this, HomeActivity.class));
                finish();
            }
        }, 3000);

    }

    void startAnim() {
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim() {
        avi.hide();
        // or avi.smoothToHide();
    }
}
