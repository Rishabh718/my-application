package com.example.mulmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class DescActivity extends AppCompatActivity {

    EditText latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        latitude=findViewById(R.id.latitude);

        String key=getIntent().getStringExtra("KEY");
        latitude.setText(key);
    }
}
