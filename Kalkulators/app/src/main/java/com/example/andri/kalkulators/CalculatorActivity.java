package com.example.andri.kalkulators;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class CalculatorActivity extends Activity implements View.OnClickListener {
    public static final int REQUEST_CODE = 371;
    public static final double PI = 3.1415926;

    private Calculator calculator = new CalculatorEngine();
    private boolean rewriteDisplay;
    private static TextView display;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity_layout);

        display = findViewById(R.id.txtResult);
        preferences = getSharedPreferences("save", MODE_PRIVATE);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnPoint).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);


        //findViewById(R.id.btnInc).setOnClickListener(new View.OnClickListener() {
           // @SuppressLint("SetTextI18n")
          //  @Override
          //  public void onClick(View view) {
            //    display.setText(display.getText() + "0");
         //   }
        //});



        if (savedInstanceState != null) {
            CharSequence savedText = savedInstanceState.getString("counter");
            display.setText(savedText);
        }

        //findViewById(R.id.btnSave).setOnClickListener(this);
        //findViewById(R.id.btnReset).setOnClickListener(this);
        //findViewById(R.id.btnRestore).setOnClickListener(this);

        displayResult();


/////////////// ///////////////////////////////////////////////////////////




    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(calculator.getOperand() != null){
            outState.putString("operand", calculator.getOperand().toString());
        }
        else{
            outState.putString("operand","");
        }
        outState.putString("result", display.getText().toString());
        outState.putBoolean("isScreenClear", rewriteDisplay);
        if(calculator.getOperand() != null){
            outState.putString("action", calculator.getActionToString());
        }
        else{
            outState.putString("action","");

        }
        outState.putString("randNum", RandomActivity.RESULT_KEY); // TODO izmanit
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(!savedInstanceState.getString("operand").isEmpty()){
            Double savedOperand = Double.valueOf(savedInstanceState.getString("operand"));
            calculator.putOperand(savedInstanceState.getString("operand"));
        }

        display.setText(savedInstanceState.getString("result"));
        rewriteDisplay = savedInstanceState.getBoolean("isScreenClear");

        if(!savedInstanceState.getString("action").isEmpty()){

            switch(savedInstanceState.getString("action")){
                case "+":
                    calculator.putAction(CalculatorAction.ADDITION);
                    break;
                case "-":
                    calculator.putAction(CalculatorAction.SUBSTRACTION);
                    break;
                case "/":
                    calculator.putAction(CalculatorAction.DIVISION);
                    break;
                case "*":
                    calculator.putAction(CalculatorAction.MULTIPLICATION);
                    break;
                default:
                    break;

            }

        }

    }

    @Override
    public void onClick(View view) {



        try {
            switch (view.getId()) {
                case R.id.btn0:
                    addToDisplay("0");

                    break;
                case R.id.btn1:
                    addToDisplay("1");
                    break;
                case R.id.btn2:
                    addToDisplay("2");
                    break;
                case R.id.btn3:
                    addToDisplay("3");
                    break;
                case R.id.btn4:
                    addToDisplay("4");
                    break;
                case R.id.btn5:
                    addToDisplay("5");
                    break;
                case R.id.btn6:
                    addToDisplay("6");
                    break;
                case R.id.btn7:
                    addToDisplay("7");
                    break;
                case R.id.btn8:
                    addToDisplay("8");
                    break;
                case R.id.btn9:
                    addToDisplay("9");
                    break;
                case R.id.btnPoint:
                    addToDisplay(".");
                    break;
                case R.id.btnClear:
                    calculator.clear();
                    displayResult();
                    break;
                case R.id.btnPlus:
                    calculator.putOperand(display.getText().toString());
                    calculator.putAction(CalculatorAction.ADDITION);
                    displayResult();
                    break;
                case R.id.btnMinus:
                    calculator.putOperand(display.getText().toString());
                    calculator.putAction(CalculatorAction.SUBSTRACTION);
                    displayResult();
                    break;
                case R.id.btnMultiply:
                    calculator.putOperand(display.getText().toString());
                    calculator.putAction(CalculatorAction.MULTIPLICATION);
                    displayResult();
                    break;
                case R.id.btnDivide:
                    calculator.putOperand(display.getText().toString());
                    calculator.putAction(CalculatorAction.DIVISION);
                    displayResult();
                    break;
                case R.id.btnEquals:
                    calculator.putOperand(display.getText().toString());
                    displayResult();
                    break;
            }
        } catch (CalculatorException e) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("An error occured");
            dialog.setMessage("Exception caught: "+e.getMessage());
            dialog.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    display.setText("");

                }
            })
                    .setNegativeButton("Ignore", null)
                    .show();

        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRandom:
                requestRandom();
                return true;
            case R.id.menuShare:
                shareResult(display.getText().toString());
                return true;
            case R.id.menuPI:
                display.setText(null);
                WorkService.showMessage(this, 0, String.valueOf(PI));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayResult() {
        display.setText(calculator.getResult());
        rewriteDisplay = true;
    }

    private void addToDisplay(String part) {
        if (!rewriteDisplay) {
            part = display.getText() + part;
        }
        rewriteDisplay = false;
        display.setText(part);
    }

    private void shareResult(String result) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, result);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void requestRandom() {
        RandomActivity.launchForResult(this, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                handleRandom(resultCode, data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleRandom(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            showMessage(getString(R.string.error_random_cancel));
        } else {
            int value = data.getIntExtra(RandomActivity.RESULT_KEY, 0);
            if (value == 0) {
                showMessage(getString(R.string.error_random_unknown));
            } else {
                display.setText(String.valueOf(value));
                rewriteDisplay = true;
            }
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

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





}

