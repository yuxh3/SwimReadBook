package com.example.admin.swimreadbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.admin.swimreadbook.R;
import com.example.admin.swimreadbook.ui.view.RoundProgressBar;
import com.example.admin.swimreadbook.utils.StartUtlis;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/1/20.
 */
public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.pb_splash)
    RoundProgressBar tvSplash;

    private Runnable run;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        tvSplash.start(SplashActivity.this);
//        run = new Runnable() {
//            @Override
//            public void run() {
//                StartUtlis.StartActivity(SplashActivity.this, MainActivity.class);
//                //finish();
//            }
//        };
//        tvSplash.postDelayed(run,3000);
    }

    @OnClick(R.id.pb_splash)
    public void onClick() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        //finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvSplash.stop();
        tvSplash.removeCallbacks(run);
        ButterKnife.unbind(this);
    }
}
