package com.shalu.letsbake;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.widget.RemoteViews;

import com.shalu.letsbake.utils.BakingJsonUtils;
import com.shalu.letsbake.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;

import static com.shalu.letsbake.utils.BakingJsonUtils.results;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String widgetText = "", ingredientName = "";
        int myRecipe;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        myRecipe = sharedPreferences.getInt(context.getString(R.string.favorite_recipe_pref),0);
        Log.d("BakingAppWidget", myRecipe+" ");
        if(results!=null) {
            for (int i = 0; i < results[myRecipe].ingredients.length; i++) {
                widgetText += results[myRecipe].ingredients[i].quantity + " " + results[myRecipe].ingredients[i].measure + " " + results[myRecipe].ingredients[i].ingredient_name + "\n";
            }
            ingredientName = results[myRecipe].name;
        }

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("POSITION",myRecipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.ingredient_text, widgetText);

        views.setTextViewText(R.id.recipe_name,ingredientName);

        views.setOnClickPendingIntent(R.id.ingredient_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

