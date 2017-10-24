package com.example.andri.kalkulators;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class CalculatorActivity extends Activity {

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
                shareResult("Result has been shared!");
            }
        });
        linearLayout.addView(buttonShare);

        // random button
        Button buttonRandom = new Button(this);
        buttonRandom.setText("Random");
        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OtherActivity.startForResult(MainActivity.this, REQUEST_OTTER);
            }
        });
        linearLayout.addView(buttonRandom);

        setContentView(linearLayout);
    }

    public void shareResult(String input){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, input);
        intent.setType("text/plain");
        startActivity(intent);
    }
}
