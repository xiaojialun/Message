package com.example.xjl.message.Client;



import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.xjl.message.CombankDroid;
import com.example.xjl.message.IndexActivity;
import com.example.xjl.message.LoginActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import grpc.message.Client.GreeterGrpc;
import grpc.message.Client.LoginReply;
import grpc.message.Client.LoginRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by xjl on 2017/10/25.
 */

public class LoginClient {
    private static final String TAG = "Demo";
    private static final int PROT = 50055;
//        private static final String HOST = "192.168.199.157";
    private static final String HOST = "10.0.2.2";
//    private static final String HOST="39.108.80.242";
    private Context context;
    private CombankDroid combankDroid;

    public  LoginClient(String username,String password,Context context,CombankDroid combankDroid) {
        Log.e("Demo","1");
        this.context=context;
        this.combankDroid=combankDroid;
        new GrpcTask(username,password).execute();
    }

    private class GrpcTask extends AsyncTask<Void, Void, LoginReply> {
        private String mUsername;
        private String mPassword;
        private String mHost;
        private int mPort;
        private ManagedChannel mChannel;

        public GrpcTask(String mUsername, String mPassword) {
            this.mUsername = mUsername;
            this.mPassword = mPassword;
            this.mHost=HOST;
            this.mPort=PROT;
        }

        @Override
        protected LoginReply doInBackground(Void... voids) {
            mChannel= ManagedChannelBuilder.forAddress(mHost,mPort)
                    .usePlaintext(true).build();
            try{
                GreeterGrpc.GreeterBlockingStub stub=GreeterGrpc.newBlockingStub(mChannel);
                LoginRequest request=LoginRequest.newBuilder().setUsername(mUsername)
                        .setPassword(mPassword).build();
                LoginReply reply=stub.login(request);
                return reply;
            }catch (Exception e){
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                Log.d(TAG,"Failed... : " + sw);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(LoginReply reply) {
            if(reply.getLogin()){
                combankDroid.setLogin(true);
                if(reply.getUser()!=null){
                    combankDroid.setUser(reply.getUser());
                }
                context.startActivity(new Intent(context, IndexActivity.class));
                ((LoginActivity)context).finish();

            }else {
                Toast.makeText(context,"账号或密码错误",Toast.LENGTH_LONG).show();
            }
            try {
                mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Log.d("GRPCDemo",reply.getMessage());
        }
    }
}
