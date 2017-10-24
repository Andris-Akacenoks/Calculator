package com.example.andri.kalkulators;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import java.util.Random;


public class RandomActivity extends Activity {

    public static void startForResult(Activity activity, int requestCode){
        Intent intent = new Intent(activity, RandomActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("parsedNumber", getRandomNumber(1,999999));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    
    public int getRandomNumber(int min, int max){
        Random random = new Random();
        return random.nextInt(max) + min;
    }

}
