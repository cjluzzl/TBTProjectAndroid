package cn.cjluzzl.tbt.tbtproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import cn.cjluzzl.tbt.tbtproject.R;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/20 15:28.
 * 用户注册Activity
 */

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
    }
}
