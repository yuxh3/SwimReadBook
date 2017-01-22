package com.example.admin.swimreadbook.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by admin on 2017/1/20.
 */
public class StartUtlis {

    /**
     * 开启Activity
     * @param context
     * @param clazz
     */
    public static void StartActivity(Context context,Class<?> clazz){
        context.startActivity(new Intent(context,clazz));
    }


}
