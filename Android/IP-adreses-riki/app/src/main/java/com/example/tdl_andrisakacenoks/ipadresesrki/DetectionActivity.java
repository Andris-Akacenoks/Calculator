package com.example.tdl_andrisakacenoks.ipadresesrki;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Text;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;


public class DetectionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView ipView;
    private TextView ipHint;
    private static String url = "http://httpbin.org/ip";   // url for ip address
    private String ipAdress;
    ProgressDialog pd;

    //JSON Node Names
    private static final String IP_ADDRESS = "origin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        ipView = findViewById(R.id.txt_ipField);
        ipHint = findViewById(R.id.txtHint);

        findViewById(R.id.btn_getIP).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getIP:
                //showToast("IP poga nospiesta");
                new GetIpTask(DetectionActivity.this).execute(url);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateIp(String s) {
        this.ipAdress = ipAdress;
        ((TextView) findViewById(R.id.txt_ipField)).setText(String.valueOf(s));
    }

    private class GetIpTask extends AsyncTask<String, String, String> {

        private static final String IP_URL = "http://httpbin.org/ip";
        WeakReference<DetectionActivity> activity;

        GetIpTask(DetectionActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DetectionActivity.this);
            pd.setMessage("Fetching IP adress...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            ipView.setText(result);

            try {
                JSONObject ipResult = new JSONObject(result);
                ipView.setText(ipResult.getString("origin"));
                ipHint.setText("Your IP:");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (ipView.getText() != null) {
            outState.putString("address", ipView.getText().toString());
        } else {
            outState.putString("address", "");
        }
        //showToast("Ekrans pagriests");
        //showToast("saglabats teksts: "+ ipView.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString("address") != null) {
            ipView.setText(savedInstanceState.getString("address"));
        }
    }
}