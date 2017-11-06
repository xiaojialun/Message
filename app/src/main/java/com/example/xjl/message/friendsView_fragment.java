package com.example.xjl.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xjl.message.CustomerView.AvatarImageView;

/**
 * Created by xjl on 2017/11/4.
 */

public class friendsView_fragment extends Fragment {
    private AvatarImageView userimg;
    private TextView username;
    private Button add_Btn;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.addfriends_fragment,container,false);
        initView();
        initData();
        initEvent();
        if(view==null){
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }

    private void initView() {
        userimg=(AvatarImageView)view.findViewById(R.id.userimg);
        username=(TextView)view.findViewById(R.id.username);
        add_Btn=(Button)view.findViewById(R.id.add_Btn);
    }

    public final static String USERNAME="username";
    public final static String USERID="userId";
    public final static String TEL="tel";

    private void initData() {
        Bundle Arguments=getArguments();
        if(Arguments!=null){
            String sUsername=Arguments.getString(USERNAME);
            username.setText(sUsername);
        }
    }

    private void initEvent() {
    }
}
