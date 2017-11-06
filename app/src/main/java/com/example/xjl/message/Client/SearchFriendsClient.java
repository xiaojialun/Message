package com.example.xjl.message.Client;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xjl.message.friendsView_fragment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import grpc.message.Client.GreeterGrpc;
import grpc.message.Client.SearchFriendReply;
import grpc.message.Client.SearchFriendRequest;
import grpc.message.Client.User;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by xjl on 2017/11/4.
 */

public class SearchFriendsClient {
    private static final String TAG = "GrpcDemo";
    private static final int PROT = 50055;
    //    private static final String HOST = "192.168.199.157";
    private static final String HOST = "10.0.2.2";

//    private static final String HOST="39.108.80.242";
    private Context context;
    private LinearLayout friendView;
    private FragmentManager fragmentManager;

    public  SearchFriendsClient(Context context,String username,LinearLayout friendView,FragmentManager fragmentManager) {
        this.context=context;
        this.friendView=friendView;
        this.fragmentManager=fragmentManager;
        new SearchFriendsClient.GrpcTask(username).execute();
    }

    private class GrpcTask extends AsyncTask<Void, Void, SearchFriendReply> {
        private String mHost;
        private int mPort;
        private String mUsername;
        private ManagedChannel mChannel;


        public GrpcTask(String username) {
            this.mHost = HOST;
            this.mPort = PROT;
            this.mUsername=username;
        }

        @Override
        protected void onPreExecute() {
        }
        @Override
        protected SearchFriendReply doInBackground(Void... nothing) {
            try {
                mChannel = ManagedChannelBuilder.forAddress(mHost, mPort)
                        .usePlaintext(true)
                        .build();
                GreeterGrpc.GreeterBlockingStub stub=GreeterGrpc.newBlockingStub(mChannel);
                SearchFriendRequest message = SearchFriendRequest.newBuilder()
                        .setUsername(mUsername)
                        .build();
                SearchFriendReply reply = stub.searchFriend(message);
                return reply;
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                Log.d(TAG,"Failed... : " + sw);
                return null;
            }
        }

        @Override
        protected void onPostExecute(SearchFriendReply result) {
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            User user=result.getUser();
            friendView.removeAllViews();
            if(!user.getUsername().equals("")){
                Fragment fragment_friendView=new friendsView_fragment();
                Bundle bundle=new Bundle();
                bundle.putString(friendsView_fragment.USERNAME,user.getUsername());
                bundle.putInt(friendsView_fragment.USERID,user.getId());
                bundle.putString(friendsView_fragment.TEL,user.getTel());
                fragment_friendView.setArguments(bundle);
                if(fragmentManager.findFragmentByTag("friendView")==null){
                    transaction.add(friendView.getId(),fragment_friendView,"friendView");
                }else {
                    transaction.replace(friendView.getId(),fragment_friendView,"friendView");
                }

                Log.d(TAG, result.getUser().toString());
                Log.d(TAG, result.getUser().toString());

            }else {
                if(fragmentManager.findFragmentByTag("friendView")==null){
                    TextView tv=new TextView(context);
                    tv.setText("用户不存在");
                    friendView.addView(tv);
                }else {
                    transaction.remove(fragmentManager.findFragmentByTag("friendView"));
                    TextView tv=new TextView(context);
                    tv.setText("用户不存在");
                    friendView.addView(tv);
                }
            }
            transaction.commit();
            try {
                mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }
}