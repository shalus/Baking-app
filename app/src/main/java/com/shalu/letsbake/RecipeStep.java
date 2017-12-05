package com.shalu.letsbake;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sarum on 11/17/2017.
 */

public class RecipeStep extends AppCompatActivity {

    public static int stepIndex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_detail);

        if (getIntent() != null) {
            if (getIntent().hasExtra(getString(R.string.intent_position))) {
                stepIndex = getIntent().getIntExtra(getString(R.string.intent_position), 0);
            }
        }
        if (savedInstanceState == null) {
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_container, recipeStepFragment).commit();
        }
     }
}
