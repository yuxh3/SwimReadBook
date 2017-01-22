package com.example.admin.swimreadbook.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 2017/1/20.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initView();
        initData();

    }

    /**
     * 初始化布局
     * @return
     */
    public abstract int getLayoutID();

    /**
     * 初始化控件
     * @return
     */
    public abstract void initView();

    /**
     * 初始化数据
     * @return
     */
    public abstract void initData();

}
