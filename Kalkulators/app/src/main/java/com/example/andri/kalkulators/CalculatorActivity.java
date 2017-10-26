package com.example.andri.kalkulators;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends Activity implements View.OnClickListener {
    public static final int REQUEST_CODE = 371;

    private Calculator calculator = new CalculatorEngine();
    private boolean  rewriteDisplay;
    private TextView display;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity_layout);

        display = findViewById(R.id.txtResult);

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

        displayResult();
    }

    @Override
    public void onClick(View view) {
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
}
