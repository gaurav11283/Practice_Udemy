package com.example.practiceudemy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private TextView textView;
    private Button button;
    private Button calculator;
    private Button download;
    private Button btnYoutube;
    private Button standAlone;
    private Button flickr;
    private int count = 0;
    private static final String TAG = "MainActivity";
    private static final String TEXT_CONTENT = "text_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        btnYoutube = findViewById(R.id.btn_youtube);
        calculator = findViewById(R.id.calculator);
        download = findViewById(R.id.download);
        standAlone = findViewById(R.id.btnStandalone);
        flickr = findViewById(R.id.btnFlickr);
        textView.setText("");
        textView.setMovementMethod(new ScrollingMovementMethod());



        button.setOnClickListener(this);
        calculator.setOnClickListener(this);
        download.setOnClickListener(this);
        btnYoutube.setOnClickListener(this);
        standAlone.setOnClickListener(this);
        flickr.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: in");
        super.onStart();
        Log.d(TAG, "onStart: out");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: in");
        super.onStop();
        Log.d(TAG, "onStop: out");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: in");
        super.onDestroy();
        Log.d(TAG, "onDestroy: out");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: in");
        super.onPause();
        Log.d(TAG, "onPause: out");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: in");
        super.onResume();
        Log.d(TAG, "onResume: out");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: in");
        //here we are saving current instance data in bundle to restore it while changing orientation of device
        //or somehow during activity resume
        outState.putString(TEXT_CONTENT, textView.getText().toString());
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: out");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");
        //here we can restore our previous instance state
        String setInstance = savedInstanceState.getString(TEXT_CONTENT);
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(setInstance);
        Log.d(TAG, "onRestoreInstanceState: out");
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: in");
        super.onRestart();
        Log.d(TAG, "onRestart: out");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button:
                count++;
                String result = "\nThe button clicked " + count + "  times..." + editText.getText().toString();
                textView.append(result);
                break;
            case R.id.calculator:
                startActivity(new Intent(MainActivity.this, Calculator.class));
                break;
            case R.id.download:
                startActivity(new Intent(MainActivity.this, DownloadData.class));
                break;
            case R.id.btn_youtube:
                startActivity(new Intent(MainActivity.this,YouTubeActivity.class));
                break;
            case R.id.btnStandalone:
                startActivity(new Intent(MainActivity.this,StandAloneActivity.class));
                break;
            case R.id.btnFlickr:
                startActivity(new Intent(MainActivity.this,FlickerAppActivity.class));
                break;

            default:break;

        }

    }
}