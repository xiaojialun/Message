package com.example.xjl.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xjl.message.Client.LoginClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button LoginBtn;
    private EditText username, password;
    private TextView register, repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView() {
        LoginBtn = (Button) findViewById(R.id.buttonLogin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register);
        repassword = (TextView) findViewById(R.id.repassword);
    }

    private void initEvent() {
        LoginBtn.setOnClickListener(this);
        register.setOnClickListener(this);
        repassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.buttonLogin:
               Login();
               break;
           case R.id.register:
               register();
               break;
           case R.id.repassword:
               break;
       }
    }

    private void Login() {
        CombankDroid combankDroid= (CombankDroid) getApplication();
        new LoginClient(username.getText().toString()
                ,password.getText().toString()
                ,this
                ,combankDroid);

    }

    private void repassword() {
        if(username==null||password==null){

        }else {

        }
    }

    private void register() {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }
}
