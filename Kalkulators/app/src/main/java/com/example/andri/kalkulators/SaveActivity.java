package com.example.andri.kalkulators;

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.TextView;

public class SaveActivity extends AppCompatActivity implements View.OnClickListener {
    TextView          txtCounter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("save", MODE_PRIVATE);

        setContentView(R.layout.activity_save);
        txtCounter = findViewById(R.id.txtCount);

        findViewById(R.id.btnInc).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                txtCounter.setText(txtCounter.getText() + "0");
            }
        });

        if (savedInstanceState != null) {
            String counter = savedInstanceState.getString("counter");
            if (counter != null) {
                txtCounter.setText(counter);
            }
        }

        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnReset).setOnClickListener(this);
        findViewById(R.id.btnRestore).setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("counter", txtCounter.getText().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                preferences.edit().putString("counter", txtCounter.getText().toString()).apply();
                break;
            case R.id.btnReset:
                preferences.edit().clear().apply();
            case R.id.btnRestore:
                String counter = preferences.getString("counter", "1");
                txtCounter.setText(counter);
                break;
        }
    }
}
