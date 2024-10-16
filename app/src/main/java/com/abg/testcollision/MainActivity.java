package com.abg.testcollision;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.abg.testcollision.gamemode.GameModeAssault;
import com.abg.testcollision.gamemode.GameViewDefense;

public class MainActivity extends AppCompatActivity {

    TextView countPassEnemy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);

        countPassEnemy = findViewById(R.id.passEnemy);
        GameModeAssault gameView = findViewById(R.id.gameView);
        gameView.setChangeCountListener(count ->  countPassEnemy.setText("Pass enemy: " + count));
        Button building = findViewById(R.id.build);
        Button shoot = findViewById(R.id.shoot);
//        building.setOnClickListener(v -> gameView.setState(GameViewDefense.State.BUILD));
//        shoot.setOnClickListener(v -> gameView.setState(GameViewDefense.State.SHOOT));
        countPassEnemy.setText("ok");
    }
}
