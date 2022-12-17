package ru.gsemenov.mysteriousservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btn_option1);
        btnStart.setOnClickListener((view) -> {
            // TODO: запустите MysteriousService с помощью startService
        });

        btnStop = findViewById(R.id.btn_option2);
        btnStop.setOnClickListener((view) -> {
            // TODO: остановите MysteriousService с помощью stopService
        });

    }


}