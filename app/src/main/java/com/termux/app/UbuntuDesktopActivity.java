package com.termux.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.termux.R;

public class UbuntuDesktopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubuntu_desktop);

        if (TermuxActivity.instance == null) {
            Intent intent = new Intent(this, TermuxActivity.class);
            intent.putExtra("execute_command", "echo Hello");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            TermuxActivity.instance.executeCommand("echo Hello");
            Intent intent = new Intent(this, TermuxActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
} 
