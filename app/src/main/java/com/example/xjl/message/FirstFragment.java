package com.example.xjl.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xjl.message.CustomerView.AvatarImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by xjl on 2017/10/19.
 */

public class FirstFragment extends Fragment {
    private String FragmentText="test";
    public static final String FRAGTEXT="FragmentText";
    private TextView tv;
    private ListView list_lv;
    private List<HashMap<String,Object>> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        initView(view);
        initTitle();
        initList(inflater);
        return view;
    }

    private void initList(LayoutInflater inflater) {
        list=getData();
        MyAdapter adapter=new MyAdapter(inflater);
        list_lv.setAdapter(adapter);
    }

    //ViewHolder静态类
    static class ViewHolder
    {
        public AvatarImageView img;
        public TextView tv;
        public TextView info;
    }

    private class MyAdapter extends BaseAdapter{
        private LayoutInflater mInflater = null;
        private MyAdapter(LayoutInflater inflater){
            this.mInflater=inflater;
        };
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if(view == null)
            {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                view = mInflater.inflate(R.layout.frist_list_item, null);
                holder.img = view.findViewById(R.id.img);
                holder.tv = view.findViewById(R.id.tv);
                holder.info = view.findViewById(R.id.info);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                view.setTag(holder);
            }else
            {
                holder = (ViewHolder)view.getTag();
            }
            holder.img.setImageResource((Integer) list.get(i).get("img"));
            holder.tv.setText((String)list.get(i).get("tv"));
            holder.info.setText((String)list.get(i).get("info"));

            return view;
        }
    }

    private List<HashMap<String,Object>> getData() {
        List<HashMap<String,Object>> Data=new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<20;i++){
            HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("img", R.mipmap.ic_launcher);
            map.put("tv","寒春思密达");
            map.put("info","在线");
            Data.add(map);
        }
        return Data;
    }

    private void initTitle() {
        if(getArguments()!=null){
            FragmentText=getArguments().getString(FRAGTEXT);
            tv.setText(FragmentText);
        }else{
            tv.setText("error");
        }
    }

    private void initView(View view) {
        tv=view.findViewById(R.id.text_fragment);
        list_lv=view.findViewById(R.id.List_lv);
    }
}
