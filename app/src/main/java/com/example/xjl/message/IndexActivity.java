package com.example.xjl.message;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjl on 2017/10/19.
 */

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mtitle,text_message,text_friends,text_trends;
    private List<TextView> TvList=new ArrayList<TextView>();
    private FrameLayout frameLayout;
    private ImageView userimage;
    private FragmentManager manager;
    private FragmentTransaction transantion;
    private List<FirstFragment> fragmentlist=new ArrayList<FirstFragment>();
    private FirstFragment f1,f2,f3;
    private ImageView titleMenu;
    private Toolbar TitleBar;
    private LinearLayout addFirends_item1;
    private View popwindow_view;
    private ActivityManager activityManager;

    private String fragmentText[]=new String[]{
      "fristFragment","secondFragment","thirdFragment"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initDatas(savedInstanceState);
        initView();
        initEvent();
        upload();
    }

    private void initDatas(Bundle savedInstanceState) {
        frameLayout=(FrameLayout)findViewById(R.id.fragment_container);
        manager=getSupportFragmentManager();
        transantion=manager.beginTransaction();
        if(savedInstanceState!=null){
            f1= (FirstFragment) manager.findFragmentByTag("0");
            f2= (FirstFragment) manager.findFragmentByTag("1");
            f3= (FirstFragment) manager.findFragmentByTag("2");
            fragmentlist.add(f1);
            fragmentlist.add(f2);
            fragmentlist.add(f3);

        }else {
            if(f1==null){
                Bundle bundle = new Bundle();
                bundle.putString(FirstFragment.FRAGTEXT, fragmentText[0]);
                f1=new FirstFragment();
                f1.setArguments(bundle);
                fragmentlist.add(f1);
            }
            if(f2==null){
                Bundle bundle = new Bundle();
                bundle.putString(FirstFragment.FRAGTEXT, fragmentText[1]);
                f2=new FirstFragment();
                f2.setArguments(bundle);
                fragmentlist.add(f2);
            }
            if(f3==null){
                Bundle bundle = new Bundle();
                bundle.putString(FirstFragment.FRAGTEXT, fragmentText[2]);
                f3=new FirstFragment();
                f3.setArguments(bundle);
                fragmentlist.add(f3);
            }


            for(int i=0;i<fragmentlist.size();i++){
                String tag=i+"";
                transantion.add(frameLayout.getId(),fragmentlist.get(i),tag);
            }
            for(int i=1;i<fragmentlist.size();i++){
                transantion.hide(fragmentlist.get(i));
            }
            transantion.show(fragmentlist.get(0));

        }
        transantion.commit();
    }

    private void upload() {
        //绝对路径是主页上的文件或目录在硬盘上真正的路径，相对路径是相对于当前文件的路径
//        File userimg=new File();
    }

    private void initView() {
        text_friends=(TextView)findViewById(R.id.text_friends);
        text_trends=(TextView)findViewById(R.id.text_trends);
        userimage=(ImageView)findViewById(R.id.userimage);
        text_message=(TextView)findViewById(R.id.text_message);
        mtitle=(TextView)findViewById(R.id.mtitle);
        titleMenu=(ImageView)findViewById(R.id.titleMenu);
        TitleBar=(Toolbar)findViewById(R.id.Titlebar);
        popwindow_view=LayoutInflater.from(this).inflate(R.layout.popwindow_view,null);
        addFirends_item1=(LinearLayout) popwindow_view.findViewById(R.id.addFriends_item1);

        TvList.add(mtitle);
        TvList.add(text_friends);
        TvList.add(text_trends);
        TvList.add(text_message);
        if(!f1.isHidden()){
            selected(R.id.text_message);
        }else if(!f2.isHidden()){
            selected(R.id.text_friends);
        }else if(!f3.isHidden()){
            selected(R.id.text_trends);
        }
    }

    private void initEvent() {
        text_message.setOnClickListener(this);
        text_friends.setOnClickListener(this);
        text_trends.setOnClickListener(this);
        userimage.setOnClickListener(this);
        titleMenu.setOnClickListener(this);
        addFirends_item1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        transantion=getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.text_message:
                selected(R.id.text_message);
                hideAllFragment(transantion);
                transantion.show(fragmentlist.get(0));
                break;
            case R.id.text_friends:
                selected(R.id.text_friends);
                hideAllFragment(transantion);
                transantion.show(fragmentlist.get(1));
                break;
            case R.id.text_trends:
                hideAllFragment(transantion);
                selected(R.id.text_trends);
                transantion.show(fragmentlist.get(2));
                break;
            case R.id.userimage:
                break;
            case R.id.titleMenu:
                showMent();
                break;
            case R.id.addFriends_item1:
                startActivity(new Intent(this,AddFriendsActivity.class));
                break;
        }
        transantion.commit();
    }

    private void showMent() {
        PopupWindow popupWindow=new PopupWindow(this);
        popupWindow.setWidth(500);
        popupWindow.setHeight(400);
        popupWindow.setContentView(popwindow_view);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAsDropDown(titleMenu,-420,50);
    }

    public void hideAllFragment(FragmentTransaction transaction){
       for(int i=0;i<fragmentlist.size();i++){
           String tag=i+"";
           transaction.hide(getSupportFragmentManager().findFragmentByTag(tag));
       }
    }
    public void selected(int Id){
       if(Id== R.id.text_message){
           text_message.setSelected(true);
           text_friends.setSelected(false);
           text_trends.setSelected(false);
           mtitle.setText("消息");
       }else if(Id== R.id.text_friends){
           text_message.setSelected(false);
           text_friends.setSelected(true);
           text_trends.setSelected(false);
           mtitle.setText("联系人");
       }else if(Id== R.id.text_trends){
           text_message.setSelected(false);
           text_friends.setSelected(false);
           text_trends.setSelected(true);
           mtitle.setText("动态");
       }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle=new Bundle();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
