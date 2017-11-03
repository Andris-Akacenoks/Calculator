package com.example.andri.kalkulators;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;


public class RandomActivity extends Activity {
    public static final String RESULT_KEY = "result";

    public static void launchForResult(Activity starter, int requestCode) {
        starter.startActivityForResult(new Intent(starter, RandomActivity.class), requestCode);
    }

    public String number;
    private Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_activity_layout);

        findViewById(R.id.btnGenerateNumber).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateRandom();
            }
        });

        findViewById(R.id.btnReturnNumber).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnResult(number);

            }
        });

        updateRandom();
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String message = intent.getStringExtra(WorkService.KEY_MESSAGE);
                TextView textView = findViewById(R.id.txtRandNumber);
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

    public String getRandomNumToString(){
        return number;
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.unregisterReceiver(receiver);
        super.onStop();
    }

    private void updateRandom() {
        HelloTask task = new HelloTask(RandomActivity.this);
        task.execute();

        ((TextView) findViewById(R.id.txtRandNumber)).setText(task.doInBackground());
    }

    private void returnResult(String result) {
        setResult(RESULT_OK, new Intent().putExtra(RESULT_KEY, result));
        finish();
    }
    static class HelloTask extends AsyncTask<Void, Void, String> {
        WeakReference<RandomActivity> activity;

        HelloTask(RandomActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String output = getUrlContents("https://www.random.org/integers/?num=1&min=1&max=65535&col=1&base=10&format=plain&rnd=new");
            // number bounds are set in the URL
            return output;
        }
        private static String getUrlContents(String numberUrl)
        {
            StringBuilder content = new StringBuilder();
            try
            {
                URL url = new URL(numberUrl);
                URLConnection urlConnection = url.openConnection();
                InputStream is = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    content.append(line + "\n");
                }
                bufferedReader.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            final RandomActivity helloActivity = this.activity.get();
            if (helloActivity != null) {
                if (!s.equals("0")) {
                    ((TextView) helloActivity.findViewById(R.id.txtRandNumber)).setText(s);
                } else {
                    new AlertDialog.Builder(helloActivity)
                            .setTitle("Bad number returned :(")
                            .setMessage("Please try again")
                            .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new HelloTask(helloActivity).execute();
                                }
                            })
                            .setNegativeButton("Ignore", null)
                            .show();
                }
            }
        }
    }
}
