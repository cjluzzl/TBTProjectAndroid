package cn.cjluzzl.tbt.tbtproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.cjluzzl.tbt.tbtproject.R;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/20 15:30.
 *
 */

public class LoginActivity extends Activity {

    Button btnBack;
    Button btnLogin;
    EditText etUserName;
    EditText etPassWord;
    String userName;
    String userPwd;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        btnBack = (Button) findViewById(R.id.btn_login_back);
        btnLogin = (Button) findViewById(R.id.btn_login_submit);
        etUserName = (EditText) findViewById(R.id.et_login_username);
        etPassWord = (EditText) findViewById(R.id.et_login_passwd);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = etUserName.getText().toString();
                userPwd = etPassWord.getText().toString();
                RequestParams params = new RequestParams();

                if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPwd)){
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }else{

                    params.addBodyParameter("username",userName);
                    params.addBodyParameter("password",userPwd);

                    HttpUtils utils = new HttpUtils();
                    utils.send(HttpRequest.HttpMethod.POST, "http://192.168.155.1:8000/users/mobile_login/",params, new RequestCallBack<Object>() {
                        @Override
                        public void onSuccess(ResponseInfo<Object> responseInfo) {
                            String result = (String) responseInfo.result;
                            System.out.println("登录信息提交成功"+result);
                            if(TextUtils.equals(result, "success")){
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                sp = getSharedPreferences("user_info",MODE_PRIVATE);
                                editor = sp.edit();
                                editor.putString("username", userName);
                                editor.putString("userpwd", userPwd);
                                editor.commit();
                                Intent intent = getIntent();
                                LoginActivity.this.setResult(200, intent);
                                LoginActivity.this.finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                LoginActivity.this.setResult(100, intent);
                                LoginActivity.this.finish();
                            }



                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            System.out.println("登录信息提交失败");
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }


            }
        });
    }
}
