package com.abg.testcollision.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Defence wall min: 1, max: 10
 * Damage bullet min: 1, max: 10
 * Class bullet: stock: 1, explosive: 2, shotgun: 3
 * Recharge: 1 - 10 sec,
 *           2 - 8 sec,
 *           3 - 7 - sec,
 *           4 - 6 sec,
 *           5 - 5 sec,
 *           6 - 4 sec,
 *           7 - 3 sec,
 *           8 - 2 sec,
 *           9 - 1 sec
 * **/

public class Save {
    private SharedPreferences prefer;
    private Context context;
    private String name = "tower_defence";

    public Save(Context context) {
        this.context = context;
    }

    public void saveDefenceWallPoint(int point) {
        prefer = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefer.edit();
        ed.putInt("defence", point);
        ed.apply();
    }

    public int getDefenceWallPoint() {
        prefer = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return prefer.getInt("defence", 1);
    }

    public void saveDamageBullet(int point) {
        prefer = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefer.edit();
        ed.putInt("damage", point);
        ed.apply();
    }

    public int getDamageBullet() {
        prefer = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return prefer.getInt("damage", 1);
    }

    public void saveClassBullet(int point) {
        prefer = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefer.edit();
        ed.putInt("bullet", point);
        ed.apply();
    }

    public int getClassBullet() {
        prefer = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return prefer.getInt("bullet", 1);
    }

    public void saveRecharge(int point) {
        prefer = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefer.edit();
        ed.putInt("recharge", point);
        ed.apply();
    }

    public int getRecharge() {
        prefer = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return prefer.getInt("recharge", 1);
    }
}
