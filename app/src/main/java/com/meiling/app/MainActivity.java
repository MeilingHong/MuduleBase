package com.meiling.app;

import android.os.Bundle;
import android.widget.TextView;

import com.meiling.utils.TextViewUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        TextViewUtil.setTextSize(textView, TextViewUtil.TextSizeUnit.DIP, 16);
    }
}