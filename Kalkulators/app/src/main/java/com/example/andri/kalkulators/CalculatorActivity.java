package com.example.andri.kalkulators;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.andri.kalkulators.CalculatorAction.ADDITION;
import static com.example.andri.kalkulators.CalculatorAction.DIVISION;
import static com.example.andri.kalkulators.CalculatorAction.MULTIPLICATION;
import static com.example.andri.kalkulators.CalculatorAction.SUBTRACTION;

public class CalculatorActivity extends Activity implements View.OnClickListener{

    public static final int REQUEST_CALCULATOR = 9;
    private static boolean isClearable;
    private static int randomResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calculator_activity_layout);

        Button buttonZero = findViewById(R.id.btn0);
        Button buttonOne = findViewById(R.id.btn1);
        Button buttonTwo = findViewById(R.id.btn2);
        Button buttonThree = findViewById(R.id.btn3);
        Button buttonFour = findViewById(R.id.btn4);
        Button buttonFive = findViewById(R.id.btn5);
        Button buttonSix = findViewById(R.id.btn6);
        Button buttonSeven = findViewById(R.id.btn7);
        Button buttonEight = findViewById(R.id.btn8);
        Button buttonNine = findViewById(R.id.btn9);
        Button buttonAdd = findViewById(R.id.btnPlus);
        Button buttonSubtract = findViewById(R.id.btnMinus);
        Button buttonMultiply = findViewById(R.id.btnMultiply);
        Button buttonDivide = findViewById(R.id.btnDivide);
        Button buttonClear = findViewById(R.id.btnClear);
        Button buttonPoint = findViewById(R.id.btnPoint);
        Button buttonEquals = findViewById(R.id.btnEquals);

        buttonZero.setOnClickListener(this);
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSubtract.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonPoint.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        buttonClear.setOnClickListener(this);


    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView resultView = findViewById(R.id.txtResult);

        switch (item.getItemId()) {
            case R.id.menuRandom:
                requestRandom();
                resultView.setText(String.valueOf(randomResult));
                makeClearable(true);
                return true;
            case R.id.menuShare:
                shareResult(resultView.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Override
        public void onClick(View calculatorView) {
            Calculator calculator = new CalculatorEngine();
            TextView resultView = findViewById(R.id.txtResult);

            switch (calculatorView.getId()) {
                case R.id.btnClear:
                    calculator.clear();
                    displayResult(calculator, resultView);
                    break;
                case R.id.btnEquals:
                    try {
                        calculator.putOperand(resultView.getText().toString());
                        displayResult(calculator, resultView);
                    } catch (CalculatorExeption e) {
                        showMessage("Invalid operation");
                    }
                    break;
                case R.id.btnPlus:
                case R.id.btnMinus:
                case R.id.btnMultiply:
                case R.id.btnDivide:
                    try {
                        Button buttonOperation = findViewById(calculatorView.getId());
                        calculator.putOperand(resultView.getText().toString());
                        showMessage((buttonOperation.getText().toString()));


                        switch (buttonOperation.getText().toString()) {
                            case "+":
                                calculator.putAction(ADDITION);
                                break;
                            case "-":
                                calculator.putAction(SUBTRACTION);
                                break;
                            case "*":
                                calculator.putAction(MULTIPLICATION);
                                break;
                            case "/":
                                calculator.putAction(DIVISION);
                                break;
                            default:
                                showMessage("Invalid operator");
                        }
                        displayResult(calculator, resultView);

                    } catch (CalculatorExeption e) {
                        showMessage("Invalid operation");
                    }
                    break;
                default:
                    Button buttonDigit = findViewById(calculatorView.getId());
                    addToDisplay(buttonDigit.getText().toString(), resultView);
                    showMessage((buttonDigit.getText().toString()));
                    showMessage(("Result: "+calculator.getResult()));

            }
        }

    public void displayResult(Calculator calculator, TextView text) {
        text.setText(calculator.getResult());
        makeClearable(true);
    }

    public void addToDisplay(String addedText, TextView textView){
        if(getIsClearable()){
            textView.setText("");
            makeClearable(false);
        }
        else{
            textView.append(addedText);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CALCULATOR:
                randomResult = onRandomResult(resultCode, data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private int onRandomResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int result = data.getIntExtra("parsedNumber", 0);
            if (result != 0) {
                return result;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    public void makeClearable(boolean isAllowed){
        isClearable = isAllowed;
    }

    public boolean getIsClearable(){
        return isClearable;
    }

    public void shareResult(String input){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, input);
        intent.setType("text/plain");
        startActivity(intent);
    }

    public void requestRandom(){
        RandomActivity.startForResult(CalculatorActivity.this, REQUEST_CALCULATOR);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
