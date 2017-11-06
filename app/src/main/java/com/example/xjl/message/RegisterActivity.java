package com.example.xjl.message;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xjl.message.Client.RegisterClient;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;


/**
 * Created by xjl on 2017/10/19.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText  usernameTv,passwordTv,telTv,passWord_et;
    private Button registerBtn,Message_Btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化bomb
        initBomb();
        initView();
        initClient();
    }

    private void initBomb() {
        BmobSMS.initialize(this, "553cb4affe3ad127168f4256ed21d67f");
        //这里是我的bmob的applicationID,使用时换成自己的就可以了
    }

    private void initView() {
        usernameTv=(EditText)findViewById(R.id.username);
        passwordTv=(EditText)findViewById(R.id.password);
        telTv=(EditText)findViewById(R.id.tel);
        registerBtn=(Button)findViewById(R.id.registerBtn);
        passWord_et=(EditText)findViewById(R.id.passWord_et);
        Message_Btn=(Button)findViewById(R.id.Message_btn);
    }

    private void initClient() {
        registerBtn.setOnClickListener(this);
        Message_Btn.setOnClickListener(this);
        usernameTv.setOnClickListener(this);
        passwordTv.setOnClickListener(this);
        telTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.username:
                break;
            case R.id.password:
                break;
            case R.id.tel:
                break;
            case R.id.Message_btn:
                getTelMessage();
                break;
            case R.id.registerBtn:
                ToRegister();
        }
    }

    private void getTelMessage() {
        Log.e("MESSAGE:", "2");
        String Tel=telTv.getText().toString();
        if (Tel.length() != 11) {
            Toast.makeText(this, "请输入11位有效手机号码", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("MESSAGE:", "3");
            //进行获取验证码操作和倒计时1分钟操作
            BmobSMS.requestSMSCode(this, Tel, "一夜了",new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId,BmobException ex) {
                    if(ex==null){//验证码发送成功
                        Log.e("bmob", "短信id："+smsId);//用于查询本次短信发送详情
                        //发送成功时，让获取验证码按钮不可点击，且为灰色
                        Message_Btn.setClickable(false);
                        Message_Btn.setBackgroundColor(Color.GRAY);
                        Toast.makeText(RegisterActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                        new  CountDownTimer(60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                Message_Btn.setBackgroundResource(R.drawable.button_shape02);
                                Message_Btn.setText(millisUntilFinished / 1000 + "秒");
                            }

                            @Override
                            public void onFinish() {
                                Message_Btn.setClickable(true);
                                Message_Btn.setBackgroundResource(R.drawable.button_shape03);
                                Message_Btn.setText("重新发送");
                            }
                        }.start();
                        Log.e("MESSAGE:", "4");
                    }
                    else {
                        Log.e("Message",ex.getMessage());
                        Toast.makeText(RegisterActivity.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void ToRegister() {
        if(usernameTv.getText().toString().equals("")
                ||passwordTv.getText().toString().equals("")
                ||telTv.getText().toString().equals("")
                ||passWord_et.getText().toString().equals("")){
            Log.e("MESSAGE:", "6");
            Toast.makeText(this, "输入不合法", Toast.LENGTH_SHORT).show();
        }else {
            BmobSMS.verifySmsCode(this,telTv.getText().toString(), passWord_et.getText().toString(), new VerifySMSCodeListener() {

                @Override
                public void done(BmobException ex) {
                    // TODO Auto-generated method stub
                    if(ex==null){//短信验证码已验证成功
                        Log.e("MESSAGE:", "7");
                        new RegisterClient( usernameTv.getText().toString()
                                ,passwordTv.getText().toString()
                                ,telTv.getText().toString()
                                ,RegisterActivity.this);
                        Log.i("bmob", "验证通过");
                    }else{
                        Log.e("MESSAGE:", "8");
//                        Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        Log.i("bmob", "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                    }
                }
            });
        }
    }

}

