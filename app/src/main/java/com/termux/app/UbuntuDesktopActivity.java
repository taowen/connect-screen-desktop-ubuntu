package com.termux.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.termux.R;
import com.termux.shared.termux.TermuxConstants;

public class UbuntuDesktopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubuntu_desktop);

        // 添加退出按钮点击事件
        findViewById(R.id.exit_button).setOnClickListener(v -> {
            if (TermuxActivity.instance != null) {
                TermuxActivity.instance.finish();
            }
            Intent exitIntent = new Intent(this, TermuxService.class).setAction(TermuxConstants.TERMUX_APP.TERMUX_SERVICE.ACTION_STOP_SERVICE);
            startService(exitIntent);
            finish();
        });
        // 显示启动提示
        SharedPreferences prefs = getSharedPreferences("connect_screen_ubuntu_desktop", MODE_PRIVATE);
        if (prefs.getBoolean("dont_show_tips", false)) {
            startDesktop(prefs);
        } else {
            android.view.View dialogView = getLayoutInflater().inflate(R.layout.dialog_tips_checkbox, null);
            android.widget.CheckBox checkBox = dialogView.findViewById(R.id.dont_show_again);
            
            new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("提示")
                .setView(dialogView)
                .setMessage("需手工进入安卓屏连，在屏幕详情页点击“投屏 Termux X11”按钮\nUbuntu 用户密码 12345678")
                .setPositiveButton("确定", (dialog, which) -> {
                    if (checkBox.isChecked()) {
                        prefs.edit().putBoolean("dont_show_tips", true).apply();
                    }
                    startDesktop(prefs);
                })
                .show();
        }


    }

    private void startDesktop(SharedPreferences prefs) {
        if (!prefs.getBoolean("has_restored_initial_backup", false)) {
            Intent intent = new Intent(this, TermuxActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
        if (TermuxActivity.instance == null) {
            Intent intent = new Intent(this, TermuxActivity.class);
            intent.putExtra("execute_command", TermuxActivity.START_UBUNTU_DESKTOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            TermuxActivity.instance.executeCommand(TermuxActivity.START_UBUNTU_DESKTOP);
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
