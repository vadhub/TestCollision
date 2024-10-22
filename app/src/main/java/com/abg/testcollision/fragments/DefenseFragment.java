package com.abg.testcollision.fragments;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abg.testcollision.R;
import com.abg.testcollision.gamemode.GameModeDefense;

import java.util.Timer;

public class DefenseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_defense, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView countPassEnemy = view.findViewById(R.id.passEnemy);
        TextView scoreTextView = view.findViewById(R.id.score);
        GameModeDefense gameView = view.findViewById(R.id.gameView);
        gameView.setZOrderOnTop(true);
        gameView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        gameView.setChangeCountListener(count ->  countPassEnemy.setText("Pass enemy: " + count));
        gameView.setChangeScoreListener(score -> scoreTextView.setText("Score" + score));
        Button building = view.findViewById(R.id.build);
        Button shoot = view.findViewById(R.id.shoot);
        building.setOnClickListener(v -> gameView.setState(GameModeDefense.State.BUILD));
        shoot.setOnClickListener(v -> gameView.setState(GameModeDefense.State.SHOOT));
        countPassEnemy.setText("ok");

         new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                gameView.setStartGame(false);
            }
        }.start();
    }
}
