package com.shalu.letsbake.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sarum on 11/13/2017.
 */

public class BakingJsonUtils {

    private static final String TAG = BakingJsonUtils.class.getSimpleName();

    private static final String PARAM_QUANTITY = "quantity";
    private static final String PARAM_MEASURE = "measure";
    private static final String PARAM_INGREDIENT = "ingredient";
    private static final String PARAM_INGREDIENTS = "ingredients";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_IMAGE = "image";
    private static final String PARAM_STEPS = "steps";
    private static final String PARAM_SHORTDESC = "shortDescription";
    private static final String PARAM_DESC = "description";
    private static final String PARAM_VIDEO = "videoURL";
    private static final String PARAM_SERVING = "servings";
    private static final String PARAM_THUMBNAIL = "thumbnailURL";

    public static Recipe[] results;

    public static void extractDetailsFromJson(String jsonStr) throws JSONException {

        JSONArray recipes = new JSONArray(jsonStr);
        results = new Recipe[recipes.length()];

        for (int i = 0; i < recipes.length(); i++) {
            JSONObject recipe = recipes.getJSONObject(i);

            String name = recipe.getString(PARAM_NAME);
            String servings = recipe.getString(PARAM_SERVING);
            String image = recipe.getString(PARAM_IMAGE);
            results[i] = new Recipe(name, servings, image);

            JSONArray ingredientsJsonArray = recipe.getJSONArray(PARAM_INGREDIENTS);
            Log.d(TAG, ingredientsJsonArray.length()+" ");
            results[i].ingredients = new Recipe.Ingredient[ingredientsJsonArray.length()];
            for (int j = 0; j < ingredientsJsonArray.length(); j++) {

                JSONObject ing = ingredientsJsonArray.getJSONObject(j);
                Log.d(TAG, ing.getString(PARAM_QUANTITY));
                results[i].ingredients[j] = new Recipe.Ingredient();

                results[i].ingredients[j].quantity = ing.getString(PARAM_QUANTITY);
                results[i].ingredients[j].measure = ing.getString(PARAM_MEASURE);
                results[i].ingredients[j].ingredient_name = ing.getString(PARAM_INGREDIENT);
            }
            JSONArray stepsJsonArray = recipe.getJSONArray(PARAM_STEPS);
            results[i].steps = new Recipe.Steps[stepsJsonArray.length()];

            for (int j = 0; j < stepsJsonArray.length(); j++) {

                JSONObject steps = stepsJsonArray.getJSONObject(j);
                results[i].steps[j] = new Recipe.Steps();

                results[i].steps[j].shortDescription = steps.getString(PARAM_SHORTDESC);
                results[i].steps[j].description = steps.getString(PARAM_DESC);
                results[i].steps[j].videoURL = steps.getString(PARAM_VIDEO);
                results[i].steps[j].thumbnailURL = steps.getString(PARAM_THUMBNAIL);
            }
        }
        return;
    }
}

