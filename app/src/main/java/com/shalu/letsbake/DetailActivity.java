package com.shalu.letsbake;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.shalu.letsbake.utils.BakingJsonUtils.results;

public class DetailActivity extends AppCompatActivity {

    public static int recipeIndex;
    public static boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(getString(R.string.intent_position))){
            recipeIndex = intent.getIntExtra(getString(R.string.intent_position),0);
        }
        if(savedInstanceState==null)
            RecipeStep.stepIndex = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle(results[recipeIndex].name);

        if(findViewById(R.id.layout_recipe_steps) != null) {
            twoPane = true;
            if (savedInstanceState == null) {
                RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.step_container, recipeStepFragment).commit();
            }
        }
    }


}
