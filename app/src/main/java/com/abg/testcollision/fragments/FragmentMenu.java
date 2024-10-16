package com.abg.testcollision.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abg.testcollision.Navigator;
import com.abg.testcollision.R;

import java.util.Random;

public class FragmentMenu extends Fragment {

    private Navigator navigator;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = (Navigator) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button start = view.findViewById(R.id.start);
        Button enhancement= view.findViewById(R.id.enhancement);

        start.setOnClickListener(v -> navigator.startFragment(startRandomMode()));
        enhancement.setOnClickListener(v -> {});
    }

    private Fragment startRandomMode() {
        return new AssaultFragment();
//        Random random = new Random();
//        if (random.nextInt(2) == 0) {
//            return new AssaultFragment();
//        } else {
//            return new DefenseFragment();
//        }
    }
}
