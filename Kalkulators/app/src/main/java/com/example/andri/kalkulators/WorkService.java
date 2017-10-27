package com.example.andri.kalkulators;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import android.os.Handler;

import java.lang.invoke.MethodHandles;


public class WorkService extends IntentService {

    private static final String KEY_LENGTH  = "length";
    public static final String KEY_MESSAGE = "message";
    public static final String MESSAGE_ACTION = "com.example.andri.kalkulators.MESSAGE";
    private Handler handler;

    @Override
    public void onCreate(){
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
    }

    public static void showMessage(Context context, long delay, String message){
        Intent intent = new Intent(context, WorkService.class);
        intent.putExtra(KEY_LENGTH, delay);
        intent.putExtra(KEY_MESSAGE, message);
        context.startService(intent);

    }

    public WorkService(){
        super("WorkService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent){
        if(intent == null){
            return;
        }

        long length = intent.getLongExtra(KEY_LENGTH, 0);
        final String message = intent.getStringExtra(KEY_MESSAGE);

        if(message == null){
            return;
        }

        if(length > 0){
            try{
                Thread.sleep(length);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        Intent outgoing = new Intent(MESSAGE_ACTION);
        outgoing.putExtra(KEY_MESSAGE, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outgoing);
    }

}
