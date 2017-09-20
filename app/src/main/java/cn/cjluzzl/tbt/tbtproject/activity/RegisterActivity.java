package cn.cjluzzl.tbt.tbtproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
 * on 2017/9/20 15:28.
 * 用户注册Activity
 */

public class RegisterActivity extends Activity {
    Button btnBack;
    Button btnSubmit;
    EditText etUserName;
    EditText etNickName;
    EditText etPwd;
    EditText etPwdAgain;
    EditText etEmail;
    EditText etPhoneNumber;

    String UserName;
    String NickName;
    String Pwd;
    String PwdAgain;
    String Email;
    String PhoneNumber;

    RadioGroup rg;
    String gender = "male";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        btnBack = (Button) findViewById(R.id.btn_register_back);
        btnSubmit = (Button) findViewById(R.id.btn_register_submit);
        etUserName = (EditText) findViewById(R.id.et_register_username);
        etNickName = (EditText) findViewById(R.id.et_register_nick_name);
        etEmail = (EditText) findViewById(R.id.et_register_email);
        etPwd = (EditText) findViewById(R.id.et_register_passwd);
        etPwdAgain = (EditText) findViewById(R.id.et_register_passwd_again);
        etPhoneNumber = (EditText) findViewById(R.id.et_register_phone_number);
        rg = (RadioGroup) findViewById(R.id.rg);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_male){
                    gender = "male";
                }
                if(checkedId == R.id.rb_female){
                    gender = "female";
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserName = etUserName.getText().toString();
                NickName = etNickName.getText().toString();
                Pwd = etPwd.getText().toString();
                PwdAgain = etPwdAgain.getText().toString();
                Email = etEmail.getText().toString();
                PhoneNumber = etPhoneNumber.getText().toString();

                RequestParams params = new RequestParams();
                params.addBodyParameter("username",UserName);
                params.addBodyParameter("pwd",Pwd);
                params.addBodyParameter("email",Email);
                params.addBodyParameter("phoneNumber", PhoneNumber);
                params.addBodyParameter("gender",gender);
                HttpUtils utils = new HttpUtils();
                utils.send(HttpRequest.HttpMethod.POST, "http://192.168.155.1:8000/users/mobile_register/", params, new RequestCallBack<Object>() {
                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo) {
                        String result = (String) responseInfo.result;
                        if(TextUtils.equals(result, "success")){
                            Toast.makeText(RegisterActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "注册失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(RegisterActivity.this, "网络故障，请重试",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
