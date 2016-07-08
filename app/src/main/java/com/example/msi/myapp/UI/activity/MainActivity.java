package com.example.msi.myapp.UI.activity;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.msi.myapp.Interface.DoSth;
import com.example.msi.myapp.R;
import com.example.msi.myapp.Utils.SaveImage;
import com.example.msi.myapp.UI.activity.fragment.AndroidFragment;
import com.example.msi.myapp.UI.activity.fragment.IosFragment;
import com.example.msi.myapp.UI.activity.fragment.MeiziFragment;
import com.example.msi.myapp.module.MeiziResult;
import com.ftinc.scoop.BindToppingStatus;
import com.ftinc.scoop.Scoop;
import com.ftinc.scoop.ui.ScoopSettingsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;
import rx.Subscriber;
@BindToppingStatus(Toppings.PRIMARY_DARK)
public class MainActivity extends AppCompatActivity implements DoSth {

    //绑定组件
    @Bind(R.id.dl_main)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar_main)
    Toolbar toolbar;
    @Bind(R.id.bottom_tab_layout)
    PagerBottomTabLayout bottomTabLayout;
    @Bind(R.id.nv_main)
    NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;
    private Controller controller;
    private android.app.FragmentTransaction fragmentTransaction;

    private List<Fragment> fragments;
    private Bundle bundle;
    Subscriber<List<MeiziResult>> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Scoop.waffleCone()
                .addFlavor("Default", R.style.Theme_Scoop, true)
                .addFlavor("Light", R.style.Theme_Scoop_Light)
                .addDayNightFlavor("DayNight", R.style.Theme_Scoop_DayNight)
                .addFlavor("Alternate 1", R.style.Theme_Scoop_Alt1)
                .addFlavor("Alternate 2", R.style.Theme_Scoop_Alt2)
                .addToppings(Toppings.getToppings())
                .setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this))
                .initialize();
        super.onCreate(savedInstanceState);

        // Apply Scoop to the activity
        Scoop.getInstance().apply(this);

        setContentView(R.layout.activity_main);


        //初始化绑定的组件
        ButterKnife.bind(this);
        //绑定需要换肤的组件
        Scoop.sugarCone().bind(this);
        Scoop.sugarCone().bind(this, Toppings.PRIMARY,toolbar)
                .bindStatusBar(this, Toppings.PRIMARY_DARK);
        //设置toolbar
        setSupportActionBar(toolbar);
        //将drawlayout和toolbar绑定使得navigaView能被打开
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawopen,R.string.drawclose);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        //设置导航栏按钮监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_item_about:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("hello")
                                .setMessage("this is my first app.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                        break;
                    case R.id.navigation_item_blog:
                        controller.setMessageNumber(0,6);
                        Toast.makeText(MainActivity.this,"blog",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_delete:
                        SaveImage.getINSTANCE().cleanImage(getApplicationContext());
                        Snackbar.make(MainActivity.this.getCurrentFocus(),"图片已删除",Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_item_change_theme:
                        Snackbar.make(MainActivity.this.getCurrentFocus(),"successed",Snackbar.LENGTH_SHORT).show();
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        initFragments();
        //创建底部按钮
        controller = bottomTabLayout.builder()
                .addTabItem(R.mipmap.ic_launcher,"android",Color.parseColor("#e91e63"))
                .addTabItem(R.mipmap.ic_launcher,"ios",Color.parseColor("#e91e63"))
                .addTabItem(R.mipmap.ic_launcher,"妹纸",Color.parseColor("#e91e63"))
                .setDefaultColor(Color.parseColor("#9c27b0"))
                .setMessageBackgroundColor(Color.RED)
                .setMessageNumberColor(Color.WHITE)
                .setMode(TabLayoutMode.HIDE_TEXT)
                .build();
        //设置底部按钮的监听
        controller.addTabItemClickListener(new OnTabItemSelectListener() {
            @Override
            public void onSelected(int index, Object tag) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //跳转到相应的页面
                switch (index){
                    case 0:
                        toolbar.setTitle("android");
                        fragmentTransaction.replace(R.id.fragment,fragments.get(index))
                        .addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        toolbar.setTitle("ios");
                        fragmentTransaction.replace(R.id.fragment,fragments.get(index))
                        .addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        toolbar.setTitle("妹纸");
                        fragmentTransaction.replace(R.id.fragment,fragments.get(index))
                        .addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                }
            }

            @Override
            public void onRepeatClick(int index, Object tag) {
                controller.setMessageNumber(index,0);
            }
        });

    }

    private void initFragments() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new AndroidFragment());
        fragments.add(new IosFragment());
        fragments.add(new MeiziFragment());

    }

    //从主页面直接返回桌面
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        Scoop.sugarCone().unbind(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 0){
            recreate();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Scoop.getInstance().apply(this, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.toolbar_setting){
            startActivityForResult(ScoopSettingsActivity.createIntent(this), 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //回调方法设置主页面的消息提示
    @Override
    public void a(int index) {
        controller.setMessageNumber(index,6);
    }
}
