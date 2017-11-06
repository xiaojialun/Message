package com.example.xjl.message.Client;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.xjl.message.IndexActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import grpc.message.Client.GreeterGrpc;
import grpc.message.Client.RegisterReply;
import grpc.message.Client.RegisterRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class RegisterClient {
    private static final String TAG = "GrpcDemo";
    private static final int PROT = 50055;
//    private static final String HOST = "192.168.199.157";
//    private static final String HOST = "10.0.2.2";
    private static final String HOST="39.108.80.242";
    private Context context;

    public  RegisterClient(String username, String password, String tel, Context context) {
        this.context=context;
        new GrpcTask(username,password,tel).execute();
    }

    private class GrpcTask extends AsyncTask<Void, Void, RegisterReply> {
        private String mHost;
        private int mPort;

        private String mUsername;
        private String mPassword;
        private String mTel;

        private ManagedChannel mChannel;


        public GrpcTask(String username,String password,String tel) {
            this.mHost = HOST;
            this.mPort = PROT;
            this.mUsername=username;
            this.mPassword=password;
            this.mTel=tel;
        }

        @Override
        protected void onPreExecute() {
        }
        @Override
        protected RegisterReply doInBackground(Void... nothing) {
            try {
                mChannel = ManagedChannelBuilder.forAddress(mHost, mPort)
                        .usePlaintext(true)
                        .build();
                GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(mChannel);
                RegisterRequest message = RegisterRequest.newBuilder()
                        .setUsername(mUsername)
                        .setPassword(mPassword)
                        .setTel(mTel).build();
                RegisterReply reply = stub.register(message);
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
        protected void onPostExecute(RegisterReply result) {
            if(result.getResgister()){
                context.startActivity(new Intent(context,IndexActivity.class));
            }else {
                Toast.makeText(context, "该手机号已被注册", Toast.LENGTH_SHORT).show();
            }
            try {
                mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Log.d(TAG, result.getMessage());
        }
    }
}
