package com.example.msi.myapp.UI.activity;

import android.app.Application;
import android.preference.PreferenceManager;

import com.example.msi.myapp.R;
import com.ftinc.scoop.Scoop;

/**
 * 文 件 名: MyApplication
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/11 23:03
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Scoop.waffleCone()
                .addFlavor("Default", R.style.Theme_Scoop, true)
                .addFlavor("Light", R.style.Theme_Scoop_Light)
                .addDayNightFlavor("DayNight", R.style.Theme_Scoop_DayNight)
                .addFlavor("Alternate 1", R.style.Theme_Scoop_Alt1)
                .addFlavor("Alternate 2", R.style.Theme_Scoop_Alt2)
                .addToppings(Toppings.getToppings())
                .setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this))
                .initialize();
    }
}
