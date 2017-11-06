package com.example.xjl.message;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;

/**
 * Created by kris on 2016.
 *
 */
public class MessageLoginActivity extends Activity implements View.OnClickListener {
    private EditText userName_et, passWord_et;
    private Button Message_btn, login_btn;
    private static final int MY_PERMISSION_REQUEST_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_login);
        //初始化bomb
        initBomb();
        //初始化控件
        initView();
        //设置监听
        initEvent();
    }
    private void initBomb() {
            BmobSMS.initialize(this, "553cb4affe3ad127168f4256ed21d67f");
            //这里是我的bmob的applicationID,使用时换成自己的就可以了
        }
    //初始化控件
    private void initView() {
        userName_et = findViewById(R.id.userName_et);
        passWord_et = findViewById(R.id.passWord_et);
        Message_btn = findViewById(R.id.Message_btn);
        login_btn = findViewById(R.id.login_btn);
    }
    private void initEvent() {
        //监听初始化
        login_btn.setOnClickListener(this);
        Message_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Log.e("MESSAGE:", "1");
        String userName = userName_et.getText().toString();
        String passWord = passWord_et.getText().toString();
        switch (v.getId()) {
            case R.id.Message_btn:
                getMessage_btn(userName);

                break;
            case R.id.login_btn:
                getLogin_btn(userName,passWord);
                break;
        }
    }
    private void getLogin_btn(String userName,String passWord) {
        Log.e("MESSAGE:", "5");
        if (userName.length() == 0 || passWord.length() == 0 || userName.length() != 11) {
            Log.e("MESSAGE:", "6");
            Toast.makeText(this, "手机号或验证码输入不合法", Toast.LENGTH_SHORT).show();
        } else {
            BmobSMS.verifySmsCode(this, userName, passWord, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.e("MESSAGE:", "7");
                        Toast.makeText(MessageLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("MESSAGE:", "8");
                        Toast.makeText(MessageLoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void getMessage_btn(String userName) {
        Log.e("MESSAGE:", "2");
        if (userName.length() != 11) {
            Toast.makeText(this, "请输入11位有效手机号码", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("MESSAGE:", "3");
            //进行获取验证码操作和倒计时1分钟操作
            BmobSMS.requestSMSCode(this, userName, "一夜了",new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId,BmobException ex) {
                    if(ex==null){//验证码发送成功
                        Log.e("bmob", "短信id："+smsId);//用于查询本次短信发送详情
                        //发送成功时，让获取验证码按钮不可点击，且为灰色
                        Message_btn.setClickable(false);
                        Message_btn.setBackgroundColor(Color.GRAY);
                        Toast.makeText(MessageLoginActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                        new  CountDownTimer(60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                Message_btn.setBackgroundResource(R.drawable.button_shape02);
                                Message_btn.setText(millisUntilFinished / 1000 + "秒");
                            }

                            @Override
                            public void onFinish() {
                                Message_btn.setClickable(true);
                                Message_btn.setBackgroundResource(R.drawable.button_shape03);
                                Message_btn.setText("重新发送");
                            }
                        }.start();
                        Log.e("MESSAGE:", "4");
                    }
                    else {
                        Log.e("Message",ex.getMessage());
                        Toast.makeText(MessageLoginActivity.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


