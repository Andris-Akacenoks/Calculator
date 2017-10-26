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
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WorkService.this, message, Toast.LENGTH_SHORT).show();

            }
        });
        Intent outgoing = new Intent(MESSAGE_ACTION);
        outgoing.putExtra(KEY_MESSAGE, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outgoing);
    }

}
/*
private BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String message = intent.getStringExtra(WorkService.KEY_MESSAGE);
                TextView textView = findViewById(R.id.txtResult);
                textView.setText(textView.getText() + "\n" + message);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver, new IntentFilter(WorkService.MESSAGE_ACTION));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.unregisterReceiver(receiver);
        super.onStop();
    }


        TextView text = findViewById(R.id.txtResult);
        //text.setText(getString(R.string.app_name, System.currentTimeMillis()));
        new HelloTask(CalculatorActivity.this).execute();

        WorkService.showMessage(this, 0, "Hello");
        WorkService.showMessage(this, 5000, "How are you");
        WorkService.showMessage(this, 10000, "Cool");
        WorkService.showMessage(this, 0, "Bye");
 */