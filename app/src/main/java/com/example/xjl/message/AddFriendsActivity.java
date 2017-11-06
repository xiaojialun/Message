package com.example.xjl.message;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.xjl.message.Client.SearchFriendsClient;

import grpc.message.Client.User;

/**
 * Created by xjl on 2017/11/3.
 */
//
public class AddFriendsActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView backimg,searchFriends;
    private TextView title;
    private EditText searchFriendsTv;
    private LinearLayout friendView;
    private User mUser;
    private FragmentManager fragmentManager=getSupportFragmentManager();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);
        initView();
        initEvent();
    }

    private void initView() {
        backimg=(ImageView)findViewById(R.id.backimg);
        title=(TextView)findViewById(R.id.title);
        searchFriendsTv= (EditText) findViewById(R.id.searchFriendsTv);
        searchFriends=(ImageView)findViewById(R.id.searchFriends);
        friendView=(LinearLayout)findViewById(R.id.friendView);
    }

    private void initEvent() {
        backimg.setOnClickListener(this);
        searchFriends.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backimg:
                startActivity(new Intent(this,IndexActivity.class));
                this.finish();
                break;
            case R.id.searchFriends:
                searchFriends();
                break;
        }
    }

    private void searchFriends() {
        CombankDroid combankdroid=(CombankDroid)getApplication();
        if(combankdroid.getUser()!=null){
            mUser=combankdroid.getUser();
            String Id=mUser.getId()+"";
            Log.e("This User is:",mUser.getUsername()+" ,Tel is:"+mUser.getTel()+"id is"+Id);
        }
        String sUsername=searchFriendsTv.getText().toString();
        if(sUsername.equals("")){
            Log.d("Search:","sUsername is null!!!!");
        }else {
            new SearchFriendsClient(this,sUsername,friendView,fragmentManager);
        }
    }


}
