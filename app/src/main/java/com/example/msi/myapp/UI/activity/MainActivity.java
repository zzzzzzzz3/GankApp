package com.example.msi.myapp.UI.activity;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.msi.myapp.Interface.ShareToWB;
import com.example.msi.myapp.Login.weibo.AccessTokenKeeper;
import com.example.msi.myapp.Login.weibo.Constants;
import com.example.msi.myapp.R;
import com.example.msi.myapp.Utils.SaveImage;
import com.example.msi.myapp.UI.activity.fragment.AndroidFragment;
import com.example.msi.myapp.UI.activity.fragment.IosFragment;
import com.example.msi.myapp.UI.activity.fragment.MeiziFragment;
import com.example.msi.myapp.module.MeiziResult;
import com.ftinc.scoop.Scoop;
import com.ftinc.scoop.ui.ScoopSettingsActivity;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.LogoutAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements ShareToWB ,IWeiboHandler.Response{

    //绑定组件
    @Bind(R.id.dl_main)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar_main)
    Toolbar toolbar;
    @Bind(R.id.bottom_tab_layout)
    PagerBottomTabLayout bottomTabLayout;
    @Bind(R.id.nv_main)
    NavigationView navigationView;

    private CircleImageView userPhoto;

    private TextView userName;

    private ActionBarDrawerToggle drawerToggle;
    private Controller controller;
    private android.app.FragmentTransaction fragmentTransaction;

    private List<Fragment> fragments;
    private Bundle bundle;
    Subscriber<List<MeiziResult>> subscriber;


    //微博登陆
    private Oauth2AccessToken accessToken;
    private AuthInfo authInfo;
    private SsoHandler ssoHandler;
    private UsersAPI usersAPI;
    /** 微博分享的接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Apply Scoop to the activity
        Scoop.getInstance().apply(this);

        setContentView(R.layout.activity_main);

        //微博实例
        authInfo = new AuthInfo(MainActivity.this, Constants.APP_KEY,Constants.REDIRECT_URL,Constants.SCOPE);
        ssoHandler = new SsoHandler(MainActivity.this,authInfo);
        accessToken = AccessTokenKeeper.readAccessToken(MainActivity.this);
        // 第一次启动本应用，AccessToken 不可用
        if (accessToken.isSessionValid()) {
            Toast.makeText(MainActivity.this,"请勿重复登陆",Toast.LENGTH_SHORT).show();
        }

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);

        // 注册第三方应用到微博客户端中
        mWeiboShareAPI.registerApp();


        //初始化绑定的组件
        ButterKnife.bind(this);
        View view = navigationView.getHeaderView(0);
        userPhoto = (CircleImageView) view.findViewById(R.id.user_photo);
        userName = (TextView) view.findViewById(R.id.user_name);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("注销登录")
                        .setMessage("确定要注销登录？")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new LogoutAPI(MainActivity.this, Constants.APP_KEY,
                                        AccessTokenKeeper.readAccessToken(MainActivity.this)).logout(new LogOutRequestListener());
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
            }
        });

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
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.navigation_item_login:
                        ssoHandler.authorizeWeb(new AuthListener());
                        break;
                    case R.id.navigation_item_delete:
                        SaveImage.getINSTANCE().cleanImage(getApplicationContext());
                        Snackbar.make(MainActivity.this.getCurrentFocus(),"图片已删除",Snackbar.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.navigation_item_change_theme:
                        Snackbar.make(MainActivity.this.getCurrentFocus(),"successed",Snackbar.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                }
                //item.setChecked(true);
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
        /**
         * 当 SSO 授权 Activity 退出时，该函数被调用。
         */
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
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

    //分享到微博
    @Override
    public void share(String url, String desc) {
        sendMultiMessage(url,desc);
    }
    private void sendMultiMessage(String url,String desc) {

        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj(desc);
        weiboMessage.imageObject = getImageObj();
        weiboMessage.mediaObject = getWebpageObj(url);
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        AuthInfo authInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException( WeiboException arg0 ) {
            }

            @Override
            public void onComplete( Bundle bundle ) {
                // TODO Auto-generated method stub
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
                Toast.makeText(getApplicationContext(), "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancel() {
            }
        });
    }
    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String desc) {
        TextObject textObject = new TextObject();
        String format ="#干货分享# %1$s";
        String text = String.format(format,desc);
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo_share);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(String url) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.actionUrl = url;
        return mediaObject;
    }
    //分享完成的回调
    @Override
    public void onResponse(BaseResponse baseResponse) {
        if(baseResponse!= null){
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, "分享失败", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(this,
                            "Error Message: " + baseResponse.errMsg,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        mWeiboShareAPI.handleWeiboResponse(intent, this);

    }

    private class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle bundle) {
            // 从 Bundle 中解析 Token
            accessToken = Oauth2AccessToken.parseAccessToken(bundle);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  accessToken.getPhoneNum();
            if (accessToken.isSessionValid()) {
                // 获取用户信息接口
                usersAPI = new UsersAPI(MainActivity.this, Constants.APP_KEY, accessToken);
                long uid = Long.parseLong(accessToken.getUid());
                usersAPI.show(uid, new RequestListener() {
                    @Override
                    public void onComplete(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            // 调用 User#parse 将JSON串解析成User对象
                            User user = User.parse(response);
                            if (user != null) {
                                userName.setText(user.name);
                                Glide.with(MainActivity.this)
                                        .load(user.profile_image_url)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(userPhoto);
                                Toast.makeText(MainActivity.this,
                                        "登陆成功，用户昵称：" + user.screen_name,
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {

                        ErrorInfo info = ErrorInfo.parse(e.getMessage());
                        Toast.makeText(MainActivity.this, info.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(MainActivity.this, accessToken);
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = bundle.getString("code");
                String message = "登陆错误";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }

        @Override
        public void onCancel() {

        }
    }
    /**
     * 登出按钮的监听器，接收登出处理结果。（API 请求结果的监听器）
     */
    private class LogOutRequestListener implements RequestListener {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String value = obj.getString("result");

                    if ("true".equalsIgnoreCase(value)) {
                        AccessTokenKeeper.clear(MainActivity.this);
                        userName.setText("hello");
                        userPhoto.setImageResource(R.drawable.photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}

