package com.shalu.letsbake;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static com.shalu.letsbake.utils.BakingJsonUtils.results;

public class DetailActivity extends AppCompatActivity implements StepsAdapter.OnStepClickListener {

    public static int recipeIndex;
    boolean isTablet;
    //int orientation;

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
        isTablet = getResources().getBoolean(R.bool.isTablet);
        //orientation = getResources().getConfiguration().orientation;
        if(isTablet) {
            if (savedInstanceState == null) {
                Log.d("EXOPLAYER","now first time");
                RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.step_container, recipeStepFragment).commit();
            }
        }
    }


    @Override
    public void onStepClicked(int position) {
        if(isTablet == false) {
            Intent recipeStepIntent = new Intent(this, RecipeStep.class);
            recipeStepIntent.putExtra(getString(R.string.intent_position), position);
            startActivity(recipeStepIntent);
        }
        else {
            RecipeStepFragment iFragment = new RecipeStepFragment();
            RecipeStep.stepIndex = position;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_container, iFragment).commit();
        }
    }
}
