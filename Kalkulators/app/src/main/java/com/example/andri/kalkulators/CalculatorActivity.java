package com.example.andri.kalkulators;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CalculatorActivity extends Activity {

    public static final int REQUEST_CALCULATOR = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // share button
        Button buttonShare = new Button(this);
        buttonShare.setText("Share");
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareResult("This text has been shared!");
            }
        });
        linearLayout.addView(buttonShare);

        // random button
        Button buttonRandom = new Button(this);
        buttonRandom.setText("Random");
        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRandom();
            }
        });
        linearLayout.addView(buttonRandom);

        setContentView(linearLayout);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CALCULATOR:
                onRandomResult(resultCode, data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onRandomResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int result = data.getIntExtra("parsedNumber", 1);
            if (result != 0) {
                showMessage("Random number: " + result);
            } else {
                showMessage("Random number was zero! (that's not good)");
            }
        } else {
            showMessage("No random number received");
        }
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
