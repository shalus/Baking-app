package com.shalu.letsbake;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shalu.letsbake.idlingResource.SimpleIdlingResource;

public class MainActivity extends AppCompatActivity implements ItemListAdapter.OnRecipeClickListener {

    public static String jsonResponse;
    SharedPreferences sharedPreferences;
    @Nullable
    public SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onRecipeClicked(int position) {
        Intent detailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
        detailActivityIntent.putExtra(getString(R.string.intent_position), position);
        startActivity(detailActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_recipe, menu);
        int myRecipe = sharedPreferences.getInt(getString(R.string.favorite_recipe_pref),0);
        menu.getItem(myRecipe+1).setChecked(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch(item.getItemId()) {
            case R.id.fav_nutella: {
                if (!item.isChecked()) {
                    item.setChecked(true);
                    editor.putInt(getString(R.string.favorite_recipe_pref), 0);
                    editor.apply();
                } else item.setChecked(false);
                break;
            }

            case R.id.fav_brownie: {
                if (!item.isChecked()) {
                    item.setChecked(true);
                    editor.putInt(getString(R.string.favorite_recipe_pref), 1);
                    editor.apply();
                } else item.setChecked(false);
                break;
            }

            case R.id.fav_yellow_cake: {
                if (!item.isChecked()) {
                    item.setChecked(true);
                    editor.putInt(getString(R.string.favorite_recipe_pref), 2);
                    editor.apply();
                } else item.setChecked(false);
                break;
            }

            case R.id.fav_cheese_cake: {
                if (!item.isChecked()) {
                    item.setChecked(true);
                    editor.putInt(getString(R.string.favorite_recipe_pref), 3);
                    editor.apply();
                } else item.setChecked(false);
                break;
            }
            default:
                return super.onOptionsItemSelected(item);

        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        for (int appWidgetId : appWidgetIds) {
            BakingAppWidget.updateAppWidget(this, appWidgetManager, appWidgetId);
        }
        return true;
    }
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
