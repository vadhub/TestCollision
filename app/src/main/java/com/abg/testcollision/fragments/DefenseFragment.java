package com.abg.testcollision.fragments;

import android.os.Bundle;
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

public class DefenseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_assault, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView countPassEnemy = view.findViewById(R.id.passEnemy);
        GameModeDefense gameView = view.findViewById(R.id.gameView);
        gameView.setChangeCountListener(count ->  countPassEnemy.setText("Pass enemy: " + count));
        Button building = view.findViewById(R.id.build);
        Button shoot = view.findViewById(R.id.shoot);
        building.setOnClickListener(v -> gameView.setState(GameModeDefense.State.BUILD));
        shoot.setOnClickListener(v -> gameView.setState(GameModeDefense.State.SHOOT));
        countPassEnemy.setText("ok");
    }
}
