package com.shalu.letsbake;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import butterknife.Unbinder;

import static com.shalu.letsbake.DetailActivity.recipeIndex;
import static com.shalu.letsbake.utils.BakingJsonUtils.results;

/**
 * Created by sarum on 11/16/2017.
 */

public class DetailListFragment extends Fragment {


    @BindView(R.id.tv_ingredient) TextView mIngredientText;
    @BindView(R.id.list_view) ListView mListSteps;
    private Unbinder unbinder;
    private static final String STEP_INDEX = "step_index";


        public DetailListFragment() {}

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail, container);
        unbinder = ButterKnife.bind(this, view);
        String[] steps;
        String ingredient = "";
        if(results==null)
            return view;
        for (int i = 0; i < results[recipeIndex].ingredients.length; i++) {
            ingredient += results[recipeIndex].ingredients[i].quantity + " " + results[recipeIndex].ingredients[i].measure + " " +
                    results[recipeIndex].ingredients[i].ingredient_name + ", ";
        }
        steps = new String[results[recipeIndex].steps.length];
        for(int i = 0; i < results[recipeIndex].steps.length; i++){
            steps[i] = results[recipeIndex].steps[i].shortDescription;
        }
        mIngredientText.setText(ingredient);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.step, steps);
        mListSteps.setAdapter(adapter);
        if(savedInstanceState!=null)
            mListSteps.smoothScrollToPosition(savedInstanceState.getInt(STEP_INDEX));
        mListSteps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(DetailActivity.twoPane == false) {
                    Intent recipeStepIntent = new Intent(getContext(), RecipeStep.class);
                    recipeStepIntent.putExtra(getString(R.string.intent_position), i);
                    startActivity(recipeStepIntent);
                }
                else {
                    RecipeStepFragment iFragment = new RecipeStepFragment();
                    RecipeStep.stepIndex = i;
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.step_container, iFragment).commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_INDEX,mListSteps.getFirstVisiblePosition());
    }
}
