package com.example.andri.kalkulators;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class RandomActivity extends Activity {
    public static final String RESULT_KEY = "result";

    public static void launchForResult(Activity starter, int requestCode) {
        starter.startActivityForResult(new Intent(starter, RandomActivity.class), requestCode);
    }

    private int number;
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

    private void updateRandom() {
        number = random.nextInt(999999) + 1;
        ((TextView) findViewById(R.id.withText)).setText(String.valueOf(number));
    }

    private void returnResult(int result) {
        setResult(RESULT_OK, new Intent().putExtra(RESULT_KEY, result));
        finish();
    }
}
